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
package org.scalaml.spark.mlpipeline

import org.apache.spark.ml.evaluation.Evaluator
import org.apache.spark.ml.param.ParamMap
import org.apache.spark.ml.tuning.CrossValidatorModel
import org.apache.spark.ml.{Estimator, PipelineModel, Model}
import org.apache.spark.sql._


/**
  *
  * @param estimate
  * @param cols
  * @param trainFile
  * @param numFolds
  * @tparam T
  */
final private[spark] class ValidatedPredictor[T <: Model[T]](
    estimate: Estimator[T],
    cols: Array[String],
    trainFile: String,
    override val numFolds: Int = 2
) extends Predictor[T](estimate, cols, trainFile) with CrossValidation[T] {

  /**
    * Generate a pipeline model, including the logisitic regression as estimator
    * @return a trained pipeline model
    */
  def apply(): PipelineModel = this(trainDf, stages)

  /**
    * Generate a CrossValidator model
    *
    * @param paramGrid
    * @return
    */
  def apply(paramGrid: Array[ParamMap]): CrossValidatorModel = this(trainDf, stages, paramGrid)

  /**
    * Classifier for run time
    *
    * @param grid
    * @param testSet
    * @return
    */
  final def classify(grid: Array[ParamMap], testSet: String): DataFrame =
    this(trainDf, stages, grid).transform(csv2DF(testSet))

  /**
    *
    * @param grid
    * @return
    */
  final def evaluate(grid: Array[ParamMap]): Evaluator = evaluate(trainDf, stages, grid)

  /**
    *
    * @return
    */
  final def trainingWithSummary: Option[(Double, Double)] = trainWithSummary(trainDf, stages)
}

// ----------------------------------------  EOF ----------------------------------------------------------