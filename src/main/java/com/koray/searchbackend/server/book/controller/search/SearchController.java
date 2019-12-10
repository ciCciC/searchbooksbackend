package com.koray.searchbackend.server.book.controller.search;

import com.koray.searchbackend.server.book.controller.books.dto.PageDto;
import com.koray.searchbackend.server.book.controller.books.dto.PageSummary;
import com.koray.searchbackend.server.book.controller.books.mapper.PageMapper;
import com.koray.searchbackend.server.book.domain.IsbnBook;
import com.koray.searchbackend.server.book.service.SearchBookService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "${frontendhost}")
@RequestMapping("search")
@RestController
public class SearchController {

    private SearchBookService searchBookService;

    public SearchController(SearchBookService searchBookService) {
        this.searchBookService = searchBookService;
    }

    @GetMapping("books/title")
    public Optional<PageDto> searchBooksByTitle(@RequestParam String value, @RequestParam int page){
        Optional<PageSummary<IsbnBook>> pageSummary = searchBookService.queryByBookTitle(value, page, IsbnBook.class);
        return pageSummary.map(PageMapper.INSTANCE::isbnToDTO);
    }

//    @GetMapping("/author")
//    public Optional<PageDto> searchAuthorByTitle(@RequestParam String value){
//        Optional<PageSummary> pageSummary = searchIsbnBookService.queryByBookTitle(value, page);
//        return pageSummary.map(PageMapper.INSTANCE::toDTO);
//    }

//    @GetMapping("/search/pages")
//    public Optional<PageDto> searchBooksByAmountPages(@RequestParam String q, @RequestParam int page){
//        Optional<PageSummary> pageSummary = searchIsbnBookService.queryByBookTitle(q, page);
//        return pageSummary.map(PageMapper.INSTANCE::toDTO);
//    }

}
