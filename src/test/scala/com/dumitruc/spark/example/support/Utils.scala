package com.dumitruc.spark.example.support

import com.holdenkarau.spark.testing.SharedSparkContext
import org.apache.spark.sql.types.StructType
import org.apache.spark.sql.{DataFrame, Row, SQLContext}
import org.scalatest.FlatSpec

/**
  * Created by dima on 03/01/2017.
  */
object Utils extends FlatSpec with SharedSparkContext {

  def stringToDf(inputString: String, customSchema: StructType, sqlContext: SQLContext): DataFrame = {
    val sampleRDD = sqlContext.sparkContext.parallelize(
      inputString.split("\n").toStream.map(a => a.split(";")).toArray.toSeq)
    sqlContext.createDataFrame(sampleRDD.map(v => Row(v: _*)), customSchema)
  }


}
