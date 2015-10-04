package service1.common;

import io.netty.buffer.ByteBuf;
import io.reactivex.netty.protocol.http.server.HttpServerRequest;
import io.reactivex.netty.protocol.http.server.HttpServerResponse;
import netflix.karyon.transport.interceptor.DuplexInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.Observable;


public class LoggingInterceptor implements DuplexInterceptor<HttpServerRequest<ByteBuf>, HttpServerResponse<ByteBuf>> {

    private static final Logger logger = LoggerFactory.getLogger(LoggingInterceptor.class);

    @Override
    public Observable<Void> in(HttpServerRequest<ByteBuf> request, HttpServerResponse<ByteBuf> response) {
        logger.info("Logging interceptor invoked for direction IN.");
        return Observable.empty();
    }

    @Override
    public Observable<Void> out(HttpServerResponse<ByteBuf> response) {
        logger.info("Logging interceptor invoked for direction OUT.");
        return Observable.empty();
    }
}
