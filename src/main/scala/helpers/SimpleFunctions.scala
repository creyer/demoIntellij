package helpers

import scala.collection.immutable.TreeMap

object SimpleFunctions extends LazyLogging{
  /**
    * parse one line by counting how many times each word is found
    * after all punctuations are removed, and text is lowered
    */
  def parse(line: Line) =
    line.str
      .replaceAll("""\p{Punct}""","")
      .toLowerCase
      .split("\\s+")
      .foldLeft(Map.empty[String, Long]){ (acc, elem) =>
        acc.get(elem) match {
          case Some(v) => acc.updated(elem, v+1)
          case _ => acc.updated(elem, 1)
        }
      }

  /**
    * merge 2 maps by summing up the number of occurrences
    * and orders them by key
    */
  def combine(a: Map[String, Long], b: Map[String, Long]): Map[String, Long] = {
    val notInB = a.keySet.diff(b.keySet)
    val res = a.filter(e => notInB(e._1)) ++ b.map { case (k,v) =>
      (k, a.get(k).getOrElse(0l) + v)
    }
    TreeMap(res.toSeq: _*)
  }

  /** print result */
  def printResult(result: Map[String, Long]) = {
    val notUsed = 1
    for ((k, v) <- result)
      logger.info(s"$k => $v")
    logger.info(s"Total words: ${result.size}")
  }
}
