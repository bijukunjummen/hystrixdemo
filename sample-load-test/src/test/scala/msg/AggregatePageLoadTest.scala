package msg

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class AggregatePageLoadTest extends Simulation {

  val httpConf = http.baseURL("http://localhost:8888")

  val headers = Map("Accept" -> """application/json""")

  val scn = scenario("Get the Aggregated Page")
    .exec(http("request_1")
      .get("/message")
        .queryParam("message", "hello")
        .queryParam("delay_by","1000")
        .queryParam("throw_exception", "false"))

  setUp(scn.inject(rampUsers(21) over (5 seconds)).protocols(httpConf))
}
