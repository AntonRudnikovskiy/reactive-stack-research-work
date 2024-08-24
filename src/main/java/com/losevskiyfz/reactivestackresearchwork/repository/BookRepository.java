package com.losevskiyfz.reactivestackresearchwork.repository;

import com.losevskiyfz.reactivestackresearchwork.domain.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface BookRepository extends MongoRepository<Book, String> {

    @Query("{$or: ["
            + "{'name': {$regex: ?0, $options: 'i'}},"
            + "{'authors': {$regex: ?0, $options: 'i'}},"
            + "{'type': {$regex: ?0, $options: 'i'}},"
            + "{'department_id': {$regex: ?0, $options: 'i'}},"
            + "{'summary': {$regex: ?0, $options: 'i'}},"
            + "{'publisher': {$regex: ?0, $options: 'i'}},"
            + "{'year': {$regex: ?0, $options: 'i'}},"
            + "{'city': {$regex: ?0, $options: 'i'}},"
            + "{'quantity': {$regex: ?0, $options: 'i'}},"
            + "{'room': {$regex: ?0, $options: 'i'}},"
            + "{'pages': {$regex: ?0, $options: 'i'}},"
            + "]}")
    Page<Book> getByTextPattern(Pageable pageable, String text);

}
