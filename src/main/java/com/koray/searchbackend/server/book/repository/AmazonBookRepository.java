package com.koray.searchbackend.server.book.repository;

import com.koray.searchbackend.server.book.data.AmazonBook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface AmazonBookRepository extends ElasticsearchRepository<AmazonBook, String> {

    @Query("{\"bool\": {\"must\": [{\"match\": {\"authors.name\": \"?0\"}}]}}")
    Page<AmazonBook> findByAuthorsName(String authorName, Pageable pageable);
}
