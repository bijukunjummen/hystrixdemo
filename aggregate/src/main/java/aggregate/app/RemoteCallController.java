package aggregate.app;

import aggregate.commands.remote.RemoteMessageClientCommand;
import aggregate.domain.Message;
import aggregate.service.RemoteCallService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import com.netflix.governator.annotations.AutoBindSingleton;
import feign.Feign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.reactivex.netty.protocol.http.server.HttpServerRequest;
import io.reactivex.netty.protocol.http.server.HttpServerResponse;
import io.reactivex.netty.protocol.http.server.RequestHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.Observable;


@AutoBindSingleton
public class RemoteCallController implements RequestHandler<ByteBuf, ByteBuf> {

    private final ObjectMapper objectMapper = new ObjectMapper();

//    private final RemoteCallService remoteCallService;

    private static final Logger logger = LoggerFactory.getLogger(RemoteCallController.class);

    private final RemoteCallService remoteCallService;

    @Inject
    public RemoteCallController(RemoteCallService remoteCallService) {
        this.remoteCallService = remoteCallService;
    }

    @Override
    public Observable<Void> handle(HttpServerRequest<ByteBuf> request, HttpServerResponse<ByteBuf> response) {
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
                    return new RemoteMessageClientCommand(this.remoteCallService, message).observe();
                })
                .flatMap(ack -> {
                            try {
                                return response.writeStringAndFlush(objectMapper.writeValueAsString(ack));
                            } catch (Exception e) {
                                response.setStatus(HttpResponseStatus.BAD_REQUEST);
                                return response.close();
                            }
                        }
                );
    }
}
