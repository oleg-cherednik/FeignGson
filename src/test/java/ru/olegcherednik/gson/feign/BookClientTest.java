package ru.olegcherednik.gson.feign;

import org.springframework.context.annotation.Import;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.olegcherednik.gson.feign.app.client.BookClient;
import ru.olegcherednik.gson.feign.app.dto.Book;
import ru.olegcherednik.gson.feign.app.server.BookController;

@Test
@Import(BookController.class)
public class BookClientTest extends BaseClientTest {

    private BookClient client;

    @BeforeMethod
    public void setUp() {
        client = buildClient(BookClient.class);
    }

    public void foo() {
        Book book = new Book();
        book.setTitle("title");
        book.setAuthor("author");

        Book actual = client.createBook(book);

        int a = 0;
        a++;
    }

//    public void bar() {
//        Book book = new Book();
//        book.setTitle("title");
//        book.setAuthor("author");
//
//        Book actual = client.createBook(book);
//
//        int a = 0;
//        a++;
//    }

}
