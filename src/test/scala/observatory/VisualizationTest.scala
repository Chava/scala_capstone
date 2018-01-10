package observatory


import com.sksamuel.scrimage.Image
import org.scalactic.TolerantNumerics
import org.scalatest.FunSuite
import org.scalatest.prop.Checkers

class VisualizationTest extends FunSuite with Checkers {
  val e = 1e-3f
  implicit val eq = TolerantNumerics.tolerantDoubleEquality(e)

  test("Color values should be calculated correctly #1") {
    val colors: Iterable[(Temperature, Color)] = Array((-20.0, Color(0, 0, 255)), (10.0, Color(50, 50, 100)))
    val tmp = Visualization.interpolateColor(colors, -5.0)
    assert(tmp.equals(Color(25, 25, 178)))
  }

  test("Color values should be calculated correctly #2") {
    val colors: Iterable[(Temperature, Color)] = Array((-1.0, Color(255, 0, 0)), (0.0, Color(0, 0, 255)))
    val tmp = Visualization.interpolateColor(colors, -0.75)
    assert(tmp.equals(Color(191, 0, 64)))
  }

  test("Color values should be calculated correctly #3") {
    val colors: Iterable[(Temperature, Color)] = Array((-1.0, Color(255, 0, 0)), (0.0, Color(0, 0, 255)))
    val tmp = Visualization.interpolateColor(colors, 1)
    assert(tmp.equals(Color(0, 0, 255)))
  }

  test("Color values should be calculated correctly #4") {
    val colors: Iterable[(Temperature, Color)] = Array((-1.0, Color(255, 0, 0)), (0.0, Color(0, 0, 255)))
    val tmp = Visualization.interpolateColor(colors, -10)
    assert(tmp.equals(Color(255, 0, 0)))
  }

  test("Color values should be calculated correctly #5") {
    val colors: Iterable[(Temperature, Color)] = Array((-1.0, Color(255, 0, 0)), (0.0, Color(0, 0, 255)))
    val tmp = Visualization.interpolateColor(colors, -1.0)
    assert(tmp.equals(Color(255, 0, 0)))
  }

  test("Distance should be calculated correctly #1") {
    val distance = Visualization.distance(Location(10, 10), Location(20, 20))
    assert(distance == 1544)
  }

  test("Distance should be calculated correctly #2") {
    val distance = Visualization.distance(Location(37, -122), Location(60, 30))
    assert(distance == 8928)
  }

  test("Temperature prediction #1") {
    val data = Array((Location(30, 30), -10.0), (Location(45, 45), 5.0))
    val tmp = Visualization.predictTemperature(data, Location(40, 40))
    assert(tmp === 4.238)
  }

  test("Temperature prediction #2") {
    val data = Array((Location(30, 30), -10.0), (Location(45, 45), 5.0))
    val tmp = Visualization.predictTemperature(data, Location(50, 50))
    assert(tmp === 4.949)
  }

  test("Temperature prediction #3") {
    val data = Array((Location(30, 30), -10.0), (Location(45, 45), 5.0), (Location(0, 0), 30.0))
    val tmp = Visualization.predictTemperature(data, Location(25, -40))
    assert(tmp === 18.378)
  }

  test("Temperature prediction #4") {
    val data = Array((Location(30, 30), -10.0), (Location(45, 45), 5.0), (Location(-20, -125), 25.0))
    val tmp = Visualization.predictTemperature(data, Location(0, 0))
    assert(tmp === -6.888)
  }

  test("Temperature prediction #5") {
    val data = Array((Location(-90, 0), -50.0), (Location(90, 180), -50.0))
    val tmp = Visualization.predictTemperature(data, Location(42, -14))
    assert(tmp === -50.0)
  }

  test("Temperature prediction #6") {
    val data = Array((Location(30, 30), -10.0), (Location(45, 45), 5.0), (Location(-20, -125), 25.0))
    val tmp = Visualization.predictTemperature(data, Location(30, 30))
    assert(tmp === -10.0)
  }

  test("Visualization test #1") {
    val tmps = Array((Location(20, 20), 15.0), (Location(70, -100), 0.0))
    val colors = Array((-40.0, Color(0, 0, 255)), (-10.0, Color(0, 255, 0)), (30.0, Color(255, 0, 0)))
    val img: Image = Visualization.visualize(tmps, colors)
    assert(img.pixel(10, 160).toColor.toInt == -8617216)
    assert(img.pixel(50, 50).toColor.toInt == -12534016)
  }

  test("Visualization test #2") {
    val tmps = Array((Location(20, 20), 15.0), (Location(70, -100), 0.0), (Location(60, 30), -10.0))
    val colors = Array((-40.0, Color(0, 0, 255)), (-10.0, Color(0, 255, 0)), (30.0, Color(255, 0, 0)))
    val img: Image = Visualization.visualize(tmps, colors)
    assert(img.pixel(0, 0).toColor.toInt == -13186816)
  }
}
