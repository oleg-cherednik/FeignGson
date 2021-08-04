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

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

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
            assertThat(actual.getAuthor()).isEqualTo(book.getAuthor());                                                                 assertThat(actual.getResponse()).isEqualTo(BookController.class.getSimpleName());
            assertThat(requestInterceptor.body).isEqualTo(gson.writeValue(book));
        }
    }

    private static final class LocalRequestInterceptor implements RequestInterceptor {

        private String body;

        @Override
        public void apply(RequestTemplate template) {
            body = new String(template.body());
        }

    }

}
