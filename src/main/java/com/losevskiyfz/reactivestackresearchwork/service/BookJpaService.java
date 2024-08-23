package com.losevskiyfz.reactivestackresearchwork.service;

import com.losevskiyfz.reactivestackresearchwork.domain.BookRecord;
import com.losevskiyfz.reactivestackresearchwork.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class BookJpaService implements BookService {
    private final BookRepository bookRepository;

    @Autowired
    public BookJpaService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public BookRecord save(BookRecord book) {
        return bookRepository.save(book);
    }

    @Override
    public Page<BookRecord> getPaginated(int page, int size) {
        Pageable pageRequest = PageRequest.of(page, size);
        return bookRepository.findAll(pageRequest);
    }

    @Override
    public String delete(String bookId) {
        if (bookRepository.existsById(bookId)) {
            bookRepository.deleteById(bookId);
            return "Запись успешно удалена";
        } else {
            return "Запись не удалена, по текущему id - " + bookId;
        }
    }

}
