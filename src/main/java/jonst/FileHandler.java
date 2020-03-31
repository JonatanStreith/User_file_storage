package jonst;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class FileHandler {


    //To avoid clutter, all methods just throw the exceptions further up to be handled centrally.

    public static void read(Path filePath) throws IOException {
        if (Files.exists(filePath)) {

            try (BufferedReader reader = Files.newBufferedReader(filePath)) {
                String line;

                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }

                System.out.println("\nEnd of file reached.");
            }

        } else {
            System.out.println("File not found.");
        }


    }

    public static void write(Path filePath) throws IOException {
        if (Files.exists(filePath)) {

            try (BufferedWriter writer = Files.newBufferedWriter(filePath, StandardOpenOption.APPEND)) {

                String input = Tools.getReply("What do you want to write to this file? ");

                writer.write(input);

                System.out.println("Text added.");
            }


        } else {
            System.out.println("File not found.");
        }


    }

    public static void clear(Path filePath) throws IOException {
        if (Files.exists(filePath)) {

            try (BufferedWriter writer = Files.newBufferedWriter(filePath)) {

                writer.write("");

                System.out.println("File cleared.");
            }


        } else {
            System.out.println("File not found.");
        }


    }

    public static void newfile(Path filePath) throws IOException {

        if (Files.notExists(filePath)) {
            if (Files.notExists(filePath.getParent())) {          //If the directory we specify for our file doesn't exist, create it too.
                Files.createDirectories(filePath.getParent());
            }

            filePath.toFile().createNewFile();
            System.out.println("File created.");
        } else {
            System.out.println("A file of that name already exists.");
        }


    }

    public static void newfolder(Path filePath) throws IOException {

        if (Files.notExists(filePath)) {
            Files.createDirectories(filePath);
            System.out.println("Folder created.");
        } else {
            System.out.println("A folder of that name already exists.");
        }

    }

    public static void move(Path filePath, Path destination) throws IOException {

        if (Files.exists(filePath)) {
            if (Files.exists(destination.resolve(filePath.getFileName()))) {
                System.out.println("A file by that name already exists at the destination.");
            } else {

                if (Files.notExists(destination)) {
                    Files.createDirectories(destination);      //Make sure the file can be moved to somewhere that exists.
                }

                Files.move(filePath, destination.resolve(filePath.getFileName()));

                System.out.println("Folder or file moved.");
            }

        } else {
            System.out.println("File doesn't exist.");
        }
    }

    public static void copy(Path filePath, Path destination) throws IOException {
        if (Files.exists(filePath)) {
            if (Files.exists(destination.resolve(filePath.getFileName()))) {
                System.out.println("A file by that name already exists at the destination.");
            } else {

                if (Files.notExists(destination)) {
                    Files.createDirectories(destination);      //Make sure the file can be moved to somewhere that exists.
                }

                Files.copy(filePath, destination.resolve(filePath.getFileName()));

                System.out.println("Folder or file copied.");
            }

        } else {
            System.out.println("File doesn't exist.");
        }
    }

    public static void delete(Path filePath) throws IOException {
        if (Files.exists(filePath)) {

            Files.delete(filePath);

            System.out.println("File deleted.");

        } else {
            System.out.println("File doesn't exist.");
        }
    }

    public static void list(Path filePath) throws IOException {

        if(Files.exists(filePath) && Files.isDirectory(filePath)) {
            Files.list(filePath)
                    .forEach(System.out::println);
        } else {
            System.out.println("Folder not found.");
        }
    }

}
