package com.koray.searchbackend.server.book.repository;

import com.koray.searchbackend.server.book.data.IsbnBook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;
import java.util.Optional;

public interface IsbnBookRepository extends ElasticsearchRepository<IsbnBook, String> {

    @Query("{\"bool\": {\"must\": [{\"match\": {\"authors.name\": \"?0\"}}]}}")
    Page<IsbnBook> findByAuthorsName(String name, Pageable pageable);

    Optional<List<IsbnBook>> findBooksByTitle(String title, Pageable pageable);
    Optional<List<IsbnBook>> findBooksByTitleStartingWith(String title, Pageable pageable);
    Optional<List<IsbnBook>> findByTitleStartingWith(String title, Pageable pageable);
}
