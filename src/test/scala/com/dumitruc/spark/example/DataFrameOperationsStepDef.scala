package com.dumitruc.spark.example

/**
  * Created by dima on 22/12/2016.
  */

import cucumber.api.scala.{EN, ScalaDsl}
import org.apache.spark.sql.types.{StringType, StructField, StructType}
import org.apache.spark.sql.{DataFrame, Row, SQLContext}
import org.apache.spark.{SparkConf, SparkContext}


class DataFrameOperationsStepDef extends ScalaDsl with EN{


  //  import sqlContext.implicits._

  val conf = new SparkConf().setAppName("cars").setMaster("local")
  val sc = SparkContext.getOrCreate(conf)

  private val sqlContext = new SQLContext(sc)

  val customSchema = StructType(Array(
    StructField("Car", StringType, true),
    StructField("MPG", StringType, true),
    StructField("Cylinders", StringType, true),
    StructField("Displacement", StringType, true),
    StructField("Horsepower", StringType, true),
    StructField("Weight", StringType, true),
    StructField("Acceleration", StringType, true),
    StructField("Model", StringType, true),
    StructField("Origin", StringType, true)
  ))

  def stringToDf(inputString: String): DataFrame = {
    val sampleRDD = sqlContext.sparkContext.parallelize(
      inputString.split("\n").toStream.map(a => a.split(";")).toArray.toSeq)
    sqlContext.createDataFrame(sampleRDD.map(v => Row(v: _*)), customSchema)
  }


  //  Given("""^I have the following data(.*)$"""){(line: String) =>
  //    initialDf = stringToDf(line)
  //    initialDf.show()
  //  }
  //
  //  When("""^I clean out all of the invalid data$"""){(line: String) =>
  //   cleanDf =  CleanRecords.apply(sc, initialDf).validCars.clean
  //    cleanDf.show()
  //  }
  //
  //  When("""^Only (.*) records left in the DataFrame$"""){(count: Int) =>
  //   assert(cleanDf.count() == count)
  //  }

}
