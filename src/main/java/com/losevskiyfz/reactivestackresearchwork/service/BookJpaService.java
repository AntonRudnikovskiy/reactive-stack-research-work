package com.losevskiyfz.reactivestackresearchwork.service;

import com.losevskiyfz.reactivestackresearchwork.domain.Book;
import com.losevskiyfz.reactivestackresearchwork.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BookJpaService implements BookService {
    private final BookRepository bookRepository;

    @Autowired
    public BookJpaService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Book save(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public Page<Book> getPaginated(int page, int size, String pattern) {
        Pageable pageRequest = PageRequest.of(page, size);
        return bookRepository.getByTextPattern(pageRequest, pattern);
    }

    @Override
    public void delete(String bookId) {
        bookRepository.deleteById(bookId);
    }

    @Override
    public Optional<Book> findById(String id){
        return bookRepository.findById(id);
    }

}
