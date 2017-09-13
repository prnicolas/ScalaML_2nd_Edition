/**
 * Implementation of the function approximation for very large scale dataset using Apache Spark, broadcast,
 * Scala tail recursion and histograms
 * $DataGenerator.scala  Patrick Nicolas  Nov 2, 2015
 */
package codingchallenge.spark

import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD

import scala.collection.mutable
import scala.io.Source
import scala.util.Random


/**
 * Generator of data in RDD format by randomize an input data file. Random noise with a ratio noise/signal
 * specified by the user is added to the original data.
 * @param sourceName  Name of the file containing the template dataset
 * @param nTasks Number of concurrent tasks used in processing the data set
 */
final class DataGenerator(sourceName: String, nTasks: Int) {
  require(nTasks > 0, s"DataGenerator nTasks found $nTasks required >0")

  private final val DELIM = " "
  private final val RATIO = 0.05
  var datasetSize: Int = _

  /**
   * Constructor of a noisy data (following a uniform distribution)
   * @param sc spark context
   * @return RDD of (x, y) data set
   */
  def apply(sc: SparkContext): RDD[(Float, Float)] = {
      // See the random noise
    val r = new Random(System.currentTimeMillis + Random.nextLong)
    val src = Source.fromFile(sourceName)
    val input = src.getLines.map(_.split(DELIM))
      ./:(new mutable.ArrayBuffer[(Float, Float)])((buf, xy) => {
      val x = addNoise(xy(0).trim.toFloat, r)
      val y = addNoise(xy(1).trim.toFloat, r)
      buf += ((x, y))
    })
    datasetSize = input.size
    val data_rdd = sc.makeRDD(input, nTasks)
    src.close
    data_rdd
  }
    // Original signal + random noise
  private def addNoise(value: Float, r: Random): Float = value*(1.0 + RATIO*(r.nextDouble - 0.5)).toFloat
}

// -------------------------------------  EOF ----------------------------------------------
