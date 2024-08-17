package com.losevskiyfz.reactivestackresearchwork.service;

import com.losevskiyfz.reactivestackresearchwork.domain.BookRecord;
import com.losevskiyfz.reactivestackresearchwork.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookJpaService {
    private final BookRepository bookRepository;

    @Autowired
    public BookJpaService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public BookRecord save(BookRecord book) {
        return bookRepository.save(book);
    }

}
