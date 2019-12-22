package com.koray.searchbackend.server.book.controller.books.mapper;

import com.koray.searchbackend.server.book.controller.books.dto.AmazonBookDto;
import com.koray.searchbackend.server.book.controller.books.dto.IsbnBookDto;
import com.koray.searchbackend.server.book.data.AmazonBook;
import com.koray.searchbackend.server.book.data.Author;
import com.koray.searchbackend.server.book.data.IsbnBook;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper
public interface IsbnBookMapper {

    IsbnBookMapper INSTANCE = Mappers.getMapper(IsbnBookMapper.class);

    IsbnBookDto toDTO(IsbnBook isbnBook);
    AmazonBookDto toDTO(AmazonBook amazonBook);

    default List<String> artistSimplifiedToStringList(List<Author> artists) {
        return artists.stream().map(Author::getName).collect(Collectors.toList());
    }
}
