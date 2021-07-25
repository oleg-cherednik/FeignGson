package ru.olegcherednik.gson.feign;

import feign.Response;
import feign.Util;
import feign.codec.Decoder;
import ru.olegcherednik.gson.utils.GsonDecorator;

import java.io.IOException;
import java.lang.reflect.Type;

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

//        try (Reader in = response.body().asReader(StandardCharsets.UTF_8)) {
//            return gson.readValue(in, type);
//        }

        return null;
    }
}
