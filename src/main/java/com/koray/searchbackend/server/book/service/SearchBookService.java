package com.koray.searchbackend.server.book.service;

import com.koray.searchbackend.server.book.controller.books.dto.PageSummary;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Stream;

import static org.elasticsearch.index.query.QueryBuilders.matchAllQuery;

@Service
public class SearchBookService {

    @Qualifier("elasticsearchTemplate")
    private ElasticsearchTemplate es;

    public SearchBookService(ElasticsearchTemplate es) {
        this.es = es;
    }

    public <T> Optional<PageSummary<T>> queryForPage(int pageIndex, Class<T> bookClass) {
        Stream<T> bookStream = es.queryForPage(getAllQuery(pageIndex, bookClass), bookClass).get();
        long totalSizeIndex = es.count(getCountQuery(), bookClass);
        return pageSummaryBuilder(bookStream, totalSizeIndex);
    }

    public <T> Optional<PageSummary<T>> queryByBookTitle(String title, int pageIndex, Class<T> bookClass) {
        Page<T> bookPage = es.queryForPage(getByTitleQuery(title, pageIndex, bookClass), bookClass);
        long totalSizeIndex = bookPage.getTotalElements();
        return pageSummaryBuilder(bookPage.get(), totalSizeIndex);
    }

//    public Optional<PageSummary> queryAuthorByName(String name){
//        PageSummary pageSummary = PageSummary.builder()
//                .streamOfData(Stream.of("123", "123"))
//                .totalSizeIndex(123)
//                .build();
//
//        return Optional.of(PageSummary.builder()
//                .streamOfData(Stream.of("123", "123"))
//                .totalSizeIndex(123)
//                .build());
//    }

    private <T> SearchQuery getAllQuery(int pageIndex, Class<T> bookClass) {
        return nativeSearchQueryBuilder(matchAllQuery(), pageIndex, bookClass);
    }

    private <T> SearchQuery getByTitleQuery(String title, int pageIndex, Class<T> bookClass) {
        QueryBuilder query = QueryBuilders.boolQuery()
                .should(QueryBuilders
                        .queryStringQuery(title+"*")
                        .field("title")
                        .boost(50));

        return nativeSearchQueryBuilder(query, pageIndex, bookClass);
    }

    private SearchQuery getCountQuery() {
        return new NativeSearchQueryBuilder()
                .withQuery(matchAllQuery())
                .build();
    }

    private <T> SearchQuery getByAuthorQuery(String author, int pageIndex, Class<T> bookClass) {
        QueryBuilder query = QueryBuilders.boolQuery()
                .should(QueryBuilders
                        .queryStringQuery(author+"*")
                        .field("author.name")
                        .boost(50));
        return nativeSearchQueryBuilder(query, pageIndex, bookClass);
    }

    private <T> NativeSearchQuery nativeSearchQueryBuilder(QueryBuilder query, int pageIndex, Class<T> bookClass){
        return new NativeSearchQueryBuilder()
                .withQuery(query)
                .withIndices(bookClass.getSimpleName().toLowerCase())
                .withTypes(bookClass.getSimpleName().toLowerCase())
                .withPageable(PageRequest.of(pageIndex, 30))
                .build();
    }

    private <T> Optional<PageSummary<T>> pageSummaryBuilder(Stream<T> bookStream, long totalSizeIndex){
        return Optional.ofNullable(PageSummary.<T>builder()
                .dataDtos(bookStream)
                .totalSizeIndex(totalSizeIndex)
                .build());
    }
}
