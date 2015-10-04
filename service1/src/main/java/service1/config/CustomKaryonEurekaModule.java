package service1.config;

import com.netflix.appinfo.providers.MyDataCenterInstanceConfigProvider;
import com.netflix.discovery.providers.DefaultEurekaClientConfigProvider;
import netflix.karyon.eureka.KaryonEurekaModule;

public class CustomKaryonEurekaModule extends KaryonEurekaModule {
    protected void configureEureka() {
        bindEurekaNamespace().toInstance("eureka.");
        bindEurekaInstanceConfig().toProvider(MyDataCenterInstanceConfigProvider.class);
        bindEurekaClientConfig().toProvider(DefaultEurekaClientConfigProvider.class);
    }

}
