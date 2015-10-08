package aggregate.app;

import aggregate.commands.cached.CachedClientCommand;
import aggregate.domain.Message;
import aggregate.domain.MessageAcknowledgement;
import aggregate.service.RemoteCallService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import com.netflix.governator.annotations.AutoBindSingleton;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.reactivex.netty.protocol.http.server.HttpServerRequest;
import io.reactivex.netty.protocol.http.server.HttpServerResponse;
import io.reactivex.netty.protocol.http.server.RequestHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.Observable;


@AutoBindSingleton
public class RemoteCachedController implements RequestHandler<ByteBuf, ByteBuf> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    private static final Logger logger = LoggerFactory.getLogger(RemoteCachedController.class);

    private final RemoteCallService remoteCallService;

    @Inject
    public RemoteCachedController(RemoteCallService remoteCallService) {
        this.remoteCallService = remoteCallService;
    }

    @Override
    public Observable<Void> handle(HttpServerRequest<ByteBuf> request, HttpServerResponse<ByteBuf> response) {
        final HystrixRequestContext context = HystrixRequestContext.initializeContext();

        return request.getContent()
                .map(s -> {
                    boolean throwException = Boolean.valueOf(request.getQueryParameters().get("throw_exception").get(0));
                    int delayBy = Integer.valueOf(request.getQueryParameters().get("delay_by").get(0));
                    String msg = request.getQueryParameters().get("message").get(0);
                    Message message = new Message("id", msg, throwException, delayBy);
                    return message;
                })
                .flatMap(message -> {
                    logger.info("About to make remote call");
                    Observable<MessageAcknowledgement> resp1 = new CachedClientCommand(this.remoteCallService, message).observe();
                    Observable<MessageAcknowledgement> resp2 = new CachedClientCommand(this.remoteCallService, message).observe();
                    return resp1.mergeWith(resp2);

                })
                .flatMap(ack -> {
                            try {
                                return response.writeStringAndFlush(objectMapper.writeValueAsString(ack));
                            } catch (Exception e) {
                                response.setStatus(HttpResponseStatus.BAD_REQUEST);
                                return response.close();
                            } finally {
                                context.shutdown();
                            }
                        }
                );
    }
}
