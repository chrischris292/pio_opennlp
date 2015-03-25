package io.prediction.opennlp.evaluation

import io.prediction.controller.EmptyEvaluationInfo
import io.prediction.controller.Metric
import io.prediction.opennlp.engine.PredictedResult
import io.prediction.opennlp.engine.Query
import io.prediction.opennlp.engine.Interest.Interest
import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD

import org.apache.spark.SparkContext._

class AccuracyMetric
  extends Metric[EmptyEvaluationInfo, Query, PredictedResult, Interest, Double] {

  override def calculate(
    sc: SparkContext,
    data: Seq[(EmptyEvaluationInfo, RDD[(Query, PredictedResult, Interest)])]): Double = {

    val accurate = sc.accumulator(0)
    val inaccurate = sc.accumulator(0)

    data.foreach { set =>
      set._2.foreach { one =>
        val predicted = one._2.interest
        val actual = one._3.toString

        if (predicted == actual) {
          accurate += 1
        } else {
          inaccurate += 1
        }
      }
    }

    accurate.value.toDouble / (accurate.value.toDouble + inaccurate.value.toDouble)
  }
}
