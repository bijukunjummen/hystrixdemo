package service1.governator;

import service1.config.CustomKaryonEurekaModule;
import com.google.inject.Singleton;
import com.netflix.governator.annotations.Modules;
import netflix.adminresources.resources.KaryonWebAdminModule;
import netflix.karyon.KaryonBootstrap;
import netflix.karyon.ShutdownModule;
import netflix.karyon.archaius.ArchaiusBootstrap;
import netflix.karyon.servo.KaryonServoModule;
import service1.common.health.HealthCheck;
import service1.config.AppModule;
import service1.config.KaryonAppModule;

@ArchaiusBootstrap
@KaryonBootstrap(name = "service1", healthcheck = HealthCheck.class)
@Singleton
@Modules(include = {
        ShutdownModule.class,
        KaryonServoModule.class,
        KaryonWebAdminModule.class,
        CustomKaryonEurekaModule.class, // Uncomment this to enable Eureka client.
        AppModule.class,
        KaryonAppModule.class
})
public interface SampleGovernatorApp {}
