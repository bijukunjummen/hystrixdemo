package aggregate.app;

import aggregate.commands.fallback.FallbackCommand;
import com.netflix.governator.annotations.AutoBindSingleton;
import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.reactivex.netty.protocol.http.server.HttpServerRequest;
import io.reactivex.netty.protocol.http.server.HttpServerResponse;
import io.reactivex.netty.protocol.http.server.RequestHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.Observable;

@AutoBindSingleton
public class FallbackController implements RequestHandler<ByteBuf, ByteBuf> {

    private static final Logger logger = LoggerFactory.getLogger(FallbackController.class);

    @Override
    public Observable<Void> handle(HttpServerRequest<ByteBuf> request, HttpServerResponse<ByteBuf> response) {
        return request.getContent()
                .flatMap(msg -> {
                    logger.info("About to execute Fallback command");
                    return new FallbackCommand().observe();
                })
                .flatMap(str -> {
                            try {
                                return response.writeStringAndFlush(str);
                            } catch (Exception e) {
                                response.setStatus(HttpResponseStatus.BAD_REQUEST);
                                return response.close();
                            }
                        }
                );
    }
}
