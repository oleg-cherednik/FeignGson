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

import feign.Feign;
import feign.RequestInterceptor;
import feign.okhttp.OkHttpClient;
import feign.spring.SpringContract;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import ru.olegcherednik.gson.feign.app.server.SpringBootApp;
import ru.olegcherednik.gson.utils.GsonDecorator;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = SpringBootApp.class)
public abstract class BaseClientTest extends AbstractTestNGSpringContextTests {

    @LocalServerPort
    protected int randomServerPort;

    protected <T> T buildClient(Class<T> cls, GsonDecorator gson, RequestInterceptor requestInterceptor) {
        return Feign.builder()
                    .contract(new SpringContract())
                    .client(new OkHttpClient())
                    .encoder(new GsonEncoder(gson))
                    .decoder(new GsonDecoder(gson))
                    .requestInterceptor(requestInterceptor)
                    .target(cls, String.format("http://localhost:%d", randomServerPort));
    }

}
