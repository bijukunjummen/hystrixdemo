package aggregate.config;

import aggregate.service.RemoteCallService;
import com.google.inject.AbstractModule;
import feign.Feign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;

public class AppModule extends AbstractModule {


    @Override
    protected void configure() {
        RemoteCallService remoteCallService = Feign.builder()
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .target(RemoteCallService.class, "http://127.0.0.1:8889");
        bind(RemoteCallService.class).toInstance(remoteCallService);
    }
}
