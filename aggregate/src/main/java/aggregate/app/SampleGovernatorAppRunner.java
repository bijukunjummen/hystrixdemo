package aggregate.app;

import aggregate.governator.SamplePongAppGovernator;
import com.netflix.governator.guice.BootstrapModule;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import netflix.karyon.Karyon;

public class SampleGovernatorAppRunner {

    public static void main(String[] args) {
        Karyon.forApplication(SamplePongAppGovernator.class, (BootstrapModule[]) null)
                .startAndWaitTillShutdown();
    }
}
