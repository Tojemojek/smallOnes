package pl.kostrowski.sudoku.service;

import lombok.Data;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

@Data
public class AdditionalFindings {

    private ArrayList<Map<Integer, Integer>> allAdditional;
    private Map<Integer, Integer> rowColumnInfo;


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        Set<Integer> integers = rowColumnInfo.keySet();

        for (Integer integer : integers) {
            sb.append(integer).append(":").append(rowColumnInfo.get(integer)).append("x").append(",");
        }
        return sb.toString();
    }
}
