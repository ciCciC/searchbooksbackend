package com.koray.searchbackend.server.book.controller.books.mapper;

import com.koray.searchbackend.server.book.controller.books.dto.AmazonBookDto;
import com.koray.searchbackend.server.book.controller.books.dto.IsbnBookDto;
import com.koray.searchbackend.server.book.controller.books.dto.PageDto;
import com.koray.searchbackend.server.book.controller.books.dto.PageSummary;
import com.koray.searchbackend.server.book.domain.AmazonBook;
import com.koray.searchbackend.server.book.domain.IsbnBook;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Mapper(uses = IsbnBookMapper.class)
public interface PageMapper {

    PageMapper INSTANCE = Mappers.getMapper(PageMapper.class);

    PageDto<IsbnBookDto> isbnToDTO(PageSummary<IsbnBook> pageSummary);
    PageDto<AmazonBookDto> amazonToDTO(PageSummary<AmazonBook> pageSummary);

    default List<AmazonBookDto> toAmazonBookDTOs(Stream<AmazonBook> amazonBookStream) {
        return amazonBookStream.map(IsbnBookMapper.INSTANCE::toDTO).collect(Collectors.toList());
    }

    default List<IsbnBookDto> toIsbnBookDTOs(Stream<IsbnBook> isbnBookStream) {
        return isbnBookStream.map(IsbnBookMapper.INSTANCE::toDTO).collect(Collectors.toList());
    }
}
