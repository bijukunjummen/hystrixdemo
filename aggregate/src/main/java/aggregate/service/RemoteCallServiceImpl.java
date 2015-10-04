//package aggregate.service;
//
//import aggregate.domain.Message;
//import aggregate.domain.MessageAcknowledgement;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import io.netty.buffer.ByteBuf;
//import io.netty.handler.codec.http.HttpMethod;
//import io.reactivex.netty.RxNetty;
//import io.reactivex.netty.channel.StringTransformer;
//import io.reactivex.netty.protocol.http.client.HttpClient;
//import io.reactivex.netty.protocol.http.client.HttpClientRequest;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import rx.Observable;
//
//import java.nio.charset.Charset;
//
//public class RemoteCallServiceImpl implements RemoteCallService {
//    private static final Logger logger = LoggerFactory.getLogger(RemoteCallServiceImpl.class);
//    private final HttpClient<ByteBuf, ByteBuf> httpClient;
//    private final ObjectMapper objectMapper = new ObjectMapper();
//
//    public RemoteCallServiceImpl() {
//        this.httpClient = RxNetty.<ByteBuf, ByteBuf>newHttpClientBuilder("localhost", 8889).build();
//    }
//
//    public Observable<MessageAcknowledgement> handleMessage(Message message) {
//        logger.info("About to call Remote service");
//
//        HttpClientRequest<ByteBuf> request = HttpClientRequest.create(HttpMethod.POST, "/message");
//
//        try {
//            String messageAsString = this.objectMapper.writeValueAsString(message);
//
//            request.withRawContent(messageAsString, StringTransformer.DEFAULT_INSTANCE);
//
//            return this.httpClient.submit(request)
//                    .flatMap(response -> response.getContent())
//                    .map(data -> data.toString(Charset.defaultCharset()))
//                    .map(data -> {
//                        try {
//                            return this.objectMapper.readValue(data, MessageAcknowledgement.class);
//                        }catch (Exception e) {
//                            throw new RuntimeException(e);
//                        }
//                    });
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//            return Observable.error(e);
//        }
//
//    }
//
//}
