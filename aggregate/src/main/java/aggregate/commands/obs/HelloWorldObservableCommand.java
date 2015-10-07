package aggregate.commands.obs;


import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixObservableCommand;
import rx.Observable;

public class HelloWorldObservableCommand extends HystrixObservableCommand<String> {

    private String name;

    public HelloWorldObservableCommand(String name) {
        super(HystrixCommandGroupKey.Factory.asKey("default"));
        this.name = name;
    }

    @Override
    protected Observable<String> resumeWithFallback() {
        return Observable.just("Returning a Fallback");
    }

    @Override
    protected Observable<String> construct() {
        return Observable.just(this.name);
    }
}
