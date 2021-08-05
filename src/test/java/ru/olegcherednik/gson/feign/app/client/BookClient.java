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
package ru.olegcherednik.gson.feign.app.client;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.olegcherednik.gson.feign.app.dto.Book;

import java.util.List;
import java.util.Map;

/**
 * @author Oleg Cherednik
 * @since 30.01.2021
 */
@SuppressWarnings("InterfaceNeverImplemented")
public interface BookClient {

    @PostMapping(value = "book", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    Book createBook(@RequestBody Book book);

    @PostMapping(value = "book_list", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    List<Book> bookList(List<Book> books);

    @PostMapping(value = "book_map", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    Map<Integer, List<Book>> bookMap(Map<Integer, List<Book>> books);

    @GetMapping("book_null")
    Book bookNull();

    @GetMapping("book_not_found")
    Book bookNotFound();

    @GetMapping("book_list_not_found")
    List<Book> bookListNotFound();

    @GetMapping("book_map_not_found")
    Map<Integer, List<Book>> bookMapNotFound();

}
