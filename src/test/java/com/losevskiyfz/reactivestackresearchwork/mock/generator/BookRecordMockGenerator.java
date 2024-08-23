package com.losevskiyfz.reactivestackresearchwork.mock.generator;

import com.github.javafaker.Faker;
import com.losevskiyfz.reactivestackresearchwork.domain.BookRecord;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BookRecordMockGenerator {

    private final static Faker faker = new Faker();

    public static BookRecord generateFakeBookRecord() {
        return BookRecord.builder()
                .id(faker.idNumber().valid())
                .type(faker.book().genre())
                .quantity(faker.number().numberBetween(1, 100))
                .authors(generateAuthors(faker.number().numberBetween(1, 7)))
                .name(faker.book().title())
                .pages(faker.number().numberBetween(100, 1000))
                .publisher(faker.book().publisher())
                .year(faker.number().numberBetween(1900, 2024))
                .city(faker.address().city())
                .department_id(faker.idNumber().valid())
                .summary(faker.lorem().paragraph())
                .room(faker.address().buildingNumber())
                .build();
    }

    private static String[] generateAuthors(int count) {
        return IntStream.range(0, count)
                .mapToObj(_ -> faker.book().author())
                .toArray(String[]::new);
    }

    public static List<BookRecord> generateFakeBookRecords(int count) {
        return IntStream.range(0, count)
                .mapToObj(_ -> generateFakeBookRecord())
                .collect(Collectors.toList());
    }

}
