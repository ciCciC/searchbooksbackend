package com.koray.searchbackend.server.book.controller.books.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BookDto {

    private String id;
    private String title;
    private List<String> authors;
    private String isbn;
    private List<String> tags;
}
