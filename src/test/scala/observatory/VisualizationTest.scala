package observatory


import org.scalatest.FunSuite
import org.scalatest.prop.Checkers

class VisualizationTest extends FunSuite with Checkers {

//  test("Color values should be calculated correctly #1") {
//    val colors: Iterable[(Temperature, Color)] = Array((-20.0, Color(0, 0, 255)), (10.0, Color(50, 50, 100)))
//    val tmp = Visualization.interpolateColor(colors, -5.0)
//    assert(tmp.equals(Color(25, 25, 178)))
//  }
//
//  test("Color values should be calculated correctly #2") {
//    val colors: Iterable[(Temperature, Color)] = Array((-1.0, Color(255, 0, 0)), (0.0, Color(0, 0, 255)))
//    val tmp = Visualization.interpolateColor(colors, -0.75)
//    assert(tmp.equals(Color(191, 0, 64)))
//  }
//
//  test("Color values should be calculated correctly #3") {
//    val colors: Iterable[(Temperature, Color)] = Array((-1.0, Color(255, 0, 0)), (0.0, Color(0, 0, 255)))
//    val tmp = Visualization.interpolateColor(colors, 1)
//    assert(tmp.equals(Color(0, 0, 255)))
//  }
//
//  test("Color values should be calculated correctly #4") {
//    val colors: Iterable[(Temperature, Color)] = Array((-1.0, Color(255, 0, 0)), (0.0, Color(0, 0, 255)))
//    val tmp = Visualization.interpolateColor(colors, -10)
//    assert(tmp.equals(Color(255, 0, 0)))
//  }
//
//  test("Color values should be calculated correctly #5") {
//    val colors: Iterable[(Temperature, Color)] = Array((-1.0, Color(255, 0, 0)), (0.0, Color(0, 0, 255)))
//    val tmp = Visualization.interpolateColor(colors, -1.0)
//    assert(tmp.equals(Color(255, 0, 0)))
//  }

  test("Distance should be calculated correctly #1") {
    val distance = Visualization.distance(Location(10,10), Location(20,20))
    assert(distance == 1551)
  }

  test("Distance should be calculated correctly #2") {
    val distance = Visualization.distance(Location(37,-122), Location(60,30))
    assert(distance == 8968)
  }

}
