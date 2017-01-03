package com.dumitruc.spark.example

import org.apache.spark.sql.types.{StringType, StructField, StructType}
import org.apache.spark.sql.{DataFrame, Row, SQLContext}
import org.apache.spark.{SparkConf, SparkContext}
import org.scalatest.{FlatSpec, FunSpec}

import com.holdenkarau.spark.testing._


/**
  * Created by dima on 02/01/2017.
  */
class CleanFlatSpecTest extends FlatSpec with SharedSparkContext {

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

  "Data selction " should " ignore header row" in {
    val headers: String =
      """|Car;MPG;Cylinders;Displacement;Horsepower;Weight;Acceleration;Model;Origin
         |Chevrolet Chevelle Malibu;18.0;8;307.0;130.0;3504.;12.0;70;US""".stripMargin

    val clean = CleanRecords.apply(sc, stringToDf(headers)).validCars.clean
    clean.show
    assert(clean.count() === 1)
  }

  it should " ignore empty rows" in {
    val headers: String =
      """
        |Chevrolet Chevelle Malibu;18.0;8;307.0;130.0;3504.;12.0;70;US
      """.stripMargin

    val clean = CleanRecords.apply(sc, stringToDf(headers)).validCars.clean
    clean.show
    assert(clean.count() === 1)
  }


  it should " ignore the data description row" in {
    val headers: String =
      """|STRING;DOUBLE;INT;DOUBLE;DOUBLE;DOUBLE;DOUBLE;INT;CAT
         |Chevrolet Chevelle Malibu;18.0;8;307.0;130.0;3504.;12.0;70;US""".stripMargin

    val clean = CleanRecords.apply(sc, stringToDf(headers)).validCars.clean
    clean.show
    assert(clean.count() === 1)
  }

  it should " read all valid rows " in {
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
