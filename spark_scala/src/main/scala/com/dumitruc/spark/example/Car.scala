package com.dumitruc.spark.example

import org.apache.spark.sql.types.{DoubleType, IntegerType, StringType}

/**
  * Created by dima on 28/12/2016.
  */
case class Car(name: StringType,
               MPG: DoubleType,
               Cylinders: IntegerType,
               Displaicement: DoubleType,
               Horsepower: DoubleType,
               Weight: DoubleType,
               Acceleration: DoubleType,
               Model: IntegerType,
               Origin: StringType) {

}
