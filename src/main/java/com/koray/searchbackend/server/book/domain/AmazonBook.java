package com.koray.searchbackend.server.book.domain;

import lombok.*;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
@Document(indexName = "amazonbook", type = "amazonbook")
public class AmazonBook extends Book {

    private String imgUrl;

    @Builder
    public AmazonBook(String id, String title, @Singular List<Author> authors,
                      String isbn, @Singular List<String> tags, String imgUrl) {
        super(id, title, authors, isbn, tags);
        this.imgUrl = imgUrl;
    }
}
