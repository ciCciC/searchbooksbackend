package com.koray.searchbackend.server.book.service;

import com.koray.searchbackend.server.book.domain.AmazonBook;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@Slf4j
@Service
public class PopulateAmazonDbService {

    @Qualifier("elasticsearchTemplate")
    @Autowired
    private ElasticsearchTemplate es;

    public void populateAmazonBooks() throws URISyntaxException, IOException {

        Path booksCsvPath = Paths.get(Objects.requireNonNull(getClass()
                .getClassLoader().getResource("book30.csv")).toURI());

//        TODO saving data to elasticsearch
//        Stream<List<String> Files.lines(booksCsvPath, StandardCharsets.ISO_8859_1)
//                .parallel()
//                .map(this::separateData)
//                .filter(value -> (value.length - 1) > 8 && !value[9].isEmpty())
//                .map(this::cleanComma)
//                .filter(value -> value.length == 14 && isNumber(value[11]))
//                .map(this::reformList)
//                .map(this::buildFromStringArr)
//                .forEach(System.out::println);
    }

//    private AmazonBook buildFromStringArr(List<String> b) {
//        return AmazonBook.builder().
//    }

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
