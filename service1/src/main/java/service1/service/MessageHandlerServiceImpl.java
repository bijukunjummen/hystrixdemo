package service1.service;

import com.netflix.governator.annotations.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.Observable;
import service1.domain.Message;
import service1.domain.MessageAcknowledgement;

import java.util.concurrent.TimeUnit;

public class MessageHandlerServiceImpl implements MessageHandlerService {
    private static final Logger logger = LoggerFactory.getLogger(MessageHandlerServiceImpl.class);

    @Configuration("reply.message")
    private String replyMessage;

    public Observable<MessageAcknowledgement> handleMessage(Message message) {
        logger.info("About to Acknowledge");
        return Observable.timer(message.getDelayBy(), TimeUnit.MILLISECONDS)
                .map(l -> message.isThrowException())
                .map(throwException -> {
                    if (throwException) {
                        throw new RuntimeException("Throwing an exception!");
                    }
                    return new MessageAcknowledgement(message.getId(), message.getPayload(), replyMessage);
                });
    }


}
