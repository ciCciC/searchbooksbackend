package com.koray.searchbackend.server.book.service;

import com.koray.searchbackend.server.book.controller.books.dto.IsbnBookDto;
import com.koray.searchbackend.server.book.controller.books.fixture.IsbnBookFixture;
import com.koray.searchbackend.server.book.controller.books.mapper.IsbnBookMapper;
import com.koray.searchbackend.server.book.domain.Author;
import com.koray.searchbackend.server.book.domain.IsbnBook;
import org.apache.lucene.analysis.CharacterUtils;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

class PopulateIsbnDbServiceTest {

    @Test
    void populate() throws URISyntaxException, IOException {

        URI url = Objects.requireNonNull(getClass()
                .getClassLoader().getResource("book30.csv")).toURI();

        Path booksCsvPath = Paths.get(url);

        Files.lines(booksCsvPath, StandardCharsets.ISO_8859_1)
                .parallel()
                .limit(10000)
                .map(value -> separateData(value))
                .filter(value -> (value.length - 1)  > 8 && !value[9].isEmpty())
                .map(value -> cleanComma(value))
                .filter(value -> value.length  == 14 && isNumber(value[11]))
                .map(value -> reformList(value))
                .forEach(System.out::println);
    }

    private boolean isNumber(String s) {
        return s.matches("[1-9][0-9]") || s.matches("[0-9]");
    }

    private List<String> reformList(String [] value) {
        List<String> strings = new ArrayList<>();
        for (int i = 0; i < value.length; i++) {
            if(i == 0 || i == 2 || i == 4 || i == 6 || i == 8 || i == 10 || i == 12){
            } else {
                strings.add(value[i]);
            }
        }
        return strings;
    }

    private String[] separateData(String value) {
        return value.split("\"");
    }

    private String [] cleanComma(String [] values) {
        for (int i = 0; i < values.length; i++) {
            if(i != 9){
                values[i] = values[i].replace(",", "");
            }
        }
        return values;
    }
}
