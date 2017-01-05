package com.dumitruc.spark.example

import java.io.File
import java.util.Date

import org.apache.spark.sql.types._
import org.apache.spark.sql.{DataFrame, SQLContext}
import org.apache.spark.{SparkConf, SparkContext}

/**
  * @author ${user.name}
  */
object App extends App {

  val environment = System.getProperty("spark.master")
  val carsInputFile = System.getProperty("spark.cars.csv.in")
  val carsOutputFile = System.getProperty("spark.cars.csv.out")
  val appID = new Date().toString + math.floor(math.random * 10E4).toLong.toString

  println("Execution environment: " + environment)
  println("Running app with id: " + appID)

  private val conf = new SparkConf()
  conf.set("spark.app.id", appID)
  conf.setAppName("Cars")
  val sc = new SparkContext(conf)

  val allCars = readCars(sc, carsInputFile)

  println("Total records in file: " + allCars.count())

  val cleanCarsRecords = CleanRecords(sc, allCars)
    .validCars
    .knownMpg
    .clean
  println("Total records after the filtering:" + cleanCarsRecords.count())

  val result = writeCleanCarRecords(sc, carsOutputFile, cleanCarsRecords)

  def readCars(sc: SparkContext, csvFilePath: String): DataFrame = {
    val sqlContext = new SQLContext(sc)

    val carsDataFrame = sqlContext.read
      .format("com.databricks.spark.csv")
      .option("header", "true")
      .option("delimiter", ";")
      .schema(customSchema)
      .load(csvFilePath)

    carsDataFrame
  }

  def writeCleanCarRecords(sc: SparkContext, csvFilePath: String, df: DataFrame): Unit = {
    val sQLContext = new SQLContext(sc)

    val tmpParquetDir = "Posts.tmp.parquet"


    try {
      df.repartition(1). //To have it one file/partition!
        write.
        format("com.databricks.spark.csv").
        option("header", "true").
        option("delimiter", ",").
        save(tmpParquetDir)

      val dir = new File(tmpParquetDir)
      val tmpTsvFile = tmpParquetDir + File.separatorChar + "part-00000"
      (new File(tmpTsvFile)).renameTo(new File(csvFilePath))
      dir.listFiles.foreach(f => f.delete)
      dir.delete
      println("The file was successfully created. Check " + carsOutputFile)


    } catch {
      case problem: Throwable => println("Something went badly!")
        println("An error encountered when trying to create file!")
    }


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

