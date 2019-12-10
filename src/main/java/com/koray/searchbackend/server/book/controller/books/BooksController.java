package com.koray.searchbackend.server.book.controller.books;

import com.koray.searchbackend.server.book.controller.books.dto.PageDto;
import com.koray.searchbackend.server.book.service.PopulateIsbnDbService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Optional;


public abstract class BooksController<R> {

    private PopulateIsbnDbService populateIsbnDbService;

    abstract Optional<PageDto<R>> queryPage(@RequestParam int page);
    abstract Optional<PageDto<R>> searchBooksByTitle(@RequestParam String q, @RequestParam int page);
    abstract Optional<PageDto<R>> searchBooksByAmountPages(@RequestParam String q, @RequestParam int page);
    abstract Optional<R> findById(@PathVariable String id);

    @GetMapping("/populateIsbn")
    public String populate() throws IOException, URISyntaxException {
        populateIsbnDbService.populateISBNbooks();
        return "success";
    }
}
