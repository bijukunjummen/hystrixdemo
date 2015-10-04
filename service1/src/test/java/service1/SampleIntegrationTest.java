package service1;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http.HttpMethod;
import io.reactivex.netty.RxNetty;
import io.reactivex.netty.channel.StringTransformer;
import io.reactivex.netty.protocol.http.client.HttpClient;
import io.reactivex.netty.protocol.http.client.HttpClientRequest;
import service1.domain.Message;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;
import java.util.concurrent.CountDownLatch;

public class SampleIntegrationTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    private static final Logger logger = LoggerFactory.getLogger(SampleIntegrationTest.class);

    @Test
    public void testSampleCallToEndpoint() throws Exception {
        HttpClient<String, ByteBuf> client = RxNetty.<String, ByteBuf>newHttpClientBuilder("localhost", 8889).build();
        HttpClientRequest<String> request = HttpClientRequest.create(HttpMethod.POST, "/message");

        String message = objectMapper.writeValueAsString(new Message("1", "Ping", false, 0));

        request.withRawContent(message, StringTransformer.DEFAULT_INSTANCE);
        CountDownLatch l = new CountDownLatch(1);

        client.submit(request)
                .flatMap(response -> response.getContent())
                .map(data -> data.toString(Charset.defaultCharset()))
                .subscribe(s -> {
                    logger.info(s);
                    l.countDown();
                });
        l.await();
    }
}
