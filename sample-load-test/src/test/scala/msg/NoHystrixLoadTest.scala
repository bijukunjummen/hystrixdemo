package msg

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class NoHystrixLoadTest extends Simulation {

  val httpConf = http.baseURL("http://localhost:8888")

  val headers = Map("Accept" -> """application/json""")

  val scn = scenario("Get the Aggregated Page")
    .exec(http("request_1")
    .get("/noHystrix")
    .queryParam("message", "hello")
    .queryParam("delay_by","5000")
    .queryParam("throw_exception", "false"))

  setUp(scn.inject(rampUsers(50) over (1 seconds)).protocols(httpConf))
}
