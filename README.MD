  [![Build Status](https://travis-ci.org/dumitruc/cars.svg?branch=master)](https://travis-ci.org/dumitruc/cars)
  
  # The application
  A simple application to try and exercise Spark functionality to identify various testing requirements and solution.
  ## Funtionality
  Take a CSV list of Cars, with various properties, remove bad records, create an output file.
    **Arguments to the application:**
 - spark.cars.csv.in - Full path
 - spark.cars.csv.out - Full path
    
  ## Running from IDE
      -Dspark.master=local -Dspark.cars.csv.in=/Users/dima/IdeaProjects/BigData/spark/cars/src/main/resources/cars-details.csv -Dspark.cars.csv.out=/Users/dima/IdeaProjects/BigData/spark/cars/src/main/resources/cars-cleaned.csv

    ### Setup
    Repository required:
    ```xml
     <repositories>
                <repository>
                    <id>cloudera</id>
                    <url>https://repository.cloudera.com/artifactory/cloudera-repos/</url>
                </repository>
                    <repository>
                        <snapshots/>
                        <id>maven central</id>
                        <name>Maven Repository</name>
                        <url>http://repo1.maven.org/maven2</url>
                    </repository>
                        <repository>
                            <id>cloudera</id>
                            <url>https://repository.cloudera.com/artifactory/cloudera-repos/</url>
                        </repository>
                </repositories>

                <pluginRepositories>
                    <pluginRepository>
                        <id>maven central plugin</id>
                        <name>Maven Plugin Repository</name>
                        <url>http://repo1.maven.org/maven2</url>
                        <layout>default</layout>
                        <snapshots>
                            <enabled>false</enabled>
                        </snapshots>
                        <releases>
                            <updatePolicy>never</updatePolicy>
                        </releases>
                    </pluginRepository>
                </pluginRepositories>
                ```


  # Findings and ideas

  - whe using Cucumber, is best to use it with JUnit rather than Scala, although it is possible, there might arise different version issues and the scla - cucumber plugins and libraries are in their infancy.
  - Unit test ideally should be of the language type
  - Does not require full SPARK env, can run locally


  # TODO
  
- [x] Change to pass the input output as arguments
- [ ] Structure POM so that all dependencies are in a separate JAR. This will avoid building big JARs in Artifactory everytime
- [ ] Write vrious DF validation test (i.e. compary 2 data frames)
- [ ] Create Integration tests to run on a separate standlone cluster
- [ ] Add pig and pig tests
- [ ] Add sqoop and sqoop tests
- [ ] Maven profile to execute app on separate standalone cluster
- [ ] Maven profile to test app on separate standalone cluster
- [ ] wire Cucumber to JUnit
- [ ] Wire cucumber to run by deploying and running on local cluster
- [ ] Document setup & environment pre-req
- [ ] Create a AWS pipeline
- [ ] Create a docker image that runs jenkins
- [ ] Create shell + groovy scripts that run on the DOCKER Jenkins
- [ ] Configure Jenkins jobs to instantiate HADOOP cloudera docker(s) (3 cluster mode)
- [ ] Jenkins job to deploy&run test in the cluster
- [ ] Jenkins job to deploy&run app in "prod" cluster (another set of docker images)




  #Execution notes.

  - Submit a job to a running Spark Environment (talking local only)
  - Solution for OutOfMemory due to hive on OSX
  ```bash
  JAVA_TOOL_OPTIONS="-Xmx1024m -XX:MaxPermSize=512m -Xms512m"
  ```

```bash
spark-submit \
  --class com.dumitruc.spark.example.App \
  --master spark://${HOSTNAME}:7077 \
  --driver-memory 512m \
  --executor-memory 512m \
  --conf spark.eventLog.enabled=false \
  --conf spark.cars.csv.in=cars/src/main/resources/cars-details.csv \
  --conf spark.cars.csv.out=cars/src/main/resources/cars-cleaned.csv \
  --name "Cars" \
  cars/target/cars-1.0-SNAPSHOT-jar-with-dependencies.jar
```
