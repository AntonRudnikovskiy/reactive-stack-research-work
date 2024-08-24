package com.losevskiyfz.reactivestackresearchwork.service;

import com.losevskiyfz.reactivestackresearchwork.domain.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface BookService {
    Book save(Book book);

    void delete(String bookId);

    Page<Book> getPaginated(Pageable pageable, String pattern);

    Optional<Book> findById(String id);
}
