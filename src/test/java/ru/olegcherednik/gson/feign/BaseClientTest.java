package ru.olegcherednik.gson.feign;

import feign.Feign;
import feign.Logger;
import feign.okhttp.OkHttpClient;
import feign.spring.SpringContract;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import ru.olegcherednik.gson.feign.app.server.SpringBootApp;
import ru.olegcherednik.gson.utils.GsonDecorator;
import ru.olegcherednik.gson.utils.GsonUtilsHelper;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = SpringBootApp.class)
public abstract class BaseClientTest extends AbstractTestNGSpringContextTests {

    @LocalServerPort
    protected int randomServerPort;

    protected <T> T buildClient(Class<T> clientClass) {
        GsonDecorator gson = GsonUtilsHelper.createGsonDecorator();
//        ObjectMapper objectMapper = new ObjectMapper()
//                .registerModule(new Jdk8Module())
//                .registerModule(new JavaTimeModule());

        return Feign.builder()
                    .contract(new SpringContract())
                    .client(new OkHttpClient())
                    .encoder(new GsonEncoder(gson))
                    .decoder(new GsonDecoder(gson))
//                    .encoder(new JacksonEncoder(objectMapper))
//                    .decoder(new JacksonDecoder(objectMapper))
//                    .logger(new Slf4jLogger(FileSetClient.class))
                    .logLevel(Logger.Level.FULL)
                    .target(clientClass, String.format("http://localhost:%d", randomServerPort));
    }

}
