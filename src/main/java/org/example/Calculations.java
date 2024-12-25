package org.example;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class Calculations {
    private final HashMap<String, Integer> duplicate = new HashMap<>();
    private final HashSet<String> uniqueHashes = new HashSet<>();
    private final HashMap<String, BigInteger> sumWeight = new HashMap<>();
    private long maximum = 0;
    private long minimum = 635402863086387241L;

    public void duplicate(List<String[]> list) {
        List<String> shortList = new ArrayList<>();
        for (int j = 0; j < list.size(); j++) {
            shortList.add(list.get(j)[0] + " " + list.get(j)[1]);
        }
        shortList.forEach(n -> {
            String hash = getHash(n);
            if (uniqueHashes.contains(hash)) {
                duplicate.put(n, duplicate.getOrDefault(n, 0) + 1);
            } else {
                uniqueHashes.add(hash);
            }
        });
        System.out.println("Дубликаты посчитаны");
    }

    public void weight(List<String[]> list) {
        int index = 0;
        if (list.get(0)[0].equals("group")) { //Чтобы пропустить первую строку файла
            index = 1;
        }
        BigInteger sum = BigInteger.ZERO;
        for (int i = index; i < list.size(); i++) {
            if (Long.parseLong(list.get(i)[3]) > maximum) {
                maximum = Long.parseLong(list.get(i)[3]);
            }
            if (Long.parseLong(list.get(i)[3]) < minimum) {
                minimum = Long.parseLong(list.get(i)[3]);
            }
            if (sumWeight.containsKey(list.get(i)[0])) {
                sum = sum.add(new BigInteger(sumWeight.get(list.get(i)[0]).toByteArray()));
                sum = sum.add(new BigInteger(list.get(i)[3]));
                sumWeight.put(list.get(i)[0], sum);
                sum = BigInteger.ZERO;
            } else {
                sumWeight.put(list.get(i)[0], BigInteger.valueOf(Long.parseLong((list.get(i)[3]))));
            }
        }
        System.out.println("Вес объектов посчитан");
    }

    private static String getHash(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = md.digest(input.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    public HashMap<String, Integer> getDuplicate() {
        return duplicate;
    }

    public HashSet<String> getUniqueHashes() {
        return uniqueHashes;
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
