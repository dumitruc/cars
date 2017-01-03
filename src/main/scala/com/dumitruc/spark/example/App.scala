package com.dumitruc.spark.example

import org.apache.spark.sql.types._
import org.apache.spark.sql.{DataFrame, SQLContext}
import org.apache.spark.{SparkConf, SparkContext}

/**
  * @author ${user.name}
  */
object App extends App {

  val environment = System.getProperty("spark.master")

  println("Execution environment: "+ environment)

  val sc = new SparkContext(new SparkConf())


  private val carsInputFile = "/Users/corobced/IdeaProjects/_EDP/cars/src/main/resources/cars-details.csv"


  val allCars = readCars(sc, carsInputFile)

  println("Total records in file: "+ allCars.count())

  val cleanCarsRecords = CleanRecords(sc,allCars)
    .validCars
    .knownMpg
    .clean

  cleanCarsRecords.show()
  println ("Total records after the filtering:" + cleanCarsRecords.count())


  def readCars(sc: SparkContext, jsonPath: String): DataFrame = {
    val sqlContext = new SQLContext(sc)

    val carsDataFrame = sqlContext.read
      .format("com.databricks.spark.csv")
      .option("header","true")
      .option("delimiter",";")
      .schema(customSchema)
      .load(jsonPath)

    carsDataFrame
  }

  val customSchema = StructType(Array(
    StructField("Car", StringType, true),
    StructField("MPG", DoubleType, true),
    StructField("Cylinders", IntegerType, true),
    StructField("Displacement", DoubleType, true),
    StructField("Horsepower", DoubleType, true),
    StructField("Weight", DoubleType, true),
    StructField("Acceleration", DoubleType, true),
    StructField("Model", IntegerType, true),
    StructField("Origin", StringType, true)
  ))

  sc.stop()
}

