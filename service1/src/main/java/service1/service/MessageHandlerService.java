package service1.service;

import service1.domain.Message;
import service1.domain.MessageAcknowledgement;
import rx.Observable;

public interface MessageHandlerService {
    Observable<MessageAcknowledgement> handleMessage(Message message);
}
