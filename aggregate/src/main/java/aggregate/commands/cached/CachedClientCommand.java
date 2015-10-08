package aggregate.commands.cached;

import aggregate.domain.Message;
import aggregate.domain.MessageAcknowledgement;
import aggregate.service.RemoteCallService;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CachedClientCommand extends HystrixCommand<MessageAcknowledgement> {
    private static final String COMMAND_GROUP = "demo";
    private static final Logger logger = LoggerFactory.getLogger(CachedClientCommand.class);

    private final RemoteCallService remoteCallService;
    private final Message message;

    public CachedClientCommand(RemoteCallService remoteCallService, Message message) {
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

    @Override
    protected String getCacheKey() {
        return this.message.hashCode() + "";
    }
}
