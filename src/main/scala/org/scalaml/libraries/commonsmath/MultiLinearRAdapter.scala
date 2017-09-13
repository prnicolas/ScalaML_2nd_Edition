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
package org.scalaml.libraries.commonsmath

import scala.language.implicitConversions
import org.apache.commons.math3.stat.regression.OLSMultipleLinearRegression

import org.scalaml.Predef._

/**
 * Implicit conversion from internal primitive types Array[Double] and DblMatrix to Apache
 * Commons Math types.
 * @author Patrick Nicolas
 * @since January 23, 2014 0.98
 * @version 0.99.2
 * @see Scala for Machine Learning Chapter 9 Regression and regularization
 */

final private[scalaml] class MultiLinearRAdapter extends OLSMultipleLinearRegression {

  final def createModel(x: Vector[Array[Double]], y: DblVec): Unit =
    super.newSampleData(y.toArray, x.toArray)

  final def weights: Array[Double] = estimateRegressionParameters
  final def rss: Double = calculateResidualSumOfSquares
}

// ---------------------------  EOF -----------------------------