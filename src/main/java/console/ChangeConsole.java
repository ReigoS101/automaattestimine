package console;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class ChangeConsole {
    public static String fileName = "/Users/macbook/Desktop/automaat/src/main/java/textFiles/input.txt";

    public static int changeConsole(String cityName, String countryCode) throws IOException {
        List<String> fromInput = new ArrayList<>();
        List<String> newConfigValuesToInput = new ArrayList<>();
        try (BufferedReader br = Files.newBufferedReader(Paths.get(fileName))) {
            //br returns as stream and convert it into a List
            fromInput = br.lines().collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        fromInput.forEach(line -> {
            String [] split = line.split(",");
            for (int i = 0; i < split.length; i++) {
                newConfigValuesToInput.add(String.valueOf(split[i]));
            }
        });
        String newValue = cityName + ", " + countryCode + ", " + newConfigValuesToInput.get(2).toString().trim() + "\n";
        System.out.println(newValue);

        Files.write(Paths.get(fileName), newValue.getBytes(), StandardOpenOption.WRITE);
        return 1;
    }

    public static void main(String[] args) {
        Scanner obj = new Scanner(System.in);
        System.out.println("PRESS 1 to change values \n");
        int option = obj.nextInt();
        System.out.println(option);
        if (option == 1) {
            System.out.println("Enter city name. \n");
            String cityName = obj.next();
            System.out.println("Enter country code. \n");
            String countryCode = obj.next();
            try {
                changeConsole(cityName, countryCode);
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Error");
            }
        }
        else {
            System.out.println("Failed to change.. \n");
        }
        System.exit(0);
    }
}
