package com.koray.searchbackend.server.book.controller.books.fixture;

import com.koray.searchbackend.server.book.data.Author;
import com.koray.searchbackend.server.book.data.IsbnBook;

import java.util.Arrays;
import java.util.Locale;

public class IsbnBookFixture {

    public static IsbnBook getDefault(){
        return IsbnBook.builder()
                .id("1")
                .title("Harry Potter and the Half-Blood Prince (Harry Potter  #6)")
                .author(new Author("J.K. Rowling-Mary GrandPré"))
                .averageRating(4.56)
                .isbn("0439785960")
                .isbn13("9780439785969")
                .languageCode(new Locale("eng"))
                .numPages(652)
                .ratingsCount(1944099)
                .textReviewsCount(26249)
                .tags(Arrays.asList("thriller", "action"))
                .build();
    }

    public static IsbnBook getDefaultWithId(String id){
        IsbnBook book = getDefault();
        book.setId(id);
        return book;
    }
}
