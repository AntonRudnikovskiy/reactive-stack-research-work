package com.losevskiyfz.reactivestackresearchwork.service;

import com.losevskiyfz.reactivestackresearchwork.domain.BookRecord;
import org.springframework.data.domain.Page;

public interface BookService {
    BookRecord save(BookRecord book);
    Page<BookRecord> getPaginated(int page, int size);

    void delete(String bookId);
}
