Feature: Data frame basic operations

  Scenario: Filter
    Given I have the following data
    """
    Car;MPG;Cylinders;Displacement;Horsepower;Weight;Acceleration;Model;Origin
    Chevrolet Chevelle Malibu;18.0;8;307.0;130.0;3504.;12.0;70;US
    """
    When I clean out all of the invalid data
    Then Only 1 records left in the DataFrame

#  Scenario: Reduce
#
#  Scenario: Transform
#
#  Scenario: Create from RDD
#  Scenario: Create from Hive
#  Scenario: Create from Spark Data Source
#
#  Scenario: Group
#
#  Scenario: Join

