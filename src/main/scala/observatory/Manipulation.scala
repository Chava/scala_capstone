package observatory

import scala.math.{abs}

/**
  * 4th milestone: value-added information
  */
object Manipulation {

  /**
    * @param temperatures Known temperatures
    * @return A function that, given a latitude in [-89, 90] and a longitude in [-180, 179],
    *         returns the predicted temperature at this location
    */
  def makeGrid(temperatures: Iterable[(Location, Temperature)]): GridLocation => Temperature = {
    (grid: GridLocation) => Visualization.predictTemperature(temperatures, Location(grid.lat, grid.lon))
  }

  /**
    * @param temperaturess Sequence of known temperatures over the years (each element of the collection
    *                      is a collection of pairs of location and temperature)
    * @return A function that, given a latitude and a longitude, returns the average temperature at this location
    */
    def average(temperaturess: Iterable[Iterable[(Location, Temperature)]]): GridLocation => Temperature = {
      val data = temperaturess.map(makeGrid)
      (grid: GridLocation) => data.map(_(grid)).sum / temperaturess.size
  }

  /**
    * @param temperatures Known temperatures
    * @param normals A grid containing the “normal” temperatures
    * @return A grid containing the deviations compared to the normal temperatures
    */
  def deviation(temperatures: Iterable[(Location, Temperature)], normals: GridLocation => Temperature): GridLocation => Temperature = {
    (grid: GridLocation) => makeGrid(temperatures)(grid) - normals(grid)
  }
}

