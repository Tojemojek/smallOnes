package pl.kostrowski.sudoku.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

public class MakeWorkTest {

    static String[] known;

    @Before
    public void init(){
        known = new String[]{
                "","","3","9","8","1","7","2","5",
                "9","1","7","4","5","2","6","","3",
                "5","","","3","7","6","9","4","1",
                "3","4","1","8","2","9","","6","",
                "8","2","","3","7","7","4","","9",
                "","9","5","6","1","4","8","","2",
                "","","8","7","4","","2","9","",
                "","","9","1","","","3","","",
                "6","5","4","2","9","","1","7","8"};
    }


    @Test
    public void testMakeWork(){

        String testData = "x,x,7,x,8,6,2,4,3," +
                "2,6,3,7,4,9,1,5,8," +
                "4,x,x,x,5,3,6,7,9," +
                "7,4,5,3,2,8,x,x,6," +
                "8,2,6,9,1,4,7,3,5," +
                "x,3,9,6,7,5,x,x,4," +
                "x,5,x,x,9,7,4,x,2," +
                "x,8,x,4,3,x,x,9,x," +
                "x,x,4,x,6,x,3,x,x,";

        String[] split = testData.split(",");

        MakeWork makeWork = new MakeWork();
        makeWork.importData(split);

    }

    @Test
    public void testMakeWorkHard(){

        String testData = "x,5,x,x,x,x,x,6,4," +
                "x,x,x,x,6,8,5,x,x," +
                "1,x,x,x,x,x,x,x,9," +
                "x,9,x,x,x,6,x,x,x," +
                "5,x,x,4,x,9,x,x,x," +
                "x,x,7,x,x,x,x,x,x," +
                "2,x,x,x,9,x,x,7,x," +
                "7,4,x,1,x,2,x,x,5," +
                "x,x,1,8,x,x,6,x,x,";

        String[] split = testData.split(",");

        MakeWork makeWork = new MakeWork();
        makeWork.importData(split);

    }

}