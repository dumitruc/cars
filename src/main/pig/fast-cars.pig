-- report number of cars from each country with acceleration less than 10 seconds
cars = LOAD '${INPUT_FILE}' USING PigStorage(';') AS (Car:chararray,MPG:chararray,Cylinders:chararray,Displacement:chararray,Horsepower:chararray,Weight:chararray,Acceleration:chararray,Model:chararray,Origin:chararray);
cars_filtered = FILTER cars BY (double) Acceleration < 10.0;
cars_grouped = GROUP cars_filtered by Origin;
report = FOREACH cars_grouped GENERATE group, COUNT(cars_filtered);
STORE report INTO '$OUTPUT_FILE' using PigStorage(',');