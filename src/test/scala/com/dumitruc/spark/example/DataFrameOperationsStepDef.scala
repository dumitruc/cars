package com.dumitruc.spark.example

/**
  * Created by dima on 22/12/2016.
  */

import cucumber.api.scala.{EN, ScalaDsl}


class DataFrameOperationsStepDef extends ScalaDsl with EN{



  When("""^(.*)$"""){ (line: String) =>
    println(line)
  }

}
