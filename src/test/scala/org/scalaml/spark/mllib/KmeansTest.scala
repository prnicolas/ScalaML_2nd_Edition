/**
  * Copyright (c) 2013-2017  Patrick Nicolas - Scala for Machine Learning - All rights reserved
  *
  * Licensed under the Apache License, Version 2.0 (the "License") you may not use this file
  * except in compliance with the License. You may obtain a copy of the License at
  *
  * http://www.apache.org/licenses/LICENSE-2.0
  *
  * Unless required by applicable law or agreed to in writing, software is distributed on an
  * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  *
  * The source code in this file is provided by the author for the sole purpose of illustrating the
  * concepts and algorithms presented in "Scala for Machine Learning".
  * ISBN: 978-1-783355-874-2 Packt Publishing.
  *
  * Version 0.99.2
  */
package org.scalaml.spark.mllib

import org.apache.log4j.{Level, Logger}
import org.apache.spark.storage.StorageLevel
import org.apache.spark.{SparkConf, SparkContext}
import org.scalaml.{Logging, Resource}
import org.scalaml.Predef._
import org.scalaml.stats.TSeries._
import org.scalaml.trading.YahooFinancials
import org.scalaml.workflow.data.DataSource
import org.scalatest.FunSuite
import org.scalatest.concurrent.ScalaFutures


final class KmeansTest extends FunSuite with ScalaFutures with Logging with Resource {

  protected val name = "Spark MLlib K-Means"
  private val K = 8
  private val NRUNS = 16
  private val MAXITERS = 200
  private val PATH = "spark/CSCO.csv"
  private val CACHE = false

  test(s"$name evaluation") {
    show(s"$name evaluation")

    Logger.getRootLogger.setLevel(Level.ERROR)
      // The Spark configuration has to be customize to your environment
    val sparkConf = new SparkConf().setMaster("local[4]")
      .setAppName("Kmeans")
      .set("spark.executor.memory", "4096m")

    implicit val sc = SparkContext.getOrCreate(sparkConf) // no need to load additional jar file

    extract.map(input => {
      val volatilityVol = zipToSeries(input._1, input._2)

      val config = new KmeansConfig(K, MAXITERS, NRUNS)

      val rddConfig = RDDConfig(CACHE, StorageLevel.MEMORY_ONLY)
      val kmeans = Kmeans(config, rddConfig, volatilityVol)

      show(s"$name \n${kmeans.toString}\nPrediction:\n")
      val obs = Array[Double](0.23, 0.67)
      val clusterId1 = kmeans |> obs
      show(s"(${obs(0)},${obs(1)}) => Cluster #$clusterId1")

      val obs2 = Array[Double](0.56, 0.11)
      val clusterId2 = kmeans |> obs2
      val result = s"(${obs2(0)},${obs2(1)}) => Cluster #$clusterId2"
      show(s"$name result: $result")
    })

    // SparkContext is cleaned up gracefully
    sc.stop
  }

  private def extract: Option[(DblVec, DblVec)] = {
    import scala.util._
    val extractors = List[Array[String] => Double](
      YahooFinancials.volatility,
      YahooFinancials.volume
    )

    DataSource(getPath(PATH).get, true).map(_.|>) match {
      case Success(pfnSrc) => pfnSrc(extractors).map(res => ((res(0).toVector, res(1).toVector))).toOption
      case Failure(e) =>
        failureHandler(e)
        None
    }
  }
}


// ---------------------------------  EOF -------------------------------------------------