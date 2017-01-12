#!/usr/bin/env bash
set -e

#This is a bit of self job...
#this file needs to be extracted before it can be executed, keeping it with the JAR for release/version control
#unzip -j ${FAT_JAR_FILE_NAME} "scripts/dev-deploy.sh" -d .
#

print_help(){
	printf "Script usage: \n\t$(basename $0) <version number>"
}

if [ $# -eq 0 ]
  then
    print_help
	exit 0
fi



VERSION=$1


FAT_JAR_FILE_NAME=cars-${VERSION}-jar-with-dependencies.jar
FAT_JAR_URL=http://artifactory.trusted.visa.com:8080/VE_IT_EDP-snapshots/com/dumitruc/spark/example/cars/${VERSION}/${FAT_JAR_FILE_NAME}
curl ${FAT_JAR_URL} -o ${FAT_JAR_FILE_NAME}
unzip -j ${FAT_JAR_FILE_NAME} "cars-details.csv" -d .
hadoop fs -copyFromLocal cars-details.csv hdfs://nameservice1/user/corobced/cars-details.csv


spark-submit \
  --class com.dumitruc.spark.example.App \
  --master yarn \
  --conf spark.cars.csv.in=cars-details.csv \
  --conf spark.cars.csv.out=cars-cleaned.csv \
  --name "Cars" \
  ${FAT_JAR_FILE_NAME}


exit 0
