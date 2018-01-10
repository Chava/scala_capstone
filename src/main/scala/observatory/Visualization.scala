package observatory

import com.sksamuel.scrimage.{Image, Pixel}
import scala.math.{cos, pow, sin, toRadians, atan2, sqrt}

/**
  * 2nd milestone: basic visualization
  */
object Visualization {

  val EARTH_RADIUS = 6371
  val WIDTH = 360
  val HEIGHT = 180

  /**
    * @param temperatures Known temperatures: pairs containing a location and the temperature at this location
    * @param location     Location where to predict the temperature
    * @return The predicted temperature at `location`
    */

  def predictTemperature(temperatures: Iterable[(Location, Temperature)], location: Location): Temperature = {
    // Inverse distance weighting algorithm
    // https://en.wikipedia.org/wiki/Inverse_distance_weighting

    val (sl, st) = temperatures.map { case (l, t) =>
      val dst = distance(l, location)
      if (dst < 1) return t
      val w = 1.0 / pow(dst, 4.0)
      (w * t, w)
    }.unzip
    sl.sum / st.sum
  }

  def distance(from:Location, to:Location): Int  ={
    // Great-circle distance
    // https://en.wikipedia.org/wiki/Great-circle_distance

    val lt = toRadians(to.lat - from.lat)
    val ln = toRadians(to.lon - from.lon)
    val a = sin(lt/2) * sin(lt/2) + (cos(toRadians(from.lat)) *
        cos(toRadians(to.lat)) * sin(ln/2)* sin(ln/2))
    val c = 2 * atan2(sqrt(a), sqrt(1 - a))
    (EARTH_RADIUS * c).toInt
  }

  /**
    * @param points Pairs containing a value and its associated color
    * @param value  The value to interpolate
    * @return The color that corresponds to `value`, according to the color scale defined by `points`
    */
  def interpolateColor(points: Iterable[PalettePoint], value: Temperature): Color = {
    val sorted = points.toSeq.sortBy(_._1)
    if (sorted.head._1 >= value) return sorted.head._2
    if (sorted.last._1 <= value) return sorted.last._2

    val (left: PalettePoint, right: PalettePoint) = sorted.sliding(2).find {
      t => t(0)._1 <= value && t(1)._1 >= value
    } match {
      case Some(p: Seq[PalettePoint]) => (p(0), p(1))
      case None => (sorted.head, sorted.last)
    }
    doInterpolate(left, right, value)
  }

  def doInterpolate(left: PalettePoint, right: PalettePoint, value: Temperature): Color = {
    if (left.equals(right) || left._1.equals(value)) return left._2
    if (right._1.equals(value)) return right._2

    val alpha: Double = (value - left._1) / (right._1 - left._1)
    val red = alpha * (right._2.red - left._2.red) + left._2.red
    val green = alpha * (right._2.green - left._2.green) + left._2.green
    val blue = alpha * (right._2.blue - left._2.blue) + left._2.blue
    Color(math.round(red).toInt, math.round(green).toInt, math.round(blue).toInt)
  }

  /**
    * @param temperatures Known temperatures
    * @param colors       Color scale
    * @return A 360×180 image where each pixel shows the predicted temperature at its location
    */
  def visualize(temperatures: Iterable[(Location, Temperature)], colors: Iterable[PalettePoint]): Image = {
    val img = new Array[Pixel](WIDTH * HEIGHT)
    for (x <- 0 until WIDTH) {
      for (y <- 0 until HEIGHT) {
        val (lat, lng) = (90 - y, x - 180)
        val tmp = predictTemperature(temperatures, Location(lat, lng))
        val color = interpolateColor(colors, tmp)
        img(y * WIDTH + x) = Pixel.apply(color.red, color.green, color.blue, 255)
      }
    }
    Image(WIDTH, HEIGHT, img)
  }
}

