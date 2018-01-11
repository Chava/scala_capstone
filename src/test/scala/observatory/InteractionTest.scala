package observatory

import org.scalatest.FunSuite
import org.scalatest.prop.Checkers

class InteractionTest extends FunSuite with Checkers {
  test("Tile locations should be calculated correctly #1") {
    val loc = Interaction.tileLocation(Tile(2,2,2))
    assert(loc.equals(Location(0,0)))
  }

  test("Tile locations should be calculated correctly #2") {
    val loc = Interaction.tileLocation(Tile(2,2,3))
    assert(loc.equals(Location(66.513, -90.0)))
  }

  test("Tile locations should be calculated correctly #3") {
    val locs = Interaction.tileAllLocations(Tile(0,0,1))
    assert(locs.head.equals(Location(85.051, -180.0)))
    assert(locs.last.equals(Location(0.703, -0.703)))
  }
}
