package aggregate.service;

import aggregate.domain.Message;
import aggregate.domain.MessageAcknowledgement;
import rx.Observable;

public interface RemoteCallService {
    Observable<MessageAcknowledgement> handleMessage(Message message);
}
