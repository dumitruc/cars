package com.dumitruc.spark.example

import org.apache.spark.SparkContext
import org.apache.spark.sql.{DataFrame, SQLContext}

/**
  * Created by dima on 27/12/2016.
  */
case class CleanRecords(sc: SparkContext, allCars: DataFrame) {

  val sqlContext = new SQLContext(sc)

  private var filteredCars = allCars

  import sqlContext.implicits._


  def validCars: CleanRecords = {
    filteredCars = filteredCars.filter($"Car" !== "STRING")
    this
  }

  def knownMpg: CleanRecords = {
    filteredCars = filteredCars.filter($"MPG" > 0)
    this
  }

  def clean: DataFrame = filteredCars

}
