package ru.olegcherednik.gson.feign.app.server;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.olegcherednik.gson.feign.app.dto.Book;

/**
 * @author Oleg Cherednik
 * @since 30.01.2021
 */
@RestController
public class BookController {

    @PostMapping("book")
    public Book createBook(@RequestBody Book book) {
        book.setResponse("BookController");
        return book;
    }
}
