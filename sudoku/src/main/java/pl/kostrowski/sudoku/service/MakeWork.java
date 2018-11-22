package pl.kostrowski.sudoku.service;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.kostrowski.sudoku.model.Sudoku;

import java.util.*;

@Service
public class MakeWork {

    private final Logger LOG = LoggerFactory.getLogger(MakeWork.class);
    private final LoggingTool lt = new LoggingTool();

    public Sudoku importData(String[] input) {

        Sudoku sudoku = parseKnown(input);

        int bigStart = 0;
        int bigStop = 0;

        int start = 0;
        int stop = 0;

        do {
            bigStart = countKnown(sudoku);
            LOG.info("BIG start " + bigStart);

            do {
                start = countKnown(sudoku);
                cleanUpRows(sudoku);
                stop = countKnown(sudoku);
            } while (stop > start);

            analyzeRows(sudoku);
            clearUniqeInRow(sudoku);

            do {
                start = countKnown(sudoku);
                cleanUpColumns(sudoku);
                stop = countKnown(sudoku);
            } while (stop > start);

            analyzeColumns(sudoku);
            clearUniqueInColumn(sudoku);

            do {
                start = countKnown(sudoku);
                cleanUpFields(sudoku);
                stop = countKnown(sudoku);
            } while (stop > start);

            analyzeFields(sudoku);

            bigStop = countKnown(sudoku);
            LOG.info("BIG stop " + bigStop);
        } while ((bigStop > bigStart) && (bigStop!=81));

        sudoku.prepareOutput();

        return sudoku;
    }

    private Integer countKnown(Sudoku sudoku) {
        int known = 0;
        ArrayList<ArrayList<Set<Integer>>> options = sudoku.getOptions();

        for (ArrayList<Set<Integer>> option : options) {
            for (Set<Integer> integers : option) {
                if (integers.size() == 1) {
                    known++;
                }
            }
        }
        return known;
    }

    private void cleanUpFields(Sudoku sudoku) {

        ArrayList<ArrayList<Set<Integer>>> options = sudoku.getOptions();

        for (int field = 0; field < 9; field++) {

            int startX = (field / 3) * 3;
            int endX = startX + 3;
            int startY = (field % 3) * 3;
            int endY = startY + 3;

            Set<Integer> known = new HashSet<>();
            for (int row = startX; row < endX; row++) {
                for (int col = startY; col < endY; col++) {
                    if (options.get(row).get(col).size() == 1) {
                        known.addAll(options.get(row).get(col));
                    }
                }
            }

            for (int row = startX; row < endX; row++) {
                for (int col = startY; col < endY; col++) {
                    if (options.get(row).get(col).size() > 1) {
                        sudoku.getOptions().get(row).get(col).removeAll(known);
                    }
                }
            }
            LOG.trace("Field " + field + " cleared\n" + lt.createOptionsPrintout(sudoku));
        }
        LOG.info("cleanUpFields");
        LOG.info("\n" + lt.createKnownPrintout(sudoku));
        LOG.debug("\n" + lt.createOptionsPrintout(sudoku));
    }

    private void cleanUpRows(Sudoku sudoku) {
        ArrayList<ArrayList<Set<Integer>>> options = sudoku.getOptions();

        for (int row = 0; row < 9; row++) {
            Set<Integer> known = new HashSet<>();
            for (int col = 0; col < 9; col++) {
                if (options.get(row).get(col).size() == 1) {
                    known.addAll(options.get(row).get(col));
                }
            }
            for (int col = 0; col < 9; col++) {
                if (options.get(row).get(col).size() > 1) {
                    sudoku.getOptions().get(row).get(col).removeAll(known);
                }
            }
        }
        LOG.info("cleanUpRows");
        LOG.info("\n" + lt.createKnownPrintout(sudoku));
        LOG.debug("\n" + lt.createOptionsPrintout(sudoku));
    }

    private void cleanUpColumns(Sudoku sudoku) {
        ArrayList<ArrayList<Set<Integer>>> options = sudoku.getOptions();

        for (int col = 0; col < 9; col++) {
            Set<Integer> known = new HashSet<>();
            for (int row = 0; row < 9; row++) {
                if (options.get(row).get(col).size() == 1) {
                    known.addAll(options.get(row).get(col));
                }
            }
            for (int row = 0; row < 9; row++) {
                if (options.get(row).get(col).size() > 1) {
                    sudoku.getOptions().get(row).get(col).removeAll(known);
                }
            }
        }
        LOG.info("cleanUpColumns");
        LOG.info("\n" + lt.createKnownPrintout(sudoku));
        LOG.debug("\n" + lt.createOptionsPrintout(sudoku));
    }


    private void clearUniqeInRow(Sudoku sudoku) {
        for (int row = 0; row < 9; row++) {
            List<Integer> tmp = new ArrayList<>();
            Map<Integer, Integer> integerIntegerMap = sudoku.getOptionsRows().get(row);

            for (Map.Entry<Integer, Integer> entry : integerIntegerMap.entrySet()) {
                if (entry.getValue() == 1) {
                    tmp.add(entry.getKey());
                }
            }
            if (tmp.size() > 0) {
                findAndEliminateInRow(row, tmp, sudoku);
            }
        }
        LOG.info("Eliminacje z wierszy");
        LOG.info("\n" + lt.createKnownPrintout(sudoku));
        LOG.debug("\n" + lt.createOptionsPrintout(sudoku));
    }

    private void findAndEliminateInRow(int row, List<Integer> tmp, Sudoku sudoku) {
        for (Integer integer : tmp) {
            for (int column = 0; column < 9; column++) {
                if (sudoku.getOptions().get(row).get(column).contains(integer)) {
                    sudoku.getOptions().get(row).get(column).clear();
                    sudoku.getOptions().get(row).get(column).add(integer);
                    break;
                }
            }
            LOG.debug("Eliminacja z wiersza " + row + "\n" + lt.createOptionsPrintout(sudoku));
        }
    }


    private void clearUniqueInColumn(Sudoku sudoku) {
        for (int column = 0; column < 9; column++) {
            List<Integer> tmp = new ArrayList<>();
            Map<Integer, Integer> integerIntegerMap = sudoku.getOptionsColumns().get(column);

            for (Map.Entry<Integer, Integer> entry : integerIntegerMap.entrySet()) {
                if (entry.getValue() == 1) {
                    tmp.add(entry.getKey());
                }
            }
            if (tmp.size() > 0) {
                findAndEliminateInColumn(column, tmp, sudoku);
            }
        }
        LOG.info("Eliminacje z kolumn");
        LOG.info("\n" + lt.createKnownPrintout(sudoku));
        LOG.debug("\n" + lt.createOptionsPrintout(sudoku));
    }

    private void findAndEliminateInColumn(int column, List<Integer> tmp, Sudoku sudoku) {
        for (Integer integer : tmp) {
            for (int i = 0; i < 9; i++) {
                if (sudoku.getOptions().get(i).get(column).contains(integer)) {
                    sudoku.getOptions().get(i).get(column).clear();
                    sudoku.getOptions().get(i).get(column).add(integer);
                    break;
                }
            }
            LOG.debug("Eliminacja z kolumny " + column + "\n" + lt.createOptionsPrintout(sudoku));
        }
    }


    private void analyzeFields(Sudoku sudoku) {
        ArrayList<Map<Integer, Integer>> optionsFields = new ArrayList<>();
        ArrayList<ArrayList<Set<Integer>>> options = sudoku.getOptions();


        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int fromX = i * 3;
                int toX = fromX + 3;
                int fromY = j * 3;
                int toY = fromY + 3;

                int nr = i * 3 + j;
                Map<Integer, Integer> sets = new HashMap<>();

                for (int x = fromX; x < toX; x++) {
                    for (int y = fromY; y < toY; y++) {
                        Set<Integer> integers = options.get(x).get(y);
                        if (integers.size() > 1) {
                            for (Integer integer : integers) {
                                if (sets.containsKey(integer)) {
                                    sets.put(integer, sets.get(integer) + 1);
                                } else {
                                    sets.put(integer, 1);
                                }
                            }
                        }
                    }
                }
                optionsFields.add(sets);
            }
        }
        sudoku.setOptionsFields(optionsFields);
        LOG.info("analyzeFields");
        LOG.debug("\n" + lt.createOptionsFields(sudoku));
    }


    private void analyzeRows(Sudoku sudoku) {
        ArrayList<Map<Integer, Integer>> rowsOptions = new ArrayList<>();

        ArrayList<ArrayList<Set<Integer>>> options = sudoku.getOptions();

        for (int i = 0; i < 9; i++) {
            Map<Integer, Integer> rowOptions = new HashMap<>();
            for (int j = 0; j < 9; j++) {
                Set<Integer> integers = options.get(i).get(j);
                if (integers.size() > 1) {
                    for (Integer integer : integers) {
                        if (rowOptions.containsKey(integer)) {
                            rowOptions.put(integer, rowOptions.get(integer) + 1);
                        } else {
                            rowOptions.put(integer, 1);
                        }
                    }
                }
            }
            rowsOptions.add(rowOptions);
        }
        sudoku.setOptionsRows(rowsOptions);
        LOG.info("analyzeRows");
        LOG.debug("\n" + lt.createOptionsRows(sudoku));
    }

    private void analyzeColumns(Sudoku sudoku) {
        ArrayList<Map<Integer, Integer>> columnsOptions = new ArrayList<>();

        ArrayList<ArrayList<Set<Integer>>> options = sudoku.getOptions();

        for (int j = 0; j < 9; j++) {
            Map<Integer, Integer> columnOptions = new HashMap<>();
            for (int i = 0; i < 9; i++) {
                Set<Integer> integers = options.get(i).get(j);
                if (integers.size() > 1) {
                    for (Integer integer : integers) {
                        if (columnOptions.containsKey(integer)) {
                            columnOptions.put(integer, columnOptions.get(integer) + 1);
                        } else {
                            columnOptions.put(integer, 1);
                        }
                    }
                }
            }
            columnsOptions.add(columnOptions);
        }
        sudoku.setOptionsColumns(columnsOptions);
        LOG.info("analyzeColumns");
        LOG.debug("\n" + lt.createOptionsColumns(sudoku));
    }


    public Sudoku parseKnown(String[] inputFrom) {

        Sudoku sudoku = new Sudoku();
        if (inputFrom == null || inputFrom.length != 81) {
            return sudoku;
        }

        ArrayList<ArrayList<Set<Integer>>> options = sudoku.getOptions();
        String[][] style = sudoku.getStyle();

        for (int i = 0; i < 81; i++) {
            if (StringUtils.isNumeric(inputFrom[i]) && !inputFrom[i].equalsIgnoreCase("")) {
                int dane = Integer.parseInt(inputFrom[i]);
                style[i / 9][i % 9] = "znany";
                options.get(i / 9).get(i % 9).clear();
                options.get(i / 9).get(i % 9).add(dane);
            }
        }
        return sudoku;
    }
}
