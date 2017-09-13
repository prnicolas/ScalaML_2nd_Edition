package org.scalaml.unsupervised.functionapprox

/**
 * Created by patrick.nicolas on 2/11/17.
 */
trait FunctionApprox[T] {
  def predict(t: T): Double
}
