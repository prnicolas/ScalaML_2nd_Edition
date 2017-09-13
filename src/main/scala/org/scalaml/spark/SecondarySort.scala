package org.scalaml.spark

import org.apache.spark.Partitioner
import org.apache.spark.rdd.{OrderedRDDFunctions, RDD}

/**
  * Created by patricknicolas on 4/14/17.
  */

case class KeyedItem[T, U](key1: T, key2: U)

object KeyedItem {
  implicit def orderedByKey2String[A <: KeyedItem[String, Int]]: Ordering[A] = Ordering.by(ki => (ki.key1, ki.key2))
}

class KeyedItemPartitioner(nPartitions: Int) extends Partitioner {
  override def numPartitions: Int = nPartitions
  override def getPartition(key: Any): Int = {
    val k = key.asInstanceOf[KeyedItem[String, Int]]
    k.key1.hashCode() % numPartitions
  }
}


private[scalaml] final class SecondarySort {

}


object SecondarySortApp extends App {
  val sessionCycle = new SessionLifeCycle {}

  type Entry = Array[String]
  val entries = Seq[Entry](
    Array[String]("abc", "20", "14.5"),
    Array[String]("abcd", "10", "7.5"),
    Array[String]("btw", "11", "23.8"),
    Array[String]("cgq", "10", "14.5"),
    Array[String]("cgq", "19", "0.5"),
    Array[String]("abcd", "7", "21.9"),
    Array[String]("abcd", "2", "30.0"),
    Array[String]("btw", "2", "1.0")
  )
  val entries_rdd: RDD[Array[String]] = sessionCycle.sparkContext.makeRDD(entries)


  def createKey(entry: Entry): (KeyedItem[String, Int], Double) = (KeyedItem(entry(0), entry(1).toInt), entry(2).toDouble)

  val keyedItems_rdd: RDD[(KeyedItem[String, Int], Double)] = entries_rdd.map(createKey(_))
  /*
  (KeyedItem(abc,20),14.5)
  (KeyedItem(abcd,10),7.5)
  (KeyedItem(btw,11),23.8)
  (KeyedItem(cgq,10),14.5)
  (KeyedItem(cgq,19),0.5)
  (KeyedItem(abcd,7),21.9)
  (KeyedItem(abcd,2),30.0)
  (KeyedItem(btw,2),1.0)
  */
      // conversion to OrderedRDD
  val partitionedData = keyedItems_rdd.repartitionAndSortWithinPartitions(new KeyedItemPartitioner(2))
  /*
  (KeyedItem(abc,20),14.5)
(KeyedItem(abcd,2),30.0)
(KeyedItem(abcd,7),21.9)
(KeyedItem(abcd,10),7.5)
(KeyedItem(btw,2),1.0)
(KeyedItem(btw,11),23.8)
(KeyedItem(cgq,10),14.5)
(KeyedItem(cgq,19),0.5)
   */
}
