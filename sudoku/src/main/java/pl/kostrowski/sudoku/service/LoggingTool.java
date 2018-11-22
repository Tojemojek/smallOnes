package pl.kostrowski.sudoku.service;


import org.apache.commons.lang.StringUtils;
import pl.kostrowski.sudoku.model.Sudoku;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

public class LoggingTool {

    public String createOptionsPrintout(Sudoku sudoku) {

        StringBuilder sb = new StringBuilder();

        ArrayList<ArrayList<Set<Integer>>> options = sudoku.getOptions();

        for (int row = 0; row < 9; row++) {
            for (int column = 0; column < 9; column++) {
                Set<Integer> integers = options.get(row).get(column);
                String s = integers.toString().replaceAll("\\[", "").replaceAll("]", "");
                String add = StringUtils.repeat(" ", 23 - s.length());
                sb.append("[").append(s).append(add)
                        .append("]\t");
                if (column % 3 == 2) {
                    sb.append("\t\t\t");
                }
            }
            sb.append("\n");
            if (row % 3 == 2) {
                sb.append("\n");
            }
        }
        return sb.toString();
    }

    public String createKnownPrintout(Sudoku sudoku) {

        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        ArrayList<ArrayList<Set<Integer>>> options = sudoku.getOptions();

        for (int row = 0; row < 9; row++) {
            for (int column = 0; column < 9; column++) {
                Set<Integer> integers = options.get(row).get(column);
                if (integers.size() == 1) {
                    sb.append(integers).append("\t");
                } else {
                    sb.append("[ ]\t");
                }

                if (column % 3 == 2) {
                    sb.append("\t\t\t");
                }
            }
            sb.append("\n");
            if (row % 3 == 2) {
                sb.append("\n");
            }
        }
        return sb.toString();
    }

    public String createOptionsRows(Sudoku sudoku) {

        StringBuilder sb = new StringBuilder();
        ArrayList<Map<Integer, Integer>> optionsRows = sudoku.getOptionsRows();

        for (int row = 0; row < 9; row++) {
            sb.append("Row ").append(row).append(", options").append(optionsRows.get(row)).append("\n");
        }
        return sb.toString();
    }

    public String createOptionsColumns(Sudoku sudoku) {
        StringBuilder sb = new StringBuilder();
        ArrayList<Map<Integer, Integer>> optionsColumns = sudoku.getOptionsColumns();

        for (int column = 0; column < 9; column++) {
            sb.append("Columns ").append(column).append(", options").append(optionsColumns.get(column)).append("\n");
        }
        return sb.toString();
    }

    public String createOptionsFields(Sudoku sudoku) {
        StringBuilder sb = new StringBuilder();
        ArrayList<Map<Integer, Integer>> optionsColumns = sudoku.getOptionsColumns();

        for (int column = 0; column < 9; column++) {
            sb.append("Field ").append(column).append(", options").append(optionsColumns.get(column)).append("\n");
        }
        return sb.toString();
    }
}
