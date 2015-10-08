package aggregate.config;

import aggregate.app.*;
import aggregate.common.health.HealthCheck;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.netflix.hystrix.contrib.rxnetty.metricsstream.HystrixMetricsStreamHandler;
import io.netty.buffer.ByteBuf;
import netflix.karyon.transport.http.SimpleUriRouter;
import netflix.karyon.transport.http.health.HealthCheckEndpoint;

public class AppRouteProvider implements Provider<SimpleUriRouter<ByteBuf, ByteBuf>> {

    @Inject
    private HealthCheck healthCheck;

    @Inject
    private HelloWorldController helloWorldController;

    @Inject
    private HelloWorldObservableController helloWorldObservableController;


    @Inject
    private RemoteCallController remoteCallController;

    @Inject
    private RemoteCallSemaphoreController remoteCallSemaphoreController;

    @Inject
    private RemoteCachedController remoteCachedController;

    @Inject
    private FallbackController fallbackController;

    @Inject
    private NoHystrixController noHystrixController;

    @Override
    public SimpleUriRouter get() {
        SimpleUriRouter simpleUriRouter = new SimpleUriRouter();
        simpleUriRouter.addUri("/healthcheck", new HealthCheckEndpoint(healthCheck));
        simpleUriRouter.addUri("/message", remoteCallController);
        simpleUriRouter.addUri("/messageSemaphore", remoteCallSemaphoreController);
        simpleUriRouter.addUri("/messageCached", remoteCachedController);
        simpleUriRouter.addUri("/hystrix.stream", new HystrixMetricsStreamHandler<>(simpleUriRouter));
        simpleUriRouter.addUri("/hello", helloWorldController);
        simpleUriRouter.addUri("/helloObservable", helloWorldObservableController);
        simpleUriRouter.addUri("/noHystrix", noHystrixController);
        simpleUriRouter.addUri("/fallback", fallbackController);
        return simpleUriRouter;
    }
}