package aggregate.config;

import aggregate.service.RemoteCallService;
import aggregate.service.RemoteCallServiceImpl;
import com.google.inject.AbstractModule;
import com.google.inject.Scopes;

public class AppModule extends AbstractModule {


    @Override
    protected void configure() {
        bind(RemoteCallService.class).to(RemoteCallServiceImpl.class).in(Scopes.SINGLETON);
    }
}
