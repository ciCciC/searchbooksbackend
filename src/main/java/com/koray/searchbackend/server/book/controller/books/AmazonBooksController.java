package com.koray.searchbackend.server.book.controller.books;

import com.koray.searchbackend.server.book.service.IsbnBookService;
import com.koray.searchbackend.server.book.service.PopulateDbService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "${frontendhost}")
@RequestMapping("amazonbooks")
@RestController
public class AmazonBooksController extends BooksController {

    public AmazonBooksController(IsbnBookService isbnBookService, PopulateDbService populateDbService) {
        super(isbnBookService, populateDbService);
    }
}
