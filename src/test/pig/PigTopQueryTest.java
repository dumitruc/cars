import org.apache.pig.pigunit.PigTest;
import org.junit.Test;

import java.io.File;

/**
 * Created by dima on 17/01/2017.
 */
public class PigTopQueryTest {


    @Test
    public void testCarsScript() throws Throwable {
        File pigScript = new File("target/classes/fast-cars.pig");
        System.out.println("Running pig script:" + pigScript.getAbsolutePath());
        assert pigScript.canRead();
        File inputDataFile = new File("target/classes/cars-details.csv");
        System.out.println("Using input data file:" + inputDataFile.getAbsolutePath());
        assert inputDataFile.canRead();

        String[] args = {"INPUT_FILE=" + inputDataFile.getAbsolutePath(), "OUTPUT_FILE=" + inputDataFile.getAbsolutePath() + ".out.csv"};


        PigTest test = new PigTest(pigScript.getAbsolutePath(), args);
//        test.unoverride("DUMP");
        test.assertOutput("report", new String[]{"(US,7)"});

    }
}
