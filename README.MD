#Findings and ideas

 * whe using Cucumber, is best to use it with JUnit rather than Scala, although it is possible, there might arise different version issues and the scla - cucumber plugins and libraries are in their infancy.

  * Unit test ideally should be of the language type

  * Does not require full SPARK env, can run locally


  #TODO
  * Change to pass the input output as arguments
  * wire Cucumber to JUnit
  * Wire cucumber to run by deplying and running on local cluster


   #Execution notes.

   * Submit a job to a running Spark Environment (talking local only)


   spark-submit \
     --class com.dumitruc.spark.example.App \
     --master spark://MobilePlatform.local:7077[2] \
     /Users/corobced/IdeaProjects/_EDP/cars/target/cars-1.0-SNAPSHOT-jar-with-dependencies.jar


   * configure Intellij RUN configurations (run & test)