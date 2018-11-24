package pl.kostrowski.sudoku.service;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.kostrowski.sudoku.model.Sudoku;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
public class Validate {

    private final Logger LOG = LoggerFactory.getLogger(Validate.class);
    private final LoggingTool lt = new LoggingTool();

    public String importData(String[] input) {

        Sudoku sudoku = parseKnown(input);
        if (!validateRows(sudoku)){
            return "ZŁE DANE";
        }
        if (!validateColumns(sudoku)){
            return "ZŁE DANE";
        }
        if (!validateFields(sudoku)){
            return "ZŁE DANE";
        }
        return "Wszystko OK";
    }

    private boolean validateRows(Sudoku sudoku) {

        Map<Integer, Integer> inRows = new HashMap<>();

        for (int row = 0; row < 9; row++) {
            for (int column = 0; column < 9; column++) {
                Set<Integer> integers = sudoku.getOptions().get(row).get(column);
                if (integers != null && integers.size() == 1) {
                    Integer next = integers.iterator().next();
                    if (inRows.containsKey(next)) {
                        Integer integer = inRows.get(next);
                        integer++;
                        inRows.put(next, integer);
                    } else {
                        inRows.put(next, 1);
                    }
                }
            }
            for (Integer value : inRows.values()) {
                if (value > 1) {
                    return false;
                }
            }
        }
        return true;
    }


    private boolean validateColumns(Sudoku sudoku) {

        Map<Integer, Integer> inColumns = new HashMap<>();

        for (int column = 0; column < 9; column++) {
            for (int row = 0; row < 9; row++) {
                Set<Integer> integers = sudoku.getOptions().get(row).get(column);
                if (integers != null && integers.size() == 1) {
                    Integer next = integers.iterator().next();
                    if (inColumns.containsKey(next)) {
                        Integer integer = inColumns.get(next);
                        integer++;
                        inColumns.put(next, integer);
                    } else {
                        inColumns.put(next, 1);
                    }
                }
            }
            for (Integer value : inColumns.values()) {
                if (value > 1) {
                    return false;
                }
            }
        }
        return true;
    }


    private boolean validateFields(Sudoku sudoku) {

        Map<Integer, Integer> inFields = new HashMap<>();

        for (int field = 0; field < 9; field++) {

            int startX = (field / 3) * 3;
            int endX = startX + 3;
            int startY = (field % 3) * 3;
            int endY = startY + 3;

            Set<Integer> known = new HashSet<>();
            for (int row = startX; row < endX; row++) {
                for (int col = startY; col < endY; col++) {
                    Set<Integer> integers = sudoku.getOptions().get(row).get(col);
                    if (integers != null && integers.size() == 1) {
                        Integer next = integers.iterator().next();
                        if (inFields.containsKey(next)) {
                            Integer integer = inFields.get(next);
                            integer++;
                            inFields.put(next, integer);
                        } else {
                            inFields.put(next, 1);
                        }
                    }
                }
            }
            for (Integer value : inFields.values()) {
                if (value > 1) {
                    return false;
                }
            }
        }
        return true;
    }


    public Sudoku parseKnown(String[] inputFrom) {

        Sudoku sudoku = new Sudoku();
        if (inputFrom == null || inputFrom.length != 81) {
            return sudoku;
        }

        for (int i = 0; i < 81; i++) {
            if (StringUtils.isNumeric(inputFrom[i]) && !inputFrom[i].equalsIgnoreCase("")) {
                int dane = Integer.parseInt(inputFrom[i]);
                sudoku.getOptions().get(i / 9).get(i % 9).clear();
                sudoku.getOptions().get(i / 9).get(i % 9).add(dane);
            } else {
                sudoku.getOptions().get(i / 9).get(i % 9).clear();
                sudoku.getOptions().get(i / 9).get(i % 9).add(null);
            }
        }
        return sudoku;
    }
}
