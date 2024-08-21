package com.losevskiyfz.reactivestackresearchwork.controller;

import com.losevskiyfz.reactivestackresearchwork.domain.BookRecord;
import com.losevskiyfz.reactivestackresearchwork.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/v1")
public class BookRecordController {

    private final BookService bookService;

    @Autowired
    public BookRecordController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping("/book")
    ResponseEntity<BookRecord> save(@RequestBody BookRecord bookRecord) {
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

}
