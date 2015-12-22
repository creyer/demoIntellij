package apps

import helpers.LazyLogging
import monifu.reactive.Observable
import monifu.reactive.OverflowStrategy.Unbounded
import monifu.reactive.channels.PublishChannel
import LineIndexed._
import monifu.concurrent.Implicits.globalScheduler
import helpers.SimpleFunctions._
import org.joda.time.DateTime
import scala.concurrent.duration._
import helpers.DateTimeHelpers._
import scala.concurrent.Await

object App3 extends App with LazyLogging {

  logger.info("Starting App3 - fast counting number of occurrences of each work inside on text file")
  val startTime = DateTime.now()
  val source = io.Source.fromInputStream(getClass.getResourceAsStream("/large.txt"))
  val parallel = 3

  val parseChannels: Map[Int,PublishChannel[String]] =
    (0 to parallel-1).map (i => (i, PublishChannel[String](Unbounded))).toMap

  val parseLinesIndexed: Map[Int, Observable[Map[String, Long]]]  =
    parseChannels map {case (k,v) =>
      (k, v.map(e => e.parsed))}

  val future = Observable.merge(parseLinesIndexed.values.toList: _*).scan(Map.empty[String, Long]){(acc, elem) =>
    combine(acc, elem)
  }.last.doWork(printResult(_)).asFuture

  Observable.fromIterator(source.getLines()).scan(0){ (index, line) =>
    val i = index % parallel
    parseChannels.get(i).get.pushNext(line)
    index + 1
  }.doOnComplete((0 to parallel-1).map(i => parseChannels.get(i).get.pushComplete())).subscribe()

  logger.info("App3 - result will be printed later")

  Await.result(future, 2.minutes)

  val endTime = DateTime.now()
  logger.info(s"result App3 - printed in ${(endTime - startTime).toSeconds} seconds")
}

object LineIndexed  {
  implicit class ParseLine(val l: String) extends AnyVal {
    def parsed: Map[String, Long] =
      l.replaceAll( """\p{Punct}""", "")
        .toLowerCase
        .split("\\s+")
        .foldLeft(Map.empty[String, Long]) { (acc, elem) =>
          acc.get(elem) match {
            case Some(v) => acc.updated(elem, v + 1)
            case _ => acc.updated(elem, 1)
          }
        }
  }
}
