package observatory

import observatory.LayerName.Temperatures
import org.scalatest.FunSuite
import org.scalatest.prop.Checkers

class Interaction2Test extends FunSuite with Checkers {

  val colors: Seq[(Temperature, Color)] = Array((-20.0, Color(0, 0, 255)), (10.0, Color(50, 50, 100)))
  val layer = Layer(Temperatures, colors, (1970 to 1990))

  test("yearBounds should be calculated correctly") {
    val customLayer = Layer(Temperatures, colors, (0 until 10))
    assert(Interaction2.yearBounds(Signal(customLayer))() === Signal((0 until 10))())
  }

  test("yearSelection should be calculated correctly #1") {
    assert(Interaction2.yearSelection(Signal(layer), Signal(1980))() == Signal(1980)())
  }

  test("yearSelection should be calculated correctly #2") {
     assert(Interaction2.yearSelection(Signal(layer), Signal(1600))() == Signal(1970)())
  }

  test("yearSelection should be calculated correctly #3") {
    assert(Interaction2.yearSelection(Signal(layer), Signal(2000))() == Signal(1990)())
  }

  test("caption method should return correct string") {
    assert(Interaction2.caption(Signal(layer), Signal(1980))() === "Temperatures (1980)")
  }
}
