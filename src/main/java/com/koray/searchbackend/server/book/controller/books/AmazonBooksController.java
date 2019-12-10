package com.koray.searchbackend.server.book.controller.books;

import com.koray.searchbackend.server.book.controller.books.dto.AmazonBookDto;
import com.koray.searchbackend.server.book.controller.books.dto.PageDto;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@CrossOrigin(origins = "${frontendhost}")
@RestController
@RequestMapping("amazonbooks")
public class AmazonBooksController extends BooksController<AmazonBookDto> {

    public AmazonBooksController() {
    }

    @Override
    Optional<PageDto<AmazonBookDto>> queryPage(int page) {
        return Optional.empty();
    }

    @Override
    Optional<PageDto<AmazonBookDto>> searchBooksByTitle(String q, int page) {
        return Optional.empty();
    }

    @Override
    Optional<PageDto<AmazonBookDto>> searchBooksByAmountPages(String q, int page) {
        return Optional.empty();
    }

    @Override
    Optional<AmazonBookDto> findById(String id) {
        return Optional.empty();
    }
}
