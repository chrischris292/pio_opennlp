package io.prediction.opennlp.engine

import java.io.File
import java.io.FileReader

import io.prediction.controller.EmptyEvaluationInfo
import io.prediction.controller.EmptyParams
import io.prediction.controller.PDataSource
import io.prediction.opennlp.engine.Interest.Interest
import opennlp.maxent.BasicEventStream
import opennlp.maxent.PlainTextByLineDataStream
import opennlp.model.OnePassDataIndexer
import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD

class DataSource(val dsp: DataSourceParams) extends PDataSource[
  TrainingData,
  EmptyEvaluationInfo,
  Query,
  Interest] {

  val Separator = " "

  override def readTraining(sc: SparkContext): TrainingData = {
    val fileReader = new FileReader(new File(dsp.trainingPath))
    val eventStream = new BasicEventStream(new PlainTextByLineDataStream(fileReader), Separator)
    val dataIndexer = new OnePassDataIndexer(eventStream, dsp.cutoff)

    TrainingData(dataIndexer)
  }

  override def readEval(
    sc: SparkContext): Seq[(TrainingData, EmptyEvaluationInfo, RDD[(Query, Interest)])] = {

    val trainingData = readTraining(sc)

    val lines = scala.io.Source.fromFile(dsp.testPath.get).getLines()
    val qna = lines.map { line =>
      val lastSpace = line.lastIndexOf(Separator)
      (Query(line.substring(0, lastSpace)), Interest(line.substring(lastSpace + 1).toInt))
    }.toSeq

    Seq((trainingData, EmptyParams(), sc.parallelize(qna)))
  }
}

