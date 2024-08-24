package com.losevskiyfz.reactivestackresearchwork.controller;

import com.losevskiyfz.reactivestackresearchwork.domain.Book;
import com.losevskiyfz.reactivestackresearchwork.domain.PostBookDto;
import com.losevskiyfz.reactivestackresearchwork.mapper.BookMapper;
import com.losevskiyfz.reactivestackresearchwork.service.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/v1/book")
public class BookController {

    private final BookService bookService;
    private final BookMapper bookMapper;

    @Autowired
    public BookController(BookService bookService, BookMapper bookMapper) {
        this.bookService = bookService;
        this.bookMapper = bookMapper;
    }

    @PostMapping
    ResponseEntity<Book> save(@Valid @RequestBody PostBookDto bookRecord) {
        Book bookToSave = bookMapper.toBook(bookRecord);
        Book savedBook = bookService.save(bookToSave);
        return ResponseEntity
                .created(
                        ServletUriComponentsBuilder
                                .fromCurrentRequest()
                                .path("/{id}")
                                .buildAndExpand(savedBook.id())
                                .toUri()
                )
                .body(savedBook);
    }

    @PutMapping("/{id}")
    ResponseEntity<Book> update(@PathVariable String id, @Valid @RequestBody PostBookDto bookRecord) {
        if(bookService.findById(id).isPresent()) {
            Book bookToSave = bookMapper.toBook(bookRecord);
            bookService.save(bookToSave);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<Page<Book>> get(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "") String pattern,
            @RequestParam(defaultValue = "name") String[] sortBy,
            @RequestParam(defaultValue = "asc") String[] sortDir
    ) {
        Sort sort = Sort.unsorted();
        for (int i = 0; i < sortBy.length; i++) {
            if ("desc".equalsIgnoreCase(sortDir[i])) {
                sort = sort.and(Sort.by(Sort.Order.desc(sortBy[i])));
            } else {
                sort = sort.and(Sort.by(Sort.Order.asc(sortBy[i])));
            }
        }
        Pageable pageable = PageRequest.of(page, size, sort);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(bookService.getPaginated(pageable, pattern));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") String id) {
        bookService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
