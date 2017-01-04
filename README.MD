# Findings and ideas

  - whe using Cucumber, is best to use it with JUnit rather than Scala, although it is possible, there might arise different version issues and the scla - cucumber plugins and libraries are in their infancy.
  - Unit test ideally should be of the language type
  - Does not require full SPARK env, can run locally


  # TODO
  [x] Change to pass the input output as arguments
  [ ] Write vrious DF validation test (i.e. compary 2 data frames)
  [ ] Create Integration tests to run on a separate standlone cluster
  [ ] Maven profile to execute app on separate standalone cluster
  [ ] wire Cucumber to JUnit
  [ ] Wire cucumber to run by deploying and running on local cluster


  #Execution notes.

  - Submit a job to a running Spark Environment (talking local only)

```bash
spark-submit \
  --class com.dumitruc.spark.example.App \
  --master spark://${HOSTNAME}:7077 \
  --driver-memory 512m \
  --executor-memory 512m \
  --conf spark.eventLog.enabled=false \
  --conf spark.cars.csv.path=cars/src/main/resources/cars-details.csv \
  --name "Cars" \
  cars/target/cars-1.0-SNAPSHOT-jar-with-dependencies.jar
```

  - configure Intellij RUN configurations (run & test)