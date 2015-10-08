package service1.app;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.reactivex.netty.protocol.http.server.HttpServerRequest;
import io.reactivex.netty.protocol.http.server.HttpServerResponse;
import io.reactivex.netty.protocol.http.server.RequestHandler;
import rx.Observable;
import service1.domain.Message;
import service1.service.MessageHandlerService;

import java.io.IOException;
import java.nio.charset.Charset;


public class ApplicationMessageHandler implements RequestHandler<ByteBuf, ByteBuf> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final MessageHandlerService messageHandlerService;

    @Inject
    public ApplicationMessageHandler(MessageHandlerService messageHandlerService) {
        this.messageHandlerService = messageHandlerService;
    }

    @Override
    public Observable<Void> handle(HttpServerRequest<ByteBuf> request, HttpServerResponse<ByteBuf> response) {
        return request.getContent().map(byteBuf -> byteBuf.toString(Charset.forName("UTF-8")))
                .map(s -> {
                    try {
                        Message m = objectMapper.readValue(s, Message.class);
                        return m;
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                })
                .flatMap(messageHandlerService::handleMessage)
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
