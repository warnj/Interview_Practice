package Specific_Questions;

import java.io.File;

import java.io.FileWriter;

import java.io.IOException;

import java.text.SimpleDateFormat;

import java.util.Date;


import java.io.FileWriter;

import java.io.IOException;

import java.time.LocalDateTime;

import java.time.format.DateTimeFormatter;



public class FileUtil {



    /**

     * Creates a file at the specified path and writes the current date to it.

     *

     * @param path The path where the file should be created (including filename and extension)

     * @throws IOException If there is an error creating or writing to the file

     * @throws SecurityException If write access to the file is denied

     * @throws NullPointerException If the provided path is null

     */

    public static void createFileWithCurrentDate(String path) throws IOException, SecurityException, NullPointerException {



// 1. Input Validation

        if (path == null) {

            throw new NullPointerException("File path cannot be null.");

        }



// 2. Get Current Date and Time

        LocalDateTime now = LocalDateTime.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");

        String formattedDate = now.format(formatter);



// 3. Create File and Write Content

        try (FileWriter fileWriter = new FileWriter(path)) {

            fileWriter.write("Current Date: " + formattedDate);

        }

    }

}