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
    ???
  }

  /**
    * @param records A sequence containing triplets (date, location, temperature)
    * @return A sequence containing, for each location, the average temperature over the year.
    */
  def locationYearlyAverageRecords(records: Iterable[(LocalDate, Location, Temperature)]): Iterable[(Location, Temperature)] = {
    ???
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

}
