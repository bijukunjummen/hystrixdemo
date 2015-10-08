package aggregate.service;

import aggregate.domain.Message;
import aggregate.domain.MessageAcknowledgement;
import feign.RequestLine;

public interface RemoteCallService {
    @RequestLine("POST /message")
    MessageAcknowledgement handleMessage(Message message);
}
