package aggregate.commands.collapsed;

import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.Observable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class CollapsedCommandTest {
    private static final Logger logger = LoggerFactory.getLogger(CollapsedCommandTest.class);

    @Test
    public void testCollapse() throws Exception {
        HystrixRequestContext requestContext = HystrixRequestContext.initializeContext();

        logger.info("About to execute Collapsed command");
        List<Observable<Person>> result = new ArrayList<>();
        CountDownLatch cl = new CountDownLatch(1);
        for (int i = 1; i <= 100; i++) {
            result.add(new PersonRequestCollapser(i).observe());
        }

        Observable.merge(result).subscribe(p -> logger.info(p.toString())
                , t -> logger.error(t.getMessage(), t)
                , () -> cl.countDown());
        cl.await();
        logger.info("Completed executing Collapsed Command");
        requestContext.shutdown();
    }
}
