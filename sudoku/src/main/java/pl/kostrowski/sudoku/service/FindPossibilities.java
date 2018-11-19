package pl.kostrowski.sudoku.service;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class FindPossibilities {

    private final static Set<Integer> allOptions = new HashSet<>(Arrays.asList(new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9}));
    private static Integer[][] know = new Integer[9][9];
    private static String[][] style = new String[10][10];
    private static Map<String, Set<Integer>> options = new HashMap<>();

    {
        createAllOptions();
        createEmptyInput();
    }

    public String[][] findPossibleOptions(String[] inputFrom) {
        createAllOptions();
        reduceOptions(inputFrom);
        return createPrintout();
    }

    public String[][] createEmptyInput() {
        String[][] emptyInput = new String[10][10];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                emptyInput[i][j] = "";
                style[i][j] = "nieznany";
            }
        }
        return emptyInput;
    }

    private void createAllOptions() {
        String key;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                key = i + "," + j;
                HashSet all = new HashSet();
                all.addAll(allOptions);
                options.put(key, all);
            }
        }
    }

    private void reduceOptions(String[] inputFrom) {

        parseKnown(inputFrom);

        Set<String> strings = options.keySet();

        for (String string : strings) {
            Set<Integer> integers = options.get(string);
            if (integers.size() > 1) {
                String[] split = string.split(",");
                reduceRows(Integer.parseInt(split[0]), integers);
                reduceColumns(Integer.parseInt(split[1]), integers);
                reduceFields(Integer.parseInt(split[0]), Integer.parseInt(split[1]), integers);
            }
        }


    }

    private void reduceFields(int x, int y, Set<Integer> possibleOptions) {
        if (x == 9 || y == 9) {
            return;
        }

        int fromX = (x / 3) * 3;
        int toX = fromX + 3;
        int fromY = (y / 3) * 3;
        int toY = fromY + 3;

        for (int i = fromX; i < toX; i++) {
            for (int j = fromY; j < toY; j++) {
                if (know[i][j] != null) {
                    possibleOptions.remove(know[i][j]);
                }
            }
        }
    }

    private void reduceRows(Integer rowNo, Set<Integer> possibleOptions) {
        if (rowNo == 9) {
            return;
        }
        for (int i = 0; i < 9; i++) {
            if (know[rowNo][i] != null) {
                possibleOptions.remove(know[rowNo][i]);
            }
        }
    }

    private void reduceColumns(Integer colNo, Set<Integer> possibleOptions) {
        if (colNo == 9) {
            return;
        }
        for (int i = 0; i < 9; i++) {
            if (know[i][colNo] != null) {
                possibleOptions.remove(know[i][colNo]);
            }
        }
    }

    private String[][] createPrintout() {

        String[][] result = new String[10][10];
        String key;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                key = i + "," + j;
                Set<Integer> integers = options.get(key);
                int k = 0;
                sb.setLength(0);
                for (Integer integer : integers) {
                    k++;
                    sb.append(integer);
                    if (k != integers.size()) {
                        sb.append(",");
                    }
                    if (k % 3 == 0 && k != integers.size()) {
                        sb.append("<br/>");
                    }
                }
                result[i][j] = sb.toString();
            }
        }

        List<String> additionalFinding = createAdditionalFinding(result);

        for (int i = 0; i < 9;i++){
            result[i][9] = additionalFinding.get(i);
        }

        for (int i = 0; i < 9;i++){
            result[9][i] = additionalFinding.get(i+9);
        }

        return result;
    }


    public void parseKnown(String[] inputFrom) {

        String key;
        for (int i = 0; i < 81; i++) {
            if (StringUtils.isNumeric(inputFrom[i]) && !inputFrom[i].equalsIgnoreCase("")) {
                know[i / 9][i % 9] = Integer.parseInt(inputFrom[i]);
                key = i / 9 + "," + i % 9;
                Set<Integer> tmp = new HashSet<>();
                tmp.add(Integer.parseInt(inputFrom[i]));
                options.put(key, tmp);
                style[i / 9][i % 9] = "znany";
            }

        }
    }

    public void reset() {

    }

    public String[][] createStyleSheet() {
        return style;
    }

    private List<String> createAdditionalFinding(String[][] printout){
        List< Map<String, Integer>> allFindings = new LinkedList<>();
        //poziome
        for (int i = 0; i<9; i++){
            Map<String, Integer> rowColumnInfo = new HashMap<>();
            for (int j = 0; j < 9; j++)
                if (printout[i][j].length() > 1){
                    String[] splited = printout[i][j].replaceAll("<br/>", "").split(",");
                    for (String split : splited) {
                        if (rowColumnInfo.containsKey(split)) {
                            Integer integer = rowColumnInfo.get(split);
                            integer++;
                            rowColumnInfo.put(split,integer);
                        } else{
                            rowColumnInfo.put(split,1);
                        }
                    }
                }
            allFindings.add(rowColumnInfo);
        }
        //pionowe
        for (int j = 0; j<9; j++){
            Map<String, Integer> rowColumnInfo = new HashMap<>();
            for (int i = 0; i < 9; i++)
                if (printout[i][j].length() > 1){
                    String[] splited = printout[i][j].replaceAll("<br/>", "").split(",");
                    for (String split : splited) {
                        if (rowColumnInfo.containsKey(split)) {
                            Integer integer = rowColumnInfo.get(split);
                            integer++;
                            rowColumnInfo.put(split,integer);
                        } else{
                            rowColumnInfo.put(split,1);
                        }
                    }
                }
            allFindings.add(rowColumnInfo);
        }

        LinkedList<String> ret = new LinkedList<>();
        for (Map<String, Integer> allFinding : allFindings) {
            StringBuilder sb = new StringBuilder();

            Set<String> integers = allFinding.keySet();

            for (String integer : integers) {
                sb.append(integer).append(":").append(allFinding.get(integer)).append("x").append(",");
            }
            ret.add(sb.toString());
        }
        return ret;
    }
}
