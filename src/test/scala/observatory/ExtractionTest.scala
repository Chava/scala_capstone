package observatory

import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner

class ExtractionTest extends FunSuite {

  test("Stations should be loaded correctly") {
    val stn = Extraction.readStations("/stations.csv")
    assert(stn.collect.size == 28128)
  }
  
}