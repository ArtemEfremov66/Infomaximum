package org.example.type_of_file;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.Calculations;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class JsonFile implements SomeFile {
    private final String path;
    Calculations jsonCalculations = new Calculations();

    public JsonFile(String path) {
        this.path = path;
    }

    public void readAndCalculate() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode jsonNode = objectMapper.readTree(new File(path));
            List<String[]> valuesList = new ArrayList<>();
            Iterator<JsonNode> elements = jsonNode.elements();
            int bufferSize = 200_000;
            while (elements.hasNext()) {
                JsonNode node = elements.next();
                String group = node.get("group").asText();
                String type = node.get("type").asText();
                String number = node.get("number").asText();
                String weight = node.get("weight").asText();
                String[] line = {group, type, number, weight};
                valuesList.add(line);

                if (valuesList.size() == bufferSize) {
                    jsonCalculations.duplicate(valuesList);
                    jsonCalculations.sumWeight(valuesList);
                    jsonCalculations.minimumWeight(valuesList);
                    jsonCalculations.maximumWeight(valuesList);
                    valuesList.clear();
                }
            }
            if (!valuesList.isEmpty()) {
                jsonCalculations.duplicate(valuesList);
                jsonCalculations.sumWeight(valuesList);
                jsonCalculations.minimumWeight(valuesList);
                jsonCalculations.maximumWeight(valuesList);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public HashMap<String, Integer> getDuplicate() {
        return jsonCalculations.getDuplicate();
    }

    public HashMap<String, BigInteger> getSumWeight() {
        return jsonCalculations.getSumWeight();
    }

    public long getMaximum() {
        return jsonCalculations.getMaximum();
    }

    public long getMinimum() {
        return jsonCalculations.getMinimum();
    }
}
