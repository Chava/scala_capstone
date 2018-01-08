package observatory

import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner

class ExtractionTest extends FunSuite {

  test("Stations should be loaded correctly") {
    val stn = Extraction.readStations("/stations.csv")
    assert(stn.collect.size == 28128)
  }

  test("Temperature values should be loaded correctly") {
    val tmp = Extraction.readTemperature("/1975.csv", 1975)
    assert(tmp.collect.size == 2190974)
  }

  test("Temperature values should be matched to locations correctly") {
    val tmp = Extraction.locateTemperatures(1975, "/stations.csv", "/1975.csv")
    assert(tmp.size == 2177190)
  }
}