package apps

import helpers.LazyLogging
import helpers.SimpleFunctions._
import org.joda.time.DateTime
import scala.concurrent.duration._
import helpers.DateTimeHelpers._
import scala.concurrent.{Await, Future}
import helpers.LineHelper._

object App2 extends App with LazyLogging {
  logger.info("Starting App2 - fast?! parallel counting")
  val startTime = DateTime.now()
  val source = io.Source.fromInputStream(getClass.getResourceAsStream("/large.txt"))

  implicit val ec = monifu.concurrent.Implicits.globalScheduler

  val futures = source.getLines()
    .map(l => Future { parse(l) })

  val ended = Future.sequence(futures).map{r =>
    logger.info("start combining results")
    r.reduce(combine(_, _))
  }.map(result =>  printResult(result))

  logger.info("App2 - result will be printed later")

  Await.result(ended, 2.minutes)

  val endTime = DateTime.now()
  logger.info(s"result App2 - printed in ${(endTime - startTime).toSeconds} seconds")
}
