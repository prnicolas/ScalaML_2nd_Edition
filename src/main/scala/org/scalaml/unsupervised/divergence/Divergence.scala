package org.scalaml.unsupervised.divergence

/**
  * Created by patrick.nicolas on 2/18/17.
  */
private[scalaml] trait Divergence {
  def divergence(nSteps: Int): Double
}
