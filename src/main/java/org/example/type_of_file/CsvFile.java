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
    private final String path;

    public CsvFile(String path) {
        this.path = path;
    }

    Calculations csvCalculations = new Calculations();

    public void read() {
        try (CSVReader csvReader = new CSVReader(new FileReader(path))) {
            String[] nextLine;
            List<String[]> list = new ArrayList<>();
            int maxIndex = 210_000;
            while ((nextLine = csvReader.readNext()) != null) {
                list.add(nextLine);
                if (list.size() == maxIndex) {
                    csvCalculations.duplicate(list);
                    csvCalculations.weight(list);
                    list.clear();
                }
            }
            if (!list.isEmpty()) {
                csvCalculations.duplicate(list);
                csvCalculations.weight(list);
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
