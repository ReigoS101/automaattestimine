package fileReaderWriter;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class fileReaderWriter {

    public static List readLinesSpecific(String inputFileAbsPath) {
        List<String> inputList = new ArrayList<>();
        List<String> fileContentList = new ArrayList<>();

        String fileName = inputFileAbsPath;

        try (BufferedReader br = Files.newBufferedReader(Paths.get(fileName))) {
            inputList = br.lines().collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }

        inputList.forEach(line -> {
            String [] split = line.split(",");
            for (int i = 0; i < split.length; i++) {
                fileContentList.add(String.valueOf(split[i]));
            }
        });

        return fileContentList;
    }

    public static void addStringToFile(String msg, String outputFileAbsPath) throws IOException {
        Files.write(Paths.get(outputFileAbsPath), msg.getBytes(), StandardOpenOption.APPEND);
    }

    public JSONArray readAllLines(String inputFileAbsPath) {
        List<String> inputList = new ArrayList<>();
        JSONArray fileContentInAnArray = new JSONArray();

        String fileName = inputFileAbsPath;

        try (BufferedReader br = Files.newBufferedReader(Paths.get(fileName))) {
            inputList = br.lines().collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }

        inputList.forEach(line -> {
            fileContentInAnArray.put(line);
        });

        return fileContentInAnArray;
    }

}
