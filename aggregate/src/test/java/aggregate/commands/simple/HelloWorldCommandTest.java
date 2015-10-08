package aggregate.commands.simple;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.Observable;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

public class HelloWorldCommandTest {
    private static final Logger logger = LoggerFactory.getLogger(HelloWorldCommandTest.class);

    @Test
    public void testGreetSync() throws Exception {
        logger.info("About to execute HelloWorld command");
        HelloWorldCommand helloWorldCommand = new HelloWorldCommand("World");
        assertEquals("Hello World", helloWorldCommand.execute());
        logger.info("Completed executing HelloWorld Command");
    }

    @Test
    public void testGreetFuture() throws Exception {
        logger.info("About to execute HelloWorld command");
        HelloWorldCommand helloWorldCommand = new HelloWorldCommand("World");
        Future<String> future = helloWorldCommand.queue();
        assertEquals("Hello World", future.get());
        logger.info("Completed executing HelloWorld Command");
    }

    @Test
    public void testGreetReactive() throws Exception {
        logger.info("About to execute HelloWorld command");
        HelloWorldCommand helloWorldCommand = new HelloWorldCommand("World");

        CountDownLatch l = new CountDownLatch(1);

        Observable<String> obs = helloWorldCommand.observe();
        obs.subscribe(
                s -> logger.info("Received : " + s),
                t -> logger.error(t.getMessage(), t),
                () -> l.countDown()
        );
        l.await(5, TimeUnit.SECONDS);
        logger.info("Completed executing HelloWorld Command");
    }
}
