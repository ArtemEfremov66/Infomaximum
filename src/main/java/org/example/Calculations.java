package org.example;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class Calculations {
    private final HashMap<String, Integer> duplicate = new HashMap<>();
    private final HashMap<String, Integer> tempDuplicate = new HashMap<>();
    private final HashMap<String, BigInteger> sumWeight = new HashMap<>();
    private long maximum = 0;
    private long minimum = Long.MAX_VALUE;

    public void duplicate(List<String[]> list) {
        for (int i = 0; i < list.size(); i++) {
            String shortLineI = list.get(i)[0] + " " + list.get(i)[1];
            tempDuplicate.put(shortLineI, tempDuplicate.getOrDefault(shortLineI, 0) + 1);
        } //Занесли список во временное хранилище, которое потом можно будет почистить
        for (Map.Entry<String, Integer> entry : tempDuplicate.entrySet()) {
            if (entry.getValue()>1) {
                duplicate.put(entry.getKey(), duplicate.getOrDefault(entry.getKey(), 0)+entry.getValue());
            } // Все повторяющиеся значения перенесли в постоянное хранилище дубликатов
        }
        tempDuplicate.clear();
        tempDuplicate.put(list.get(list.size()-1)[0] + " " + list.get(list.size()-1)[1], 1);
        // Добавили последнюю строку во временное хранилище на случай дубликатов в следующем листе
        System.out.println("Дубликаты посчитаны");
    }

    public void maximumWeight(List<String[]> list) {
        for (int i = 0; i < list.size(); i++) {
            if (Long.parseLong(list.get(i)[3]) > maximum) {
                maximum = Long.parseLong(list.get(i)[3]);
            }
        }
    }
    public void minimumWeight(List<String[]> list) {
        for (int i = 0; i < list.size(); i++) {
            if (Long.parseLong(list.get(i)[3]) < minimum) {
                minimum = Long.parseLong(list.get(i)[3]);
            }
        }
    }
    public void sumWeight(List<String[]> list) {
        BigInteger sum = BigInteger.ZERO;
        for (int i = 0; i < list.size(); i++) {
            if (sumWeight.containsKey(list.get(i)[0])) {
                sum = sum.add(new BigInteger(sumWeight.get(list.get(i)[0]).toByteArray()));
                sum = sum.add(new BigInteger(list.get(i)[3]));
                sumWeight.put(list.get(i)[0], sum);
                sum = BigInteger.ZERO;
            } else {
                sumWeight.put(list.get(i)[0], BigInteger.valueOf(Long.parseLong((list.get(i)[3]))));
            }
        }// Добавляет в хранилище сумму весов по группам
        System.out.println("Вес объектов посчитан");
    }

    public HashMap<String, Integer> getDuplicate() {
        return duplicate;
    }

    public HashMap<String, BigInteger> getSumWeight() {
        return sumWeight;
    }

    public long getMaximum() {
        return maximum;
    }

    public long getMinimum() {
        return minimum;
    }
}
