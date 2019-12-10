package com.koray.searchbackend.server.book.controller.books.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Locale;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IsbnBookDto extends BookDto {

    private String id;
    private String title;
    private List<String> authors;
    private double averageRating;
    private String isbn;
    private String isbn13;
    private Locale languageCode;
    private int numPages;
    private int ratingsCount;
    private int textReviewsCount;
    private List<String> tags;
}
