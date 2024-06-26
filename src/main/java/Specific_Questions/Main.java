package Specific_Questions;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        // Get input data
        List<Object> data = getInputData();

        // Store data in the database
        storeInDatabase(data);
    }

    private static List<Object> getInputData() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter data in the following format: Name|Age|ID|Address|Phone");
        String input = scanner.nextLine();

        // Split the input string by the pipe character
        String[] parts = input.split("\\|");

        // Remove any leading or trailing spaces from the parts
        parts = Arrays.stream(parts).map(String::trim).toArray(String[]::new);

        // Convert the parts to the correct data types
        List<Object> data = Arrays.stream(parts)
                .map(Main::convertData)
                .collect(Collectors.toList());

        return data;
    }

    private static Object convertData(String part) {
        if (part.isEmpty()) {
            throw new IllegalArgumentException("Empty data");
        }
        if (part.length() <= 3 || !Character.isLetter(part.charAt(0))) {
            throw new IllegalArgumentException("Invalid data: " + part);
        }
        if (!Character.isDigit(part.charAt(0))) {
            throw new IllegalArgumentException("Age or ID is not an integer: " + part);
        }
        return Integer.parseInt(part);
    }

    private static void storeInDatabase(List<Object> data) {
        String url = System.getenv("DB_URL");
        String user = System.getenv("DB_USER");
        String password = System.getenv("DB_PASSWORD");

        if (url == null || user == null || password == null) {
            System.out.println("Error: Environment variables are not configured correctly.");
            return;
        }

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String sql = "INSERT INTO data_consumer (name, age, id, address, phone) VALUES (?, ?, ?, ?, ?)";

            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setObject(1, data.get(0)); // Name
                statement.setObject(2, data.get(1)); // Age
                statement.setObject(3, data.get(2)); // ID
                statement.setObject(4, data.get(3)); // Address
                statement.setObject(5, data.get(4)); // Phone

                int rowsInserted = statement.executeUpdate();
                System.out.println("Inserted " + rowsInserted + " rows successfully.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}