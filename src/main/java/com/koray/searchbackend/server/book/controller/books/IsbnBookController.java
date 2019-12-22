package com.koray.searchbackend.server.book.controller.books;

import com.koray.searchbackend.server.book.controller.books.dto.IsbnBookDto;
import com.koray.searchbackend.server.book.controller.books.dto.PageDto;
import com.koray.searchbackend.server.book.controller.books.dto.PageSummary;
import com.koray.searchbackend.server.book.controller.books.mapper.IsbnBookMapper;
import com.koray.searchbackend.server.book.controller.books.mapper.PageMapper;
import com.koray.searchbackend.server.book.data.IsbnBook;
import com.koray.searchbackend.server.book.service.IsbnBookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin(origins = "${frontendhost}")
@RestController
@RequestMapping("isbnbooks")
public class IsbnBookController extends BooksController<IsbnBookDto> {

    private IsbnBookService isbnBookService;

    public IsbnBookController(IsbnBookService isbnBookService) {
        this.isbnBookService = isbnBookService;
    }

    @GetMapping
    public ResponseEntity queryPage(@RequestParam int page){

        if(invalidPageInput(page)) {
            return sendNoContent();
        }

        Optional<PageDto<IsbnBookDto>> toReturn = isbnBookService.queryForPage(page)
                .map(PageMapper.INSTANCE::isbnToDTO);

        return ResponseEntity.ok(toReturn);
    }

//    @GetMapping("search/author/{name}") TODO!!
//    public Optional<PageDto<IsbnBookDto>> findByAuthorsName(@PathVariable String name){
//        Optional<Page<IsbnBook>> books = isbnBookService.findIsbnBooksByAuthorsName(name, PageRequest.of(0, 30));
//        return books.map(value -> value.map(IsbnBookMapper.INSTANCE::toDTO));
//    }

    @GetMapping("/search/title")
    public ResponseEntity searchBooksByTitle(@RequestParam String q, @RequestParam int page){

        if(invalidPageInput(page) || q.isEmpty()) {
            return sendNoContent();
        }

        Optional<PageSummary<IsbnBook>> pageSummary = isbnBookService.searchIsbnBookByTitle(q, page);
        return ResponseEntity.ok(pageSummary.map(PageMapper.INSTANCE::isbnToDTO));
    }

    @GetMapping("/search/pages")
    public Optional<PageDto<IsbnBookDto>> searchBooksByAmountPages(@RequestParam String q, @RequestParam int page){
        Optional<PageSummary<IsbnBook>> pageSummary = isbnBookService.searchIsbnBookByTitle(q, page);
        return pageSummary.map(PageMapper.INSTANCE::isbnToDTO);
    }

    @GetMapping("/{id}")
    public Optional<IsbnBookDto> findById(@PathVariable String id){
        Optional<IsbnBook> optionalBook = isbnBookService.findById(id);
        return optionalBook.map(IsbnBookMapper.INSTANCE::toDTO);
    }

    public ResponseEntity sendNoContent() {
        return ResponseEntity.noContent().build();
    }

    public boolean invalidPageInput(int page){
        return page < 0;
    }

//    @GetMapping("/populate")
//    public String populate() throws IOException, URISyntaxException {
//        populateDbService.populateISBNbooks();
//        return "success";
//    }
}
