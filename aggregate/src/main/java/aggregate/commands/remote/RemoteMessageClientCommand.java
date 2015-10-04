package aggregate.commands.remote;

import aggregate.domain.Message;
import aggregate.domain.MessageAcknowledgement;
import aggregate.service.RemoteCallService;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RemoteMessageClientCommand extends HystrixCommand<MessageAcknowledgement> {
    private static final String COMMAND_GROUP = "demo";
    private static final Logger logger = LoggerFactory.getLogger(RemoteMessageClientCommand.class);

    private final RemoteCallService remoteCallService;
    private final Message message;

    public RemoteMessageClientCommand(RemoteCallService remoteCallService, Message message) {
        super(HystrixCommandGroupKey.Factory.asKey(COMMAND_GROUP));
        this.remoteCallService = remoteCallService;
        this.message = message;
    }

    @Override
    protected MessageAcknowledgement run() throws Exception {
        logger.info("About to make Remote Call");
        return this.remoteCallService.handleMessage(this.message);
    }

    @Override
    protected MessageAcknowledgement getFallback() {
        return new MessageAcknowledgement(message.getId(), message.getPayload(), "Fallback message");
    }
}
