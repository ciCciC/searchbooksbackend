package com.koray.searchbackend.server.book.domain;

import lombok.*;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.List;
import java.util.Locale;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
@Document(indexName = "isbnbook", type = "isbnbook")
public class IsbnBook extends Book {

    private double averageRating;
    private String isbn13;
    private Locale languageCode;
    private int numPages;
    private int ratingsCount;
    private int textReviewsCount;

    @Builder
    public IsbnBook(String id, String title, @Singular List<Author> authors, String isbn,
                    @Singular List<String> tags, double averageRating, String isbn13,
                    Locale languageCode, int numPages, int ratingsCount,
                    int textReviewsCount) {
        super(id, title, authors, isbn, tags);
        this.averageRating = averageRating;
        this.isbn13 = isbn13;
        this.languageCode = languageCode;
        this.numPages = numPages;
        this.ratingsCount = ratingsCount;
        this.textReviewsCount = textReviewsCount;
    }
}
