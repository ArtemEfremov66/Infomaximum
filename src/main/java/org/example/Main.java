package org.example;

import org.example.type_of_file.CsvFile;
import org.example.type_of_file.JsonFile;
import org.example.type_of_file.SomeFile;

import java.math.BigInteger;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Введите путь к файлу csv или json");
            String path = scanner.nextLine();
            if (path.equals("exit")) {
                System.out.println("Выход из приложения");
                break;
            } else {
                try {
                    String[] extension = path.split("\\.");
                    if (extension[1].equals("csv")) {
                        CsvFile csvFile = new CsvFile(path);
                        csvFile.read();
                        showAll(csvFile);

                    } else if (extension[1].equals("json")) {
                        JsonFile jsonFile = new JsonFile(path);
                        jsonFile.read();
                        showAll(jsonFile);

                    } else {
                        System.out.println("Некорректно указан путь к файлу, попробуйте еще раз");
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println("Пропишите путь к файлу и доступное расширение");
                }
            }
        }
    }

    public static void showAll(SomeFile file) {
        for (String group : file.getDuplicate().keySet()) {
            int count = file.getDuplicate().get(group);
            System.out.println("Группа и тип: " + group + ", количество дубликатов: " + count);
        }
        for (String weight : file.getSumWeight().keySet()) {
            BigInteger sumWeight = file.getSumWeight().get(weight);
            System.out.println("Группа: " + weight + " Сумма: " + sumWeight);
        }
        System.out.println("Максимальный вес: " + file.getMaximum());
        System.out.println("Минимальный вес: " + file.getMinimum());
    }
}