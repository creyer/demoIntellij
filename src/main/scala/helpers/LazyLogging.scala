package helpers


import org.slf4j.{Logger, LoggerFactory}

trait LazyLogging {
  protected lazy val logger: Logger =
    LoggerFactory.getLogger(getClass.getName)
}
