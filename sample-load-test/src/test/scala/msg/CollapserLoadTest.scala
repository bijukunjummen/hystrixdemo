package msg

import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.concurrent.duration._

class CollapserLoadTest extends Simulation {

  val httpConf = http.baseURL("http://localhost:8888")

  val headers = Map("Accept" -> """application/json""")

  val feeder = for ( i <- 1 to 30) yield Map("id" -> i)
  println(feeder)

  val scn = scenario("Get the Aggregated Page")
    .feed(feeder)
    .exec(http("collapser")
        .get("/sampleCollapser")
        .queryParam("id", "${id}")
        .queryParam("delay_by","10"))


  setUp(scn.inject(atOnceUsers(20)).protocols(httpConf))
}
