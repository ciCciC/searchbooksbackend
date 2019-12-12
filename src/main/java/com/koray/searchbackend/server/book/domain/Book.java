package com.koray.searchbackend.server.book.domain;

import lombok.*;
import org.springframework.data.annotation.Id;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Book {

    @Id
    private String id;
    private String title;

    private List<Author> authors;
    private String isbn;

    private List<String> tags;
}
