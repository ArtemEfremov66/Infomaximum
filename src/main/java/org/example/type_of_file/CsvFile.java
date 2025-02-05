package org.example.type_of_file;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.example.Calculations;

import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CsvFile implements SomeFile {
    private final String sortedFileName;

    public CsvFile(String sortedFileName) {
        this.sortedFileName = sortedFileName;
    }

    Calculations csvCalculations = new Calculations();

    public void readAndCalculate() {
        try (CSVReader csvReader = new CSVReader(new FileReader(sortedFileName))) {
            String[] nextLine;
            List<String[]> list = new ArrayList<>();
            int bufferSize = 200_000;
            while ((nextLine = csvReader.readNext()) != null) {
                if (nextLine[0].equals("group")) { //Пропускаем первую строку
                    continue;
                }
                list.add(nextLine);
                if (list.size() == bufferSize) {
                    csvCalculations.duplicate(list);
                    csvCalculations.sumWeight(list);
                    csvCalculations.maximumWeight(list);
                    csvCalculations.minimumWeight(list);
                    list.clear();
                }
            }
            if (!list.isEmpty()) {
                csvCalculations.duplicate(list);
                csvCalculations.sumWeight(list);
                csvCalculations.maximumWeight(list);
                csvCalculations.minimumWeight(list);
                list.clear();
            }

        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }
    }

    public HashMap<String, Integer> getDuplicate() {
        return csvCalculations.getDuplicate();
    }

    public HashMap<String, BigInteger> getSumWeight() {
        return csvCalculations.getSumWeight();
    }

    public long getMaximum() {
        return csvCalculations.getMaximum();
    }

    public long getMinimum() {
        return csvCalculations.getMinimum();
    }
}
