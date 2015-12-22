package helpers

case class Line(str: String)

object LineHelper {

   implicit def toLine(s: String): Line = Line(s)

}
