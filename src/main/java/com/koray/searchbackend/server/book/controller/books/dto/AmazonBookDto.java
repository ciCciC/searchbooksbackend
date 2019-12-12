package com.koray.searchbackend.server.book.controller.books.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AmazonBookDto extends BookDto{

    private String imgUrl;

}
