package pl.kostrowski.sudoku.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FindPossibilitiesTest {

    @Autowired
    FindPossibilities findPossibilities;

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
    public void parseKnown() {
        findPossibilities.parseKnown(known);
    }

    @Test
    public void findPossibleOptions(){
        findPossibilities.findPossibleOptions(known);
    }
}