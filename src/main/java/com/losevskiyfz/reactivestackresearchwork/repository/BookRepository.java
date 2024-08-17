package com.losevskiyfz.reactivestackresearchwork.repository;

import com.losevskiyfz.reactivestackresearchwork.domain.BookRecord;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BookRepository extends MongoRepository<BookRecord, String> {
}
