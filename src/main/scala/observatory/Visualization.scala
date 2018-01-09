package observatory

import com.sksamuel.scrimage.{Image, Pixel}

/**
  * 2nd milestone: basic visualization
  */
object Visualization {

  /**
    * @param temperatures Known temperatures: pairs containing a location and the temperature at this location
    * @param location     Location where to predict the temperature
    * @return The predicted temperature at `location`
    */
  def predictTemperature(temperatures: Iterable[(Location, Temperature)], location: Location): Temperature = {
    ???
  }

  def distance(from:Location, to:Location): Int  ={
    val lt = Math.toRadians(to.lat - from.lat)
    val ln = Math.toRadians(to.lon - from.lon)
    val a = Math.sin(lt/2) * Math.sin(lt/2) +
      (Math.cos(Math.toRadians(from.lat)) *
        Math.cos(Math.toRadians(to.lat)) *
        Math.sin(ln/2)* Math.sin(ln/2))
    val c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))
    (6400 * c).toInt
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
  def visualize(temperatures: Iterable[(Location, Temperature)], colors: Iterable[(Temperature, Color)]): Image = {
    ???
  }

}

