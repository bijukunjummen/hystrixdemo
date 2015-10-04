package service1.app;

import service1.governator.SampleGovernatorApp;
import com.netflix.governator.guice.BootstrapModule;
import netflix.karyon.Karyon;

public class SampleGovernatorAppRunner {

    public static void main(String[] args) {
        Karyon.forApplication(SampleGovernatorApp.class, (BootstrapModule[]) null)
                .startAndWaitTillShutdown();
    }
}
