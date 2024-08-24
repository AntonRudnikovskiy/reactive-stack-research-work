package com.losevskiyfz.reactivestackresearchwork.controller;

import com.losevskiyfz.reactivestackresearchwork.domain.Book;
import com.losevskiyfz.reactivestackresearchwork.domain.PostBookDto;
import com.losevskiyfz.reactivestackresearchwork.mapper.BookMapper;
import com.losevskiyfz.reactivestackresearchwork.service.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
            @RequestParam(defaultValue = "") String pattern) {
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(bookService.getPaginated(page, size, pattern));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") String id) {
        bookService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
