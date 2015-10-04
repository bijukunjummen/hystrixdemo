package aggregate.commands.simple;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Future;

import static org.junit.Assert.assertEquals;

public class HelloWorldAsyncCommandTest {
    private static final Logger logger = LoggerFactory.getLogger(HelloWorldAsyncCommandTest.class);

    @Test
    public void testGreet() throws Exception {
        logger.info("About to execute HelloWorld command");
        HelloWorldCommand helloWorldCommand = new HelloWorldCommand("World");
        logger.info("Completed executing HelloWorld Command");
        Future<String> future = helloWorldCommand.queue();
        assertEquals("Hello World", future.get());
    }
}
