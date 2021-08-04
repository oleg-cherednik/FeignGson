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

import feign.RequestTemplate;
import feign.codec.Encoder;
import ru.olegcherednik.gson.utils.GsonDecorator;

import java.lang.reflect.Type;

/**
 * @author Oleg Cherednik
 * @since 30.01.2021
 */
public class GsonEncoder implements Encoder {

    private final GsonDecorator gson;

    public GsonEncoder(GsonDecorator gson) {
        this.gson = gson;
    }

    @Override
    public void encode(Object object, Type bodyType, RequestTemplate template) {
        template.body(gson.writeValue(object));
    }

}
