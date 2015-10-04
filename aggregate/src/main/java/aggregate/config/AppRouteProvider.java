package aggregate.config;

import aggregate.app.FallbackController;
import aggregate.app.HelloWorldController;
import aggregate.common.health.HealthCheck;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.netflix.hystrix.contrib.rxnetty.metricsstream.HystrixMetricsStreamHandler;
import io.netty.buffer.ByteBuf;
import netflix.karyon.transport.http.SimpleUriRouter;
import netflix.karyon.transport.http.health.HealthCheckEndpoint;
import aggregate.app.RemoteCallController;

public class AppRouteProvider implements Provider<SimpleUriRouter<ByteBuf, ByteBuf>> {

    @Inject
    private HealthCheck healthCheck;

    @Inject
    private HelloWorldController helloWorldController;

    @Inject
    private RemoteCallController remoteCallController;

    @Inject
    private FallbackController fallbackController;

    @Override
    public SimpleUriRouter get() {
        SimpleUriRouter simpleUriRouter = new SimpleUriRouter();
        simpleUriRouter.addUri("/healthcheck", new HealthCheckEndpoint(healthCheck));
        simpleUriRouter.addUri("/message", remoteCallController);
        simpleUriRouter.addUri("/hystrix.stream", new HystrixMetricsStreamHandler<>(simpleUriRouter));
        simpleUriRouter.addUri("/hello", helloWorldController);
        simpleUriRouter.addUri("/fallback", fallbackController);
        return simpleUriRouter;
    }
}