package com.dumitruc.spark.example

/**
  * Created by dima on 28/12/2016.
  */

import org.apache.spark.sql.types._
import org.apache.spark.sql.{DataFrame, Row, SQLContext}
import org.apache.spark.{SparkConf, SparkContext}
import org.junit.Test
import org.scalatest.Assertions

class CleanJunitTest extends Assertions {

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


  @Test def ignoreHeader() = {
    val headers: String =
      """|Car;MPG;Cylinders;Displacement;Horsepower;Weight;Acceleration;Model;Origin
         |Chevrolet Chevelle Malibu;18.0;8;307.0;130.0;3504.;12.0;70;US""".stripMargin

    val clean = CleanRecords.apply(sc, stringToDf(headers)).validCars.clean
    clean.show
    assert(clean.count() === 1)
  }

  @Test def ignoreDataTypeDescriptionRow() = {
    val headers: String =
      """|STRING;DOUBLE;INT;DOUBLE;DOUBLE;DOUBLE;DOUBLE;INT;CAT
         |Chevrolet Chevelle Malibu;18.0;8;307.0;130.0;3504.;12.0;70;US""".stripMargin

    val clean = CleanRecords.apply(sc, stringToDf(headers)).validCars.clean
    clean.show
    assert(clean.count() === 1)
  }

  @Test def ignoreEmptyStrings() = {
    val headers: String =
      """
        |Chevrolet Chevelle Malibu;18.0;8;307.0;130.0;3504.;12.0;70;US
      """.stripMargin

    val clean = CleanRecords.apply(sc, stringToDf(headers)).validCars.clean
    clean.show
    assert(clean.count() === 1)
  }

  @Test def validDataOnly() = {
    val validData =
      """Chevrolet Chevelle Malibu;18.0;8;307.0;130.0;3504.;12.0;70;US
        |Buick Skylark 320;15.0;8;350.0;165.0;3693.;11.5;70;US
        |Plymouth Satellite;18.0;8;318.0;150.0;3436.;11.0;70;US
        |AMC Rebel SST;16.0;8;304.0;150.0;3433.;12.0;70;US
        |Ford Torino;17.0;8;302.0;140.0;3449.;10.5;70;US""".stripMargin

    val clean = CleanRecords.apply(sc, stringToDf(validData)).validCars.clean
    clean.show
    assert(clean.count() === 5)
  }
}
