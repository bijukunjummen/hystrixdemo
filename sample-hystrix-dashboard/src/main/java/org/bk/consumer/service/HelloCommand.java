package org.bk.consumer.service;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

public class HelloCommand extends HystrixCommand<String> {
    private static final String COMMAND_GROUP = "default";
    private final String name;

    public HelloCommand(String name) {
        super(HystrixCommandGroupKey.Factory.asKey(COMMAND_GROUP));
        this.name = name;
    }


    @Override
    protected String run() throws Exception {
        return "Hello, " + name;
    }
}
