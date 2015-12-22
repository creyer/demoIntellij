package helpers

import java.util.concurrent.TimeUnit
import org.joda.time.{DateTimeZone, Duration, DateTime}

object DateTimeHelpers {

  implicit class DateTimeExtensions(val dateTime: DateTime) extends AnyVal {
    def <(other: DateTime): Boolean =
      dateTime.compareTo(other) < 0

    def >(other: DateTime): Boolean =
      dateTime.compareTo(other) > 0

    def <=(other: DateTime): Boolean =
      dateTime.compareTo(other) <= 0

    def >=(other: DateTime): Boolean =
      dateTime.compareTo(other) >= 0

    def +(duration: Duration): DateTime =
      dateTime.plus(duration)

    def +(duration: concurrent.duration.FiniteDuration): DateTime =
      dateTime.plusMillis(duration.toMillis.toInt)

    def -(other: DateTime): concurrent.duration.FiniteDuration =
      concurrent.duration.FiniteDuration(dateTime.asUTC.getMillis - other.asUTC.getMillis, TimeUnit.MILLISECONDS)

    def -(duration: concurrent.duration.FiniteDuration): DateTime =
      dateTime.minusMillis(duration.toMillis.toInt)

    def asUTC: DateTime =
      dateTime.toDateTime(DateTimeZone.UTC)
  }

}