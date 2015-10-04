package service1.config;

import service1.common.health.HealthCheck;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.netflix.hystrix.contrib.rxnetty.metricsstream.HystrixMetricsStreamHandler;
import io.netty.buffer.ByteBuf;
import netflix.karyon.transport.http.SimpleUriRouter;
import netflix.karyon.transport.http.health.HealthCheckEndpoint;
import service1.app.ApplicationMessageHandler;

public class AppRouteProvider implements Provider<SimpleUriRouter<ByteBuf, ByteBuf>> {

    @Inject
    private HealthCheck healthCheck;

    @Inject
    private ApplicationMessageHandler applicationMessageHandler;

    @Override
    public SimpleUriRouter get() {
        SimpleUriRouter simpleUriRouter = new SimpleUriRouter();
        simpleUriRouter.addUri("/healthcheck", new HealthCheckEndpoint(healthCheck));
        simpleUriRouter.addUri("/message", applicationMessageHandler);
        simpleUriRouter.addUri("/hystrix.stream", new HystrixMetricsStreamHandler<>(simpleUriRouter));
        return simpleUriRouter;
    }
}