package com.koray.searchbackend.server.book.controller.books;

import com.koray.searchbackend.server.book.controller.books.dto.AmazonBookDto;
import com.koray.searchbackend.server.book.controller.books.dto.PageDto;
import com.koray.searchbackend.server.book.controller.books.dto.PageSummary;
import com.koray.searchbackend.server.book.controller.books.mapper.PageMapper;
import com.koray.searchbackend.server.book.data.AmazonBook;
import com.koray.searchbackend.server.book.service.AmazonBookService;
import com.koray.searchbackend.server.book.service.PopulateAmazonDbService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Optional;

@CrossOrigin(origins = "${frontendhost}")
@RestController
@RequestMapping("amazonbooks")
public class AmazonBooksController extends BooksController<AmazonBookDto> {

    private AmazonBookService amazonBookService;
    private PopulateAmazonDbService populateAmazonDbService;

    public AmazonBooksController(AmazonBookService amazonBookService,
                                 PopulateAmazonDbService populateAmazonDbService) {
        this.amazonBookService = amazonBookService;
        this.populateAmazonDbService = populateAmazonDbService;
    }

    @GetMapping
    @Override
    public ResponseEntity queryPage(@RequestParam int page) {

        if(page < 0) {
            return ResponseEntity.noContent().build();
        }

        Optional<PageDto<AmazonBookDto>> toReturn = this.amazonBookService.queryForPage(page)
                .map(PageMapper.INSTANCE::amazonToDTO);
        return ResponseEntity.ok(toReturn);
    }

    @GetMapping("/search/title")
    @Override
    public ResponseEntity searchBooksByTitle(String q, int page) {
        if(invalidPageInput(page) || q.isEmpty()) {
            return sendNoContent();
        }

        Optional<PageSummary<AmazonBook>> pageSummary = amazonBookService.searchAmazonBookByTitle(q, page);
        return ResponseEntity.ok(pageSummary.map(PageMapper.INSTANCE::amazonToDTO));
    }

    @Override
    public Optional<PageDto<AmazonBookDto>> searchBooksByAmountPages(String q, int page) {
        return Optional.empty();
    }

    @Override
    public Optional<AmazonBookDto> findById(String id) {
        return Optional.empty();
    }

    @GetMapping("/populate")
    public String populate() throws IOException, URISyntaxException {
        populateAmazonDbService.populateAmazonBooks();
        return "success";
    }

    public ResponseEntity sendNoContent() {
        return ResponseEntity.noContent().build();
    }

    public boolean invalidPageInput(int page){
        return page < 0;
    }
}
