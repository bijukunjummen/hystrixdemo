package aggregate.commands.remote;

import aggregate.domain.Message;
import aggregate.domain.MessageAcknowledgement;
import aggregate.service.RemoteCallService;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixObservableCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.Observable;

public class RemoteMessageClientCommand extends HystrixObservableCommand<MessageAcknowledgement> {
    private static final String COMMAND_GROUP = "default";
    private static final Logger logger = LoggerFactory.getLogger(RemoteMessageClientCommand.class);

    private final RemoteCallService remoteCallService;
    private final Message message;

    public RemoteMessageClientCommand(RemoteCallService remoteCallService, Message message) {
        super(HystrixCommandGroupKey.Factory.asKey(COMMAND_GROUP));
        this.remoteCallService = remoteCallService;
        this.message = message;
    }

    @Override
    protected Observable<MessageAcknowledgement> construct() {
        logger.info("Returning an observable for remote call");
        return this.remoteCallService.handleMessage(this.message);
    }

    @Override
    protected Observable<MessageAcknowledgement> resumeWithFallback() {
        return Observable.just(new MessageAcknowledgement(message.getId(), message.getPayload(), "Fallback message"));
    }

}
