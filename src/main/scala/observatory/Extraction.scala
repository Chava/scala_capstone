package observatory

import java.io.InputStream
import java.time.LocalDate

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

import scala.io.Source

/**
  * 1st milestone: data extraction
  */
object Extraction {

  lazy val conf: SparkConf = new SparkConf().setAppName("observatory").setMaster("local[*]")
  lazy val sc: SparkContext = new SparkContext(conf)

  /**
    * @param year             Year number
    * @param stationsFile     Path of the stations resource file to use (e.g. "/stations.csv")
    * @param temperaturesFile Path of the temperatures resource file to use (e.g. "/1975.csv")
    * @return A sequence containing triplets (date, location, temperature)
    */
  def locateTemperatures(year: Year, stationsFile: String, temperaturesFile: String): Iterable[(LocalDate, Location, Temperature)] = {
    val stations = readStations(stationsFile).collect.toMap // Map[Station, Location]
    val temperature = readTemperature(temperaturesFile, year) // RDD[(Station, LocalDate, Temperature)]

    temperature.flatMap {
      case (stn, date, tmp) => {
        val loc = stations.get(stn)
        loc match {
          case Some(lcn: Location) => Some((date, lcn, tmp))
          case None => None
        }
      }
      case _ => None
    }.collect
  }

  /**
    * @param records A sequence containing triplets (date, location, temperature)
    * @return A sequence containing, for each location, the average temperature over the year.
    */
  def locationYearlyAverageRecords(records: Iterable[(LocalDate, Location, Temperature)]): Iterable[(Location, Temperature)] = {
    sc.parallelize(records.toSeq).groupBy(_._2).mapValues {
      records => records.map(_._3).sum / records.size
    }.collect
  }

  def readStations(stationsFile: String): RDD[(Station, Location)] = {
    sc.textFile(getClass.getResource(stationsFile).getPath).flatMap { line =>
      val str = line.split(",", 4)
      val pattern = (str(0), str(1), str(2), str(3))
      pattern match {
        case ("", "", _, _) | (_, _, "", _) | (_, _, _, "") => None
        case (stn, wban, lat, lng) => Some((Station(stn, wban), Location(lat.toDouble, lng.toDouble)))
        case _ => None
      }
    }
  }

  def readTemperature(temperatureFile: String, year: Year): RDD[(Station, LocalDate, Temperature)] = {
    sc.textFile(getClass.getResource(temperatureFile).getPath).flatMap { line =>
      val str = line.split(",", 5)
      val pattern = (str(0), str(1), str(2), str(3), str(4))
      pattern match {
        case ("", "", _, _, _) | (_, _, "", _, _) | (_, _, _, "", _) | (_, _, _, _, "") => None
        case (stn, wban, month, day, tmp) => Some((Station(stn, wban),
          LocalDate.of(year, month.toInt, day.toInt), Convert.fToC(tmp.toDouble)))
        case _ => None
      }
    }
  }
}
