package com.koray.searchbackend.server.book.service;

import com.google.gson.Gson;
import com.koray.searchbackend.server.book.data.AmazonBook;
import com.koray.searchbackend.server.book.data.Author;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PopulateAmazonDbService {

    @Qualifier("elasticsearchTemplate")
    @Autowired
    private ElasticsearchTemplate es;

    private int num;

    public void populateAmazonBooks() throws URISyntaxException, IOException {
        Gson gson = new Gson();

        Path booksCsvPath = Paths.get(Objects.requireNonNull(getClass()
                .getClassLoader().getResource("book30.csv")).toURI());

//        TODO saving data to elasticsearch
        List<IndexQuery> indexQueryList = Files.lines(booksCsvPath, StandardCharsets.ISO_8859_1)
                .parallel()
                .map(this::separateData)
                .filter(value -> (value.length - 1) > 8 && !value[9].isEmpty())
                .map(this::cleanComma)
                .filter(value -> value.length == 14 && isNumber(value[11]))
                .map(this::reformList)
                .map(this::buildFromStringArr)
                .map(book -> toIquery(book, gson))
                .collect(Collectors.toList());

        es.bulkIndex(indexQueryList);
        es.refresh(AmazonBook.class.getSimpleName().toLowerCase());
        log.info("Bulk - insert - completed for Amazonbooks");
    }

    private IndexQuery toIquery(AmazonBook book, Gson gson) {
        StringBuilder indexName = new StringBuilder(AmazonBook.class.getSimpleName().toLowerCase());
        IndexQuery indexQuery = new IndexQuery();
        indexQuery.setId(book.getId());
        indexQuery.setSource(gson.toJson(book));
        indexQuery.setIndexName(indexName.toString());
        indexQuery.setType(indexName.toString());
        return indexQuery;
    }

    private AmazonBook buildFromStringArr(List<String> b) {
        return AmazonBook.builder()
                .id(""+this.num++)
                .isbn(b.get(0))
                .imgUrl(b.get(2))
                .title(b.get(3))
                .author(new Author(b.get(4)))
                .tag(b.get(b.size() - 1))
                .build();
    }

    private List<String> reformList(String[] value) {
        List<String> strings = new ArrayList<>();
        for (int i = 0; i < value.length; i++) {
            if (i == 0 || i == 2 || i == 4 || i == 6 || i == 8 || i == 10 || i == 12) {
            } else {
                strings.add(value[i]);
            }
        }
        return strings;
    }

    private boolean isNumber(String s) {
        return s.matches("[1-9][0-9]") || s.matches("[0-9]");
    }

    private String[] separateData(String value) {
        return value.split("\"");
    }

    private String[] cleanComma(String[] values) {
        for (int i = 0; i < values.length; i++) {
            if (i != 9) {
                values[i] = values[i].replace(",", "");
            }
        }
        return values;
    }
}
