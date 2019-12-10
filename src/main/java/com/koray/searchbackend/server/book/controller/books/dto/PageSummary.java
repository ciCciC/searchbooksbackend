package com.koray.searchbackend.server.book.controller.books.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.stream.Stream;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PageSummary<T> {
    private long totalSizeIndex;
    private Stream<T> dataDtos;
}
