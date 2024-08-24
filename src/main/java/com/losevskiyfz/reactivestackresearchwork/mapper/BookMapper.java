package com.losevskiyfz.reactivestackresearchwork.mapper;

import com.losevskiyfz.reactivestackresearchwork.domain.Book;
import com.losevskiyfz.reactivestackresearchwork.domain.PostBookDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BookMapper {
    @Mapping(target = "id", ignore = true)
    Book toBook(PostBookDto dto);
    PostBookDto toPostBookDto(Book bookRecord);
}
