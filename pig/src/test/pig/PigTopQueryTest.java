import org.apache.pig.pigunit.PigTest;
import org.junit.Test;

import java.io.File;

/**
 * Created by dima on 17/01/2017.
 */
public class PigTopQueryTest {

    String[] datain = {
            "Chevrolet Chevelle Malibu\t18.0\t8\t307.0\t130.0\t3504.\t12.0\t70\tUS",
            "Buick Skylark 320\t15.0\t8\t350.0\t165.0\t3693.\t11.5\t70\tUS",
            "Plymouth Satellite\t18.0\t8\t318.0\t150.0\t3436.\t11.0\t70\tUS",
            "AMC Rebel SST\t16.0\t8\t304.0\t150.0\t3433.\t12.0\t70\tUS",
            "Ford Torino\t17.0\t8\t302.0\t140.0\t3449.\t10.5\t70\tUS",
            "Ford Galaxie 500\t15.0\t8\t429.0\t198.0\t4341.\t10.0\t70\tUS",
            "Chevrolet Impala\t14.0\t8\t454.0\t220.0\t4354.\t9.0\t70\tUS",
            "Plymouth Fury iii\t14.0\t8\t440.0\t215.0\t4312.\t8.5\t70\tUS",
            "Pontiac Catalina\t14.0\t8\t455.0\t225.0\t4425.\t10.0\t70\tUS",
    };

    File pigScript = new File("src/main/pig/fast-cars.pig");


    @Test
    public void testCarsScript() throws Throwable {

        System.out.println("Running pig script:" + pigScript.getAbsolutePath());
        assert pigScript.canRead();
        File inputDataFile = new File("target/test-classes/cars-details.csv");
        System.out.println("Using input data file:" + inputDataFile.getAbsolutePath());
        assert inputDataFile.canRead();

        String[] args = {"INPUT_FILE=" + inputDataFile.getAbsolutePath(), "OUTPUT_FILE=" + inputDataFile.getAbsolutePath() + ".out.csv"};


        PigTest test = new PigTest(pigScript.getAbsolutePath(), args);
//        test.unoverride("DUMP");
        test.assertOutput("report", new String[]{"(US,7)"});

    }

    @Test
    public void testCarsScriptFromStringInput() throws Throwable {
        System.out.println("Running pig script:" + pigScript.getAbsolutePath());
        assert pigScript.canRead();

        String[] args = {"INPUT_FILE=in.csv", "OUTPUT_FILE=out.csv"};

        PigTest test = new PigTest(pigScript.getAbsolutePath(), args);
//        test.unoverride("DUMP");
        test.assertOutput("cars", datain, "report", new String[]{"(US,2)"});

    }
}
