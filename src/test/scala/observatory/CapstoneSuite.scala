package observatory

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class CapstoneSuite
//  extends ExtractionTest
//  extends VisualizationTest
//  extends InteractionTest
//    extends ManipulationTest
    extends Visualization2Test
    with Interaction2Test

