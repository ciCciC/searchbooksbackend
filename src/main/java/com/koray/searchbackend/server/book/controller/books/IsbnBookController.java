package com.koray.searchbackend.server.book.controller.books;

import com.koray.searchbackend.server.book.controller.books.dto.IsbnBookDto;
import com.koray.searchbackend.server.book.controller.books.dto.PageDto;
import com.koray.searchbackend.server.book.controller.books.dto.PageSummary;
import com.koray.searchbackend.server.book.controller.books.mapper.IsbnBookMapper;
import com.koray.searchbackend.server.book.controller.books.mapper.PageMapper;
import com.koray.searchbackend.server.book.domain.IsbnBook;
import com.koray.searchbackend.server.book.service.IsbnBookService;
import com.koray.searchbackend.server.book.service.PopulateDbService;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Optional;

@CrossOrigin(origins = "${frontendhost}")
@RequestMapping("isbnbooks")
@RestController
public class IsbnBookController {

    private IsbnBookService isbnBookService;
    private PopulateDbService populateDbService;

    public IsbnBookController(IsbnBookService isbnBookService,
                           PopulateDbService populateDbService) {
        this.isbnBookService = isbnBookService;
        this.populateDbService = populateDbService;
    }

    @GetMapping
    public Optional<PageDto<IsbnBookDto>> queryPage(@RequestParam int page){
        Optional<PageDto<IsbnBookDto>> toReturn = isbnBookService.queryForPage(page)
                .map(PageMapper.INSTANCE::isbnToDTO);
        return toReturn;
    }

//    @GetMapping("search/author/{name}") TODO!!
//    public Optional<PageDto<IsbnBookDto>> findByAuthorsName(@PathVariable String name){
//        Optional<Page<IsbnBook>> books = isbnBookService.findIsbnBooksByAuthorsName(name, PageRequest.of(0, 30));
//        return books.map(value -> value.map(IsbnBookMapper.INSTANCE::toDTO));
//    }

    @GetMapping("/search/title")
    public Optional<PageDto<IsbnBookDto>> searchBooksByTitle(@RequestParam String value, @RequestParam int page){
        Optional<PageSummary<IsbnBook>> pageSummary = isbnBookService.searchIsbnBookByTitle(value, page);
        return pageSummary.map(PageMapper.INSTANCE::isbnToDTO);
    }

    @GetMapping("/search/pages")
    public Optional<PageDto<IsbnBookDto>> searchBooksByAmountPages(@RequestParam String value, @RequestParam int page){
        Optional<PageSummary<IsbnBook>> pageSummary = isbnBookService.searchIsbnBookByTitle(value, page);
        return pageSummary.map(PageMapper.INSTANCE::isbnToDTO);
    }

    @GetMapping("/{id}")
    public Optional<IsbnBookDto> findById(@PathVariable String id){
        Optional<IsbnBook> optionalBook = isbnBookService.findById(id);
        return optionalBook.map(IsbnBookMapper.INSTANCE::toDTO);
    }

    @GetMapping("/populate")
    public String populate() throws IOException, URISyntaxException {
        populateDbService.populateISBNbooks();
        return "success";
    }
}
