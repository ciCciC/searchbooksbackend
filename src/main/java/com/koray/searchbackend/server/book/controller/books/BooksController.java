package com.koray.searchbackend.server.book.controller.books;

import com.koray.searchbackend.server.book.controller.books.dto.PageDto;
import com.koray.searchbackend.server.book.service.PopulateIsbnDbService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Optional;

public abstract class BooksController<R> {

    private PopulateIsbnDbService populateIsbnDbService;

    abstract ResponseEntity<Optional<PageDto<R>>> queryPage(@RequestParam int page);
    abstract ResponseEntity<Optional<PageDto<R>>> searchBooksByTitle(@RequestParam String q, @RequestParam int page);
    abstract Optional<PageDto<R>> searchBooksByAmountPages(@RequestParam String q, @RequestParam int page);
    abstract Optional<R> findById(@PathVariable String id);

    @GetMapping("/populateIsbn")
    public String populate() throws IOException, URISyntaxException {
        populateIsbnDbService.populateISBNbooks();
        return "success";
    }
}
