package org.example;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class MergeSorted {

        private static final int MAX_LINES_IN_MEMORY = 200_000; // Максимальное количество строк для фрагмента
        private static final String TEMP_FILES_PREFIX = "temp_sort_";
        private static final String SORTED_FILE_NAME = "sorted_output.csv";

        public static void sortCsv(String inputFilePath) throws IOException {
            // 1. Разбиение на фрагменты
            List<File> tempFiles = splitFile(inputFilePath);

            // 2. Сортировка фрагментов
            sortTempFiles(tempFiles);

            // 3. Слияние отсортированных фрагментов
            mergeSortedFiles(tempFiles, SORTED_FILE_NAME);

            // Очистка временных файлов
            deleteTempFiles(tempFiles);
        }

        // 1. Разбиение на фрагменты
        private static List<File> splitFile(String inputFilePath) throws IOException {
            List<File> tempFiles = new ArrayList<>();
            BufferedReader reader = Files.newBufferedReader(Paths.get(inputFilePath), StandardCharsets.UTF_8);
            String line;
            int fileCounter = 0;
            int lineCounter = 0;
            List<String> lines = new ArrayList<>();

            while ((line = reader.readLine()) != null) {
                lines.add(line);
                lineCounter++;

                if (lineCounter >= MAX_LINES_IN_MEMORY) {
                    File tempFile = createTempFile(fileCounter, lines);
                    tempFiles.add(tempFile);
                    lines.clear();
                    lineCounter = 0;
                    fileCounter++;
                }
            }

            // Обрабатываем оставшиеся строки
            if (!lines.isEmpty()) {
                File tempFile = createTempFile(fileCounter, lines);
                tempFiles.add(tempFile);
            }
            reader.close();
            return tempFiles;
        }


        private static File createTempFile(int fileCounter, List<String> lines) throws IOException {
            File tempFile = File.createTempFile(TEMP_FILES_PREFIX + fileCounter, ".csv");
            try (BufferedWriter writer = Files.newBufferedWriter(tempFile.toPath(), StandardCharsets.UTF_8)) {
                for (String line : lines) {
                    writer.write(line);
                    writer.newLine();
                }
            }
            return tempFile;
        }


        // 2. Сортировка фрагментов
        private static void sortTempFiles(List<File> tempFiles) throws IOException {
            for (File tempFile : tempFiles) {
                List<String> lines = Files.readAllLines(tempFile.toPath(), StandardCharsets.UTF_8);
                lines.sort(Comparator.comparing(line -> (line.split(",")[0])+line.split(",")[1]));  // Сортировка по первому столбцу
                try (BufferedWriter writer = Files.newBufferedWriter(tempFile.toPath(), StandardCharsets.UTF_8)) {
                    for (String line : lines) {
                        writer.write(line);
                        writer.newLine();
                    }
                }
            }
        }


        // 3. Слияние отсортированных фрагментов
        private static void mergeSortedFiles(List<File> tempFiles, String outputFileName) throws IOException {
            List<BufferedReader> readers = new ArrayList<>();
            for(File file : tempFiles){
                readers.add(Files.newBufferedReader(file.toPath(), StandardCharsets.UTF_8));
            }
            BufferedWriter writer = Files.newBufferedWriter(Paths.get(outputFileName), StandardCharsets.UTF_8);

            PriorityQueue<LineBuffer> minHeap = new PriorityQueue<>();

            for (BufferedReader reader : readers) {
                String line = reader.readLine();
                if(line != null) {
                    minHeap.offer(new LineBuffer(line, reader));
                } else{
                    reader.close();
                }
            }

            while (!minHeap.isEmpty()) {
                LineBuffer minLine = minHeap.poll();
                writer.write(minLine.line);
                writer.newLine();

                String nextLine = minLine.reader.readLine();
                if(nextLine != null){
                    minHeap.offer(new LineBuffer(nextLine, minLine.reader));
                } else {
                    minLine.reader.close();
                }

            }
            writer.close();

        }


        private static class LineBuffer implements Comparable<LineBuffer> {
            String line;
            BufferedReader reader;

            LineBuffer(String line, BufferedReader reader){
                this.line = line;
                this.reader = reader;
            }

            @Override
            public int compareTo(LineBuffer other) {
                String[] thisLineValues = this.line.split(",");
                String[] otherLineValues = other.line.split(",");

                return (thisLineValues[0]+thisLineValues[1]).compareTo(otherLineValues[0]+otherLineValues[1]); // compare by first column
            }
        }

        // Очистка временных файлов
        private static void deleteTempFiles(List<File> tempFiles) {
            for (File tempFile : tempFiles) {
                tempFile.delete();
            }
        }
        public static String getSortedFileName() {
            return SORTED_FILE_NAME;
        }
        }