package aggregate.commands.fallback;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertEquals;

public class FallbackCommandTest {
    private static final Logger logger = LoggerFactory.getLogger(FallbackCommandTest.class);

    @Test
    public void testFallback() throws Exception {
        logger.info("About to execute Fallback command");
        FallbackCommand fallbackCommand = new FallbackCommand();
        logger.info("Completed executing Fallback Command");
        assertEquals("Falling back", fallbackCommand.execute());
    }
}
