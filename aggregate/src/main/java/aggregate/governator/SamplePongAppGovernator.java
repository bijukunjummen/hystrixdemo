package aggregate.governator;

import aggregate.config.CustomKaryonEurekaModule;
import com.google.inject.Singleton;
import com.netflix.governator.annotations.Modules;
import netflix.adminresources.resources.KaryonWebAdminModule;
import netflix.karyon.KaryonBootstrap;
import netflix.karyon.ShutdownModule;
import netflix.karyon.archaius.ArchaiusBootstrap;
import netflix.karyon.eureka.KaryonEurekaModule;
import netflix.karyon.servo.KaryonServoModule;
import aggregate.common.health.HealthCheck;
import aggregate.config.AppModule;
import aggregate.config.KaryonAppModule;

@ArchaiusBootstrap
@KaryonBootstrap(name = "aggregateapp", healthcheck = HealthCheck.class)
@Singleton
@Modules(include = {
//        ShutdownModule.class,
        KaryonServoModule.class,
        KaryonWebAdminModule.class,
        CustomKaryonEurekaModule.class, // Uncomment this to enable Eureka client.
        AppModule.class,
        KaryonAppModule.class
})
public interface SamplePongAppGovernator {}
