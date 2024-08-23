package com.losevskiyfz.reactivestackresearchwork.service;

import com.losevskiyfz.reactivestackresearchwork.domain.BookRecord;
import org.springframework.data.domain.Page;

public interface BookService {
    BookRecord save(BookRecord book);

    void delete(String bookId);

    Page<BookRecord> getPaginated(int page, int size, String pattern);

}
