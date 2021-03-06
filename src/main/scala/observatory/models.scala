package observatory

import scala.math.{abs, round}

/**
  * Introduced in Week 1. Represents a location on the globe.
  *
  * @param lat Degrees of latitude, -90 ≤ lat ≤ 90
  * @param lon Degrees of longitude, -180 ≤ lon ≤ 180
  */
case class Location(lat: Double, lon: Double) {

  override def equals(obj: scala.Any): Boolean = {
    if (!obj.isInstanceOf[Location]) false
    else {
      val loc = obj.asInstanceOf[Location]
      almostEquals(loc.lat, lat, 0.001) && almostEquals(loc.lon, lon, 0.001)
    }
  }

  def almostEquals(d1: Double, d2: Double, e: Double): Boolean =
    abs(d2 - d1) < e
}

/**
  * Introduced in Week 3. Represents a tiled web map tile.
  * See https://en.wikipedia.org/wiki/Tiled_web_map
  * Based on http://wiki.openstreetmap.org/wiki/Slippy_map_tilenames
  *
  * @param x    X coordinate of the tile
  * @param y    Y coordinate of the tile
  * @param zoom Zoom level, 0 ≤ zoom ≤ 19
  */
case class Tile(x: Int, y: Int, zoom: Int)

/**
  * Introduced in Week 4. Represents a point on a grid composed of
  * circles of latitudes and lines of longitude.
  *
  * @param lat Circle of latitude in degrees, -89 ≤ lat ≤ 90
  * @param lon Line of longitude in degrees, -180 ≤ lon ≤ 179
  */
case class GridLocation(lat: Int, lon: Int) {
  def this(location: Location) = this(location.lat.toInt, location.lon.toInt)
}

object GridLocation {
  def apply(location: Location) = new GridLocation(location)
}

/**
  * Introduced in Week 5. Represents a point inside of a grid cell.
  *
  * @param x X coordinate inside the cell, 0 ≤ x ≤ 1
  * @param y Y coordinate inside the cell, 0 ≤ y ≤ 1
  */
case class CellPoint(x: Double, y: Double)

/**
  * Introduced in Week 2. Represents an RGB color.
  *
  * @param red   Level of red, 0 ≤ red ≤ 255
  * @param green Level of green, 0 ≤ green ≤ 255
  * @param blue  Level of blue, 0 ≤ blue ≤ 255
  */
case class Color(red: Int, green: Int, blue: Int) {
  def this(red: Double, green: Double, blue: Double) =
    this(round(red).toInt, round(green).toInt, round(blue).toInt)
}

object Color {
  def apply(red: Double, green: Double, blue: Double) =
    new Color(red, green, blue)
}

case class Station(stn: STN, wban: WBAN)

object Convert {
  def fToC(d: Double) = round((d - 32) * 5 / 9)

  private def round(d: Double) = BigDecimal(d).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble
}