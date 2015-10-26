package aggregate.commands.obs;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.Observable;

import java.util.concurrent.CountDownLatch;

public class HelloWorldObsCommandTest {
    private static final Logger logger = LoggerFactory.getLogger(HelloWorldObsCommandTest.class);

    @Test
    public void testHotObservable() throws Exception {
        logger.info("About to execute HelloWorld command");
        HelloWorldObservableCommand helloWorldCommand = new HelloWorldObservableCommand("World");
        logger.info("Completed executing HelloWorld Command");
        Observable<String> obs = helloWorldCommand.observe();

        CountDownLatch l = new CountDownLatch(1);
        obs.subscribe(System.out::println, t -> l.countDown(), () -> l.countDown());
        l.await();
    }

    @Test
    public void testColdObservable() throws Exception {
        logger.info("About to execute HelloWorld command");
        HelloWorldObservableCommand helloWorldCommand = new HelloWorldObservableCommand("World");
        logger.info("Completed executing HelloWorld Command");
        Observable<String> obs = helloWorldCommand.toObservable();

        CountDownLatch l = new CountDownLatch(1);
        obs.subscribe(System.out::println, t -> l.countDown(), () -> l.countDown());
        l.await();
    }
}
