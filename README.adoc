Hystrix Samples
---------------

Start the applications in the following order:

* Run Eureka - not technically required, but avoids other applications from
throwing errors on the console on not finding Eureka, the endpoint is at http://localhost:8761

    cd sample-eureka
    mvn spring-boot:run

* Start Hystrix Dashboard Application. This will be used for displaying the Hystrix command metrics, the endpoint is available at http://localhost:8080

    cd sample-hystrix-dashboard
    mvn spring-boot:run


* Start sample service, the endpoints is available at http://localhost:8889

    cd service1
    ./gradlew runApp

* Start client application, the endpoint is available at http://localhost:8888

    cd aggregate
    ./gradlew runApp

Endpoints
---------

The following endpoints should be available demonstrating different Hystrix features, look at the Hystrix Dashboard at http://localhost:8080/hystrix.dashboard and use the Hystrix stream available at http://localhost:8888/hystrix.stream

. http://localhost:8888/hello?greeting=World -
Simple Hello World Hystrix Command

. http://localhost:8888/helloObservable?greeting=World - Hello World Command using `HystrixObservableCommand`.

. http://localhost:8888/fallback - Sample Fallback Example

. http://localhost:8888/message?message=Hello&delay_by=0&throw_exception=false - Endpoint which makes a remote request behind a Hystrix command, play around with `delay_by` and `throw_exception` parameters.

.  http://localhost:8888/messageSemaphore?message=Hello&delay_by=0&throw_exception=false - Endpoint which makes a remote request behind a Hystrix command but using Semaphore based isolation strategy, play around with `delay_by` and `throw_exception` parameters.

.  http://localhost:8888/messageCached?message=Hello&delay_by=500&throw_exception=false - Hystrix Command with Caching

. http://localhost:8888/sampleCollapser?id=1 - Hystrix Request Collapsing
