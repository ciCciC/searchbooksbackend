package com.koray.searchbackend.server.book.service;

import com.koray.searchbackend.server.book.controller.books.dto.PageSummary;
import com.koray.searchbackend.server.book.domain.IsbnBook;
import com.koray.searchbackend.server.book.repository.IsbnBookRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class IsbnBookService {

    @Qualifier("elasticsearchTemplate")
    private ElasticsearchTemplate es;
    private IsbnBookRepository bookRepository;
    private SearchBookService searchBookService;

    public IsbnBookService(IsbnBookRepository bookRepository,
                           ElasticsearchTemplate elasticsearchTemplate,
                           SearchBookService searchBookService) {
        this.bookRepository = bookRepository;
        this.es = elasticsearchTemplate;
        this.searchBookService = searchBookService;
    }

    public Optional<PageSummary<IsbnBook>> queryForPage(int pageIndex) {
        return searchBookService.queryForPage(pageIndex, IsbnBook.class);
    }

    public IsbnBook save(IsbnBook book) {
        return bookRepository.save(book);
    }

    public void delete(IsbnBook book) {
        bookRepository.delete(book);
    }

    public Optional<IsbnBook> findById(String id) {
        return bookRepository.findById(id);
    }

    public Optional<Page<IsbnBook>> findIsbnBooksByAuthorsName(String author, PageRequest pageRequest) {
        return Optional.ofNullable(bookRepository.findByAuthorsName(author, pageRequest));
    }

    public Optional<PageSummary<IsbnBook>> searchIsbnBookByTitle(String title, int pageIndex) {
        return searchBookService.queryByBookTitle(title, pageIndex, IsbnBook.class);
    }
}
