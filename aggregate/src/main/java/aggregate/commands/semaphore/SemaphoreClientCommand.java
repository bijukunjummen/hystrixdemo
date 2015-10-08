package aggregate.commands.semaphore;

import aggregate.domain.Message;
import aggregate.domain.MessageAcknowledgement;
import aggregate.service.RemoteCallService;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SemaphoreClientCommand extends HystrixCommand<MessageAcknowledgement> {
    private static final String COMMAND_GROUP = "demo";
    private static final Logger logger = LoggerFactory.getLogger(SemaphoreClientCommand.class);

    private final RemoteCallService remoteCallService;
    private final Message message;

    public SemaphoreClientCommand(RemoteCallService remoteCallService, Message message) {
        super(Setter
                .withGroupKey(HystrixCommandGroupKey.Factory.asKey(COMMAND_GROUP))
                .andCommandPropertiesDefaults(
                        HystrixCommandProperties.Setter()
                                .withExecutionTimeoutInMilliseconds(5000)
                                .withExecutionIsolationStrategy(HystrixCommandProperties.ExecutionIsolationStrategy.SEMAPHORE)));
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
