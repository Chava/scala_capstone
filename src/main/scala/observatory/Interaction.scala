package observatory

import com.sksamuel.scrimage.{Image, Pixel}
import scala.math.{Pi, atan, pow, sinh}


/**
  * 3rd milestone: interactive visualization
  */
object Interaction {

  val IMG_WIDTH = 256
  val IMG_HEIGHT = 256

  /**
    * @param tile Tile coordinates
    * @return The latitude and longitude of the top-left corner of the tile, as per http://wiki.openstreetmap.org/wiki/Slippy_map_tilenames
    */
  def tileLocation(tile: Tile): Location = {
    val n = pow(2, tile.zoom)
    val lng = tile.x / n * 360.0 - 180.0
    val lat = atan(sinh(Pi * (1 - 2 * tile.y / n))) * 180.0 / Pi
    Location(lat, lng)
  }

  def tileAllLocations(tile: Tile): Array[Location] = {
    val locationsMap = new Array[Location](IMG_WIDTH * IMG_HEIGHT)
    for (y <- 0 until IMG_HEIGHT; x <- 0 until IMG_WIDTH) {
      locationsMap(y * IMG_HEIGHT + x) = tileLocation(Tile(256 * tile.x + x, 256 * tile.y + y, tile.zoom + 8))
    }
    locationsMap
  }

  /**
    * @param temperatures Known temperatures
    * @param colors       Color scale
    * @param tileObj      Tile coordinates
    * @return A 256×256 image showing the contents of the given tile
    */
  def tile(temperatures: Iterable[(Location, Temperature)], colors: Iterable[PalettePoint], tileObj: Tile): Image = {
    val tmps = tileAllLocations(tileObj).map {l => Visualization.predictTemperature(temperatures, l)}
    val pixels = tmps.map {t => Visualization.interpolateColor(colors, t)}
    val img = pixels.map { p => Pixel.apply(p.red, p.green, p.blue, 127)}
    Image(IMG_WIDTH, IMG_HEIGHT, img)
  }

  /**
    * Generates all the tiles for zoom levels 0 to 3 (included), for all the given years.
    *
    * @param yearlyData    Sequence of (year, data), where `data` is some data associated with
    *                      `year`. The type of `data` can be anything.
    * @param generateImage Function that generates an image given a year, a zoom level, the x and
    *                      y coordinates of the tile and the data to build the image from
    */
  def generateTiles[Data](
                           yearlyData: Iterable[(Year, Data)],
                           generateImage: (Year, Tile, Data) => Unit
                         ): Unit = {
    ???
  }

}
