package apps

import helpers.LazyLogging
import helpers.SimpleFunctions._
import org.joda.time.{DateTimeZone, DateTime}
import helpers.DateTimeHelpers._
import helpers.LineHelper._
/** Lazy counting words
  * It's considered a word anything that results by splitting spaces
  */
object App1 extends LazyLogging with App{
  logger.info("Starting App1 - lazy counting number of occurrences of each work inside on text file")
  var startTime = DateTime.now()
  val source = io.Source.fromInputStream(getClass.getResourceAsStream("/large.txt"))

  val result = extract

  def extract: Map[String, Long] = {
    source.getLines()
      .map(l => parse(l))
      .reduce(combine)
  }

  printResult(result)
  val endTime = DateTime.now()
  logger.info(s"result App1 - printed in ${(endTime - startTime).toSeconds} seconds")

  def exists(a: String, map: Map[String, Long]): Boolean =
    map.filter(_._1 == a).nonEmpty

}

/**
  * - suggestions
  * - renaming
  * - implicits are sometimes difficult parse(_)
  * - generate unit-test for parse method
  * - generate toString
  * - git support
  * - worksheet
  */
