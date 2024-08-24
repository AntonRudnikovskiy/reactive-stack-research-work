package com.losevskiyfz.reactivestackresearchwork.controller;

import com.losevskiyfz.reactivestackresearchwork.domain.BookRecord;
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
public class BookRecordController {

    private final BookService bookService;

    @Autowired
    public BookRecordController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    ResponseEntity<BookRecord> save(@Valid @RequestBody BookRecord bookRecord) {
        BookRecord savedBookRecord = bookService.save(bookRecord);
        return ResponseEntity
                .created(
                        ServletUriComponentsBuilder
                                .fromCurrentRequest()
                                .path("/{id}")
                                .buildAndExpand(savedBookRecord.id())
                                .toUri()
                )
                .body(savedBookRecord);
    }

    @PutMapping("/{id}")
    ResponseEntity<BookRecord> update(@PathVariable String id, @Valid @RequestBody BookRecord bookRecord) {
        if(bookService.findById(id).isPresent()) {
            bookService.save(bookRecord);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<Page<BookRecord>> get(
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
