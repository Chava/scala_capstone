package observatory

import com.sksamuel.scrimage.{Image, Pixel}
import observatory.Interaction.{IMG_HEIGHT, IMG_WIDTH}

/**
  * 5th milestone: value-added information visualization
  */
object Visualization2 {

  /**
    * @param point (x, y) coordinates of a point in the grid cell
    * @param d00   Top-left value
    * @param d01   Bottom-left value
    * @param d10   Top-right value
    * @param d11   Bottom-right value
    * @return A guess of the value at (x, y) based on the four known values, using bilinear interpolation
    *         See https://en.wikipedia.org/wiki/Bilinear_interpolation#Unit_Square
    */
  def bilinearInterpolation(
                             point: CellPoint,
                             d00: Temperature,
                             d01: Temperature,
                             d10: Temperature,
                             d11: Temperature
                           ): Temperature = {
    // Bilinear interpolation
    // https://en.wikipedia.org/wiki/Bilinear_interpolation
    val (x, y) = (point.x, point.y)
    d00 * (1 - x) * (1 - y) + d10 * x * (1 - y) +
      d01 * (1 - x) * y + d11 * x * y
  }

  def bilinearInterpolation(
                             point: CellPoint,
                             tmps: Array[Temperature]
                           ): Temperature = {
    bilinearInterpolation(point, tmps(0), tmps(1), tmps(2), tmps(3))
  }

  /**
    * @param grid   Grid to visualize
    * @param colors Color scale to use
    * @param tile   Tile coordinates to visualize
    * @return The image of the tile at (x, y, zoom) showing the grid using the given color scale
    */
  def visualizeGrid(
                     grid: GridLocation => Temperature,
                     colors: Iterable[(Temperature, Color)],
                     tile: Tile
                   ): Image = {
    val locations = Interaction.tileAllLocations(tile)
    val pixels = locations.map { l =>
      val latBounds = Array(l.lat.floor, l.lat.ceil).map(_.toInt)
      val lonBounds = Array(l.lon.floor, l.lon.ceil).map(_.toInt)

      val nodes = for (y <- lonBounds; x <- latBounds) yield (x, y) // d00, d01, d10, d11
      val gridNodes = nodes.map { case (lt, ln) => grid(GridLocation(lt, ln)) } // temperatures at grid nodes
      val (x, y) = (l.lon - lonBounds(0), l.lat - latBounds(0)) // coordinates in grid square

      val temperature = bilinearInterpolation(CellPoint(x, y), gridNodes)
      Visualization.interpolateColor(colors, temperature)
    }

    val img = pixels.map { p => Pixel.apply(p.red, p.green, p.blue, 127) }
    Image(IMG_WIDTH, IMG_HEIGHT, img)
  }
}
