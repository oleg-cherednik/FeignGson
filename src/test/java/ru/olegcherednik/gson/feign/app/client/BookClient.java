package ru.olegcherednik.gson.feign.app.client;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.olegcherednik.gson.feign.app.dto.Book;

/**
 * @author Oleg Cherednik
 * @since 30.01.2021
 */
@SuppressWarnings("InterfaceNeverImplemented")
public interface BookClient {

    @PostMapping(value = "book", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    Book createBook(@RequestBody Book book);
}
