package com.koray.searchbackend.server.book.repository;

import com.koray.searchbackend.server.book.domain.AmazonBook;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface AmazonBookRepository extends ElasticsearchRepository<AmazonBook, String> {
}
