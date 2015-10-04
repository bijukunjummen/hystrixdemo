package aggregate.commands.simple;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.*;

public class HelloWorldCommandTest {
    private static final Logger logger = LoggerFactory.getLogger(HelloWorldCommandTest.class);

    @Test
    public void testGreet() throws Exception {
        logger.info("About to execute HelloWorld command");
        HelloWorldCommand helloWorldCommand = new HelloWorldCommand("World");
        logger.info("Completed executing HelloWorld Command");
        assertEquals("Hello World", helloWorldCommand.execute());
    }
}
