package com.koray.searchbackend.server.book.service;

import com.koray.searchbackend.server.book.controller.books.dto.PageSummary;
import com.koray.searchbackend.server.book.data.AmazonBook;
import com.koray.searchbackend.server.book.repository.AmazonBookRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AmazonBookService {

    @Qualifier("elasticsearchTemplate")
    private ElasticsearchTemplate es;
    private AmazonBookRepository amazonBookRepository;
    private SearchBookService searchBookService;

    public AmazonBookService(AmazonBookRepository amazonBookRepository,
                             ElasticsearchTemplate elasticsearchTemplate,
                             SearchBookService searchBookService) {
        this.amazonBookRepository = amazonBookRepository;
        this.es = elasticsearchTemplate;
        this.searchBookService = searchBookService;
    }

    public Optional<PageSummary<AmazonBook>> queryForPage(int pageIndex) {
        return searchBookService.queryForPage(pageIndex, AmazonBook.class);
    }

    public AmazonBook save(AmazonBook book) {
        return amazonBookRepository.save(book);
    }

    public void delete(AmazonBook book) {
        amazonBookRepository.delete(book);
    }

    public Optional<AmazonBook> findById(String id) {
        return amazonBookRepository.findById(id);
    }

    public Optional<Page<AmazonBook>> findAmazonBooksByAuthorsName(String author, PageRequest pageRequest) {
        return Optional.ofNullable(amazonBookRepository.findByAuthorsName(author, pageRequest));
    }

    public Optional<PageSummary<AmazonBook>> searchAmazonBookByTitle(String title, int pageIndex) {
        return searchBookService.queryByBookTitle(title, pageIndex, AmazonBook.class);
    }

}
