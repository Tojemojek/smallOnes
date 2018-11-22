package pl.kostrowski.sudoku.model;

import lombok.Data;

import java.util.*;

@Data
public class Sudoku {

    private final int SIZE = 9;
//    private Integer[][] input = new Integer[SIZE][SIZE];
    private String[][] style = new String[SIZE + 1][SIZE + 1];

    private ArrayList<ArrayList<Set<Integer>>> options = new ArrayList<>();
    private ArrayList<Map<Integer,Integer>> optionsRows = new ArrayList<>();
    private ArrayList<Map<Integer,Integer>> optionsColumns = new ArrayList<>();
    private ArrayList<Map<Integer,Integer>> optionsFields = new ArrayList<>();

    //Fields are 3x3 nubers goes
    // 1 --> 4 --> 7
    // 2 --> 5 --> 8
    // 3 --> 6 --> 9

    private String[][] output = new String[SIZE + 2][SIZE + 1];

    public Sudoku() {
        initStyle();
        initOptions();
        initOutput();
    }

    private void initStyle() {
        for (int i = 0; i <= SIZE; i++) {
            for (int j = 0; j <= SIZE; j++) {
                style[i][j] = "nieznany";
            }
        }
    }

    private void initOptions() {
        Set<Integer> allOptions = new HashSet<>(Arrays.asList(1,2,3,4,5,6,7,8,9));

        for (int i = 0; i < SIZE; i++) {
            ArrayList<Set<Integer>> tmp = new ArrayList<>();
            for (int j = 0; j < SIZE; j++) {
                tmp.add(new HashSet<>(allOptions));
            }
            options.add(tmp);
        }

        for (int i = 0; i < SIZE; i++){
            optionsRows.add(new HashMap<>());
            optionsColumns.add(new HashMap<>());
            optionsFields.add(new HashMap<>());
        }
    }

    private void initOutput() {
        for (int i = 0; i <= SIZE; i++) {
            for (int j = 0; j <= SIZE; j++) {
                output[i][j] = "";
            }
        }
    }

    public void prepareOutput(){
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                output[i][j] = options.get(i).get(j).toString().replaceAll("\\[","").replaceAll("]","");
            }
        }
        for (int i = 0; i < SIZE; i++){
            output[i][SIZE] = optionsRows.get(i).toString().replaceAll("\\[","").replaceAll("]","");
        }

        for (int i = 0; i < SIZE; i++){
            output[SIZE][i] = optionsColumns.get(i).toString().replaceAll("\\[","").replaceAll("]","");
        }

        for (int i = 0; i < SIZE; i++){
            output[SIZE+1][i] = optionsFields.get(i).toString().replaceAll("\\[","").replaceAll("]","");
        }
    }

}
