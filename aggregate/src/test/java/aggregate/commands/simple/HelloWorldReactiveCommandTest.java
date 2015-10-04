package aggregate.commands.simple;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.Observable;

import java.util.concurrent.CountDownLatch;

import static org.junit.Assert.assertEquals;

public class HelloWorldReactiveCommandTest {
    private static final Logger logger = LoggerFactory.getLogger(HelloWorldReactiveCommandTest.class);

    @Test
    public void testGreet() throws Exception {
        logger.info("About to execute HelloWorld command");
        HelloWorldCommand helloWorldCommand = new HelloWorldCommand("World");
        logger.info("Completed executing HelloWorld Command");
        Observable<String> obs = helloWorldCommand.toObservable();

        CountDownLatch l = new CountDownLatch(1);
        obs.subscribe(System.out::println, t -> l.countDown(), () -> l.countDown());
        l.await();
    }
}
