package org.example;

import org.example.type_of_file.CsvFile;
import org.example.type_of_file.JsonFile;
import org.example.type_of_file.SomeFile;

import java.io.IOException;
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
            } try {
                    String[] extension = path.split("\\.");
                    if (extension[1].equals("csv")) {
                        MergeSorted.sortCsv(path); //Сортировка файла слиянием
                        CsvFile csvFile = new CsvFile(MergeSorted.getSortedFileName());
                        csvFile.readAndCalculate(); // Расчет
                        showAll(csvFile); // Вывести результаты


                    } else if (extension[1].equals("json")) {
                        JsonFile jsonFile = new JsonFile(path);
                        jsonFile.readAndCalculate();
                        showAll(jsonFile);//Для файла json дубликаты временно считаются не корректно

                    } else {
                        System.out.println("Некорректно указан путь к файлу, попробуйте еще раз");
                    }
                } catch (ArrayIndexOutOfBoundsException | IOException e) {
                    System.out.println("Пропишите путь к файлу и доступное расширение");
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