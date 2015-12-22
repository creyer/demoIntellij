package helpers

import org.scalatest.{Matchers, FunSuite}

class SimpleFunctions$Test extends FunSuite with Matchers {

  test("testParse") {
    SimpleFunctions.parse(Line("a b c, a")) should be (Map("a" -> 2, "b" -> 1, "c" -> 1))
  }

}
