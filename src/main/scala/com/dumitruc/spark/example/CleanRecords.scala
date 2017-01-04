package com.dumitruc.spark.example

import org.apache.spark.SparkContext
import org.apache.spark.sql.{DataFrame, SQLContext, UserDefinedFunction}

/**
  * Created by dima on 27/12/2016.
  */
case class CleanRecords(sc: SparkContext, allCars: DataFrame) {

  val sqlContext = new SQLContext(sc)

  private var filteredCars = allCars

  import org.apache.spark.sql.functions._
  import sqlContext.implicits._

  def processMissingCategory: UserDefinedFunction = udf((value: String) =>
    if (Array("Car", "STRING") contains value)
      false else true)


  def validCars: CleanRecords = {
    filteredCars = filteredCars
      .filter(trim($"Car") !== "")
      .filter(processMissingCategory($"Car"))
      .na.drop(Seq("Car"))
    this
  }

  def knownMpg: CleanRecords = {
    filteredCars = filteredCars.filter($"MPG" > 0)
    this
  }

  def clean: DataFrame = filteredCars

}
