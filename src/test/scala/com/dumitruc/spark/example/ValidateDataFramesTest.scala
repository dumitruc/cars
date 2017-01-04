package com.dumitruc.spark.example

import com.dumitruc.spark.example.support.Utils
import com.holdenkarau.spark.testing.DataFrameSuiteBase
import org.apache.spark.sql.types.{StringType, StructField, StructType}
import org.scalatest.FlatSpec

/**
  * Created by corobced on 04/01/2017.
  */
class ValidateDataFramesTest extends FlatSpec with DataFrameSuiteBase{

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


  "Provide the input data is clean, after applying filtetr output" should " equal input" in {
    val validData =
      """Chevrolet Chevelle Malibu;18.0;8;307.0;130.0;3504.;12.0;70;US
        |Buick Skylark 320;15.0;8;350.0;165.0;3693.;11.5;70;US
        |Plymouth Satellite;18.0;8;318.0;150.0;3436.;11.0;70;US
        |AMC Rebel SST;16.0;8;304.0;150.0;3433.;12.0;70;US
        |Ford Torino;17.0;8;302.0;140.0;3449.;10.5;70;US""".stripMargin

    val srcDF = Utils.stringToDf(validData, customSchema, sqlContext)
    val clean = CleanRecords(sc, srcDF).validCars.clean
    assertDataFrameEquals(srcDF,clean)
  }
}
