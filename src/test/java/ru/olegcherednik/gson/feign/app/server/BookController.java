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
package ru.olegcherednik.gson.feign.app.server;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.olegcherednik.gson.feign.app.dto.Book;

import java.util.List;
import java.util.Map;

/**
 * @author Oleg Cherednik
 * @since 30.01.2021
 */
@RestController
public class BookController {

    @PostMapping("book")
    public Book createBook(@RequestBody Book book) {
        book.setResponse(BookController.class.getSimpleName());
        return book;
    }

    @PostMapping("book_list")
    public List<Book> bookList(@RequestBody List<Book> books) {
        int i = 1;

        for (Book book : books)
            book.setResponse("BookController" + i++);

        return books;
    }

    @PostMapping("book_map")
    public Map<Integer, List<Book>> bookMap(@RequestBody Map<Integer, List<Book>> books) {
        for (Map.Entry<Integer, List<Book>> entry : books.entrySet()) {
            int i = 1;

            for (Book book : entry.getValue())
                book.setResponse("BookController_" + entry.getKey() + '_' + i++);
        }

        return books;
    }

    @GetMapping("book_null")
    public Book bookNull() {
        return null;
    }

    @GetMapping("book_not_found")
    public ResponseEntity<Book> bookNotFound() {
        return ResponseEntity.notFound().build();
    }

    @GetMapping("book_list_not_found")
    public ResponseEntity<List<Book>> bookListNotFound() {
        return ResponseEntity.notFound().build();
    }

    @GetMapping("book_map_not_found")
    public ResponseEntity<Map<Integer, List<Book>>> bookMapNotFound() {
        return ResponseEntity.notFound().build();
    }

}
