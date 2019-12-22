package com.koray.searchbackend.server.book.service;

import com.google.gson.Gson;
import com.koray.searchbackend.server.book.data.Author;
import com.koray.searchbackend.server.book.data.IsbnBook;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PopulateIsbnDbService {

    @Qualifier("elasticsearchTemplate")
    @Autowired
    private ElasticsearchTemplate es;

    public void populateISBNbooks() throws URISyntaxException, IOException {
        Gson gson = new Gson();

        Path booksCsvPath = Paths.get(Objects.requireNonNull(getClass()
                .getClassLoader()
                .getResource("books.csv")).toURI());

        List<IndexQuery> indexQueryList = Files.lines(booksCsvPath)
                .skip(1)
                .parallel()
                .map(line -> line.split(","))
                .filter(checkNum -> checkNum[3].length() < 5)
                .map(this::buildIsbnBookFromStringArr)
                .map(book -> toIquery(book, gson))
                .collect(Collectors.toList());

        es.bulkIndex(indexQueryList);
        es.refresh(IsbnBook.class.getSimpleName().toLowerCase());
        log.info("Bulk - insert - completed for IsbnBooks");
    }

    private IndexQuery toIquery(IsbnBook book, Gson gson) {
        StringBuilder indexName = new StringBuilder(IsbnBook.class.getSimpleName().toLowerCase());
        IndexQuery indexQuery = new IndexQuery();
        indexQuery.setId(book.getId());
        indexQuery.setSource(gson.toJson(book));
        indexQuery.setIndexName(indexName.toString());
        indexQuery.setType(indexName.toString());
        return indexQuery;
    }

    private IsbnBook buildIsbnBookFromStringArr(String[] b) {
        if (b[3].length() < 5) {
            return IsbnBook.builder()
                    .id(b[0])
                    .title(clean(b[1]))
                    .author(new Author(b[2]))
                    .averageRating(Double.parseDouble(b[3]))
                    .isbn(b[4])
                    .isbn13(b[5])
                    .languageCode(new Locale(b[6]))
                    .numPages(Integer.parseInt(b[7]))
                    .ratingsCount(Integer.parseInt(b[8]))
                    .textReviewsCount(Integer.parseInt(b[9]))
                    .tags(Arrays.asList("thriller", "action", "horror"))
                    .build();
        } else {
            return IsbnBook.builder()
                    .id(b[0])
                    .title(clean(b[1]))
                    .author(new Author(b[2]))
                    .averageRating(Double.parseDouble(b[4]))
                    .isbn(b[5])
                    .isbn13(b[6])
                    .languageCode(new Locale(b[7]))
                    .numPages(Integer.parseInt(b[8]))
                    .ratingsCount(Integer.parseInt(b[9]))
                    .textReviewsCount(Integer.parseInt(b[10]))
                    .tags(Arrays.asList("thriller", "action", "horror"))
                    .build();
        }
    }

    private String clean(String valueToClean) {
        StringBuilder replacedChars = replaceChars(valueToClean);
        return replacedChars.toString().contains("(") ?
                replacedChars.substring(0, replacedChars.indexOf("(")) :
                valueToClean;
    }

    private StringBuilder replaceChars(String value) {
        return new StringBuilder(value.replaceAll("[0-9#-]", ""));
    }
}
