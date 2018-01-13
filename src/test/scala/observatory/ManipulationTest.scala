package observatory

import org.scalactic.TolerantNumerics
import org.scalatest.FunSuite
import org.scalatest.prop.Checkers

class ManipulationTest extends FunSuite with Checkers {
  val e = 1e-3f
  implicit val eq = TolerantNumerics.tolerantDoubleEquality(e)

  val metrics1 = Array((Location(-50,50), -20.0), (Location(30,100), 35.0), (Location(70,0), -10.0))
  val metrics2 = Array((Location(-50,50), -23.0), (Location(30,100), 30.0), (Location(70,0), -7.0))

  test("makeGrid method should return correct calculations #1") {
    def convert = Manipulation.makeGrid(metrics1)
    assert(convert(GridLocation(0,0)) === -10.613)
    assert(convert(GridLocation(10,0)) === -7.918)
    assert(convert(GridLocation(20,0)) === -7.336)
  }

  test("makeGrid method should return correct calculations #2") {
    def convert = Manipulation.makeGrid(metrics2)
    assert(convert(GridLocation(0,0)) === -11.459)
    assert(convert(GridLocation(10,0)) === -7.378)
    assert(convert(GridLocation(20,0)) === -5.648)
  }

  test("average method should return correct calculations") {
    def avgConvert = Manipulation.average(Iterable(metrics1, metrics2))
    assert(avgConvert(GridLocation(0,0)) === -11.036)
    assert(avgConvert(GridLocation(10,0)) === -7.648)
    assert(avgConvert(GridLocation(20,0)) === -6.492)
  }
}