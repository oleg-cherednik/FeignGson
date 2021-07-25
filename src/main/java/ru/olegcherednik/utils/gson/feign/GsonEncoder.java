package ru.olegcherednik.utils.gson.feign;

import feign.RequestTemplate;
import feign.codec.Encoder;
import ru.olegcherednik.gson.GsonDecorator;

import java.lang.reflect.Type;

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
