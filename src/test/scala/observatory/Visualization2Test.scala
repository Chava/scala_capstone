package observatory

import org.scalatest.FunSuite
import org.scalatest.prop.Checkers

class Visualization2Test extends FunSuite with Checkers {

  test("visualizeGrid should be calculated correctly") {
    val colors: Iterable[(Temperature, Color)] = Array((-20.0, Color(0, 0, 255)), (10.0, Color(50, 50, 100)))
    val img = Visualization2.visualizeGrid( (_)=> 10, colors, Tile(1,1,2))
    assert(img.pixel(0,0).toColor.toInt === 2133996132)
  }
}
