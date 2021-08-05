/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package ru.olegcherednik.gson.feign;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Import;
import org.testng.annotations.Test;
import ru.olegcherednik.gson.feign.app.client.BookClient;
import ru.olegcherednik.gson.feign.app.dto.Book;
import ru.olegcherednik.gson.feign.app.server.BookController;
import ru.olegcherednik.gson.utils.GsonDecorator;
import ru.olegcherednik.gson.utils.GsonUtilsHelper;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@Test
@Import(BookController.class)
public class BookClientTest extends BaseClientTest {

    public void shouldUseGivenGsonDecoratorWhenWorkWithJson() {
        for (GsonDecorator gson : Arrays.asList(GsonUtilsHelper.createGsonDecorator(),
                GsonUtilsHelper.createPrettyPrintGsonDecorator())) {
            LocalRequestInterceptor requestInterceptor = new LocalRequestInterceptor();

            BookClient client = buildClient(BookClient.class, gson, requestInterceptor);
            Book book = new Book();
            book.setTitle("title");
            book.setAuthor("author");
            Book actual = client.createBook(book);
            assertThat(actual).isNotNull();
            assertThat(actual.getTitle()).isEqualTo(book.getTitle());
            assertThat(actual.getAuthor()).isEqualTo(book.getAuthor());
            assertThat(actual.getResponse()).isEqualTo(BookController.class.getSimpleName());
            assertThat(requestInterceptor.body).isEqualTo(gson.writeValue(book));
        }
    }

    public void shouldReceiveObjectWhenSendObject() {
        BookClient client = buildClient(BookClient.class);
        Book actual = client.createBook(new Book("title", "author"));
        assertThat(actual).isNotNull();
        assertThat(actual).isEqualTo(new Book("title", "author", "BookController"));
    }

    public void shouldReceiveListWhenSendList() {
        BookClient client = buildClient(BookClient.class);
        List<Book> actual = client.bookList(Arrays.asList(
                new Book("title1", "author1"),
                new Book("title2", "author2")));

        assertThat(actual).isNotNull();
        assertThat(actual).hasSize(2);
        assertThat(actual).containsExactly(
                new Book("title1", "author1", "BookController1"),
                new Book("title2", "author2", "BookController2"));
    }

    public void shouldReceiveMapWhenSendMap() {
        Map<Integer, List<Book>> books = new HashMap<>();
        books.put(1, Arrays.asList(
                new Book("title1", "author1"),
                new Book("title2", "author2")));
        books.put(2, Arrays.asList(
                new Book("title3", "author3"),
                new Book("title4", "author4")));

        BookClient client = buildClient(BookClient.class);
        Map<Integer, List<Book>> actual = client.bookMap(books);
        assertThat(actual).isNotNull();
        assertThat(actual).hasSize(2);
        assertThat(actual.get(1)).containsExactly(
                new Book("title1", "author1", "BookController_1_1"),
                new Book("title2", "author2", "BookController_1_2"));
        assertThat(actual.get(2)).containsExactly(
                new Book("title3", "author3", "BookController_2_1"),
                new Book("title4", "author4", "BookController_2_2"));
    }

    public void shouldReceiveNullWhenSendNull() {
        BookClient client = buildClient(BookClient.class);
        Book actual = client.bookNull();
        assertThat(actual).isNull();
    }

    public void shouldReceiveDefaultObjectWhenNotFound() {
        BookClient client = buildClient(BookClient.class);
        assertThat(client.bookNotFound()).isNull();
        assertThat(client.bookListNotFound()).isEmpty();
        assertThat(client.bookMapNotFound()).isEmpty();
    }

    private static final class LocalRequestInterceptor implements RequestInterceptor {

        private String body;

        @Override
        public void apply(RequestTemplate template) {
            body = new String(template.body());
        }

    }

}
