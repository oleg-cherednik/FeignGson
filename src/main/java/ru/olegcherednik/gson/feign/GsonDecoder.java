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

import feign.Response;
import feign.Util;
import feign.codec.Decoder;
import ru.olegcherednik.gson.utils.GsonDecorator;

import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;

/**
 * @author Oleg Cherednik
 * @since 30.01.2021
 */
public class GsonDecoder implements Decoder {

    protected static final int HTTP_STATUS_NOT_FOUND = 404;

    private final GsonDecorator gson;

    public GsonDecoder(GsonDecorator gson) {
        this.gson = gson;
    }

    @Override
    public Object decode(Response response, Type type) throws IOException {
        if (response.status() == HTTP_STATUS_NOT_FOUND)
            return Util.emptyValueOf(type);
        if (response.body() == null)
            return null;
        try (Reader in = response.body().asReader(Util.UTF_8)) {
            return gson.read(in, type);
        }
    }
}
