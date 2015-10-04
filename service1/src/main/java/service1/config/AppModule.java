package service1.config;

import service1.service.MessageHandlerService;
import service1.service.MessageHandlerServiceImpl;
import com.google.inject.AbstractModule;
import com.google.inject.Scopes;

public class AppModule extends AbstractModule {


    @Override
    protected void configure() {
        bind(MessageHandlerService.class).to(MessageHandlerServiceImpl.class).in(Scopes.SINGLETON);
    }
}
