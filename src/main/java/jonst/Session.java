package jonst;


import jonst.Models.User;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Session {



    public static void runSession(User user) {


        System.out.println("Welcome, user '" + user.getUserName() + "'.");

        String choice;
        do {


            choice = showMenu();

            try {
                processChoice(choice, user);
            } catch (IOException e) {
                System.out.println("There was an I/O exception.");          //This is the main exception catcher for all I/O errors.
                e.printStackTrace();
            }
            Tools.pause();

        } while (!choice.equalsIgnoreCase("quit"));

    }

    private static String showMenu() {

        System.out.println("Options:\n");

        System.out.println("Read <file>");
        System.out.println("Write <file> (append to end)");
        System.out.println("Clear <file>");
        System.out.println();
        System.out.println("Newfile <file>");
        System.out.println("Newfolder <folder>");
        System.out.println();
        System.out.println("Move <file or folder> <destination folder>");
        System.out.println("Copy <file or folder> <destination folder>");
        System.out.println("Delete <file or folder>");
        System.out.println();
        System.out.println("List (show user files and folders)");
        System.out.println("Quit");
        System.out.println();

        return Tools.getReply("What would you like to do? ");
    }

    private static void processChoice(String choice, User user) throws IOException {

        String[] input = choice.split(" ");

        Path choiceFile = user.getUserFolder();

        Path destination = null;

        if(input.length > 1) {

            choiceFile = user.getUserFolder().resolve(Paths.get(input[1]));

            if(!Tools.testForSanity(choiceFile, user.getUserFolder())){             //Test to ensure that anything the user tries to access is within their private folder ONLY.
                System.out.println("Attempted illegal file access.");               //Otherwise, they could use '/' or '..' to sneak into other folders.
                return;
            }
        }

        if(input.length > 2) {
            destination = user.getUserFolder().resolve(Paths.get(input[2]));

            if(!Tools.testForSanity(destination, user.getUserFolder())){
                System.out.println("Attempted illegal file access.");
                return;
            }
        }




        switch (input[0].toLowerCase()) {

            case "read":
                if(input.length == 1){
                    error(input[0].toLowerCase());
                } else
                FileHandler.read(choiceFile);
                break;
            case "write":
                if(input.length == 1){
                    error(input[0].toLowerCase());
                } else
                    FileHandler.write(choiceFile);
                break;

            case "clear":
                if(input.length == 1){
                    error(input[0].toLowerCase());
                } else
                    FileHandler.clear(choiceFile);
                break;

            case "newfile":
                if(input.length == 1){
                    error(input[0].toLowerCase());
                } else
                FileHandler.newfile(choiceFile);
                break;

            case "newfolder":
                if(input.length == 1){
                    error(input[0].toLowerCase());
                } else
                FileHandler.newfolder(choiceFile);
                break;

            case "move":
                if(input.length == 1){
                    error(input[0].toLowerCase());
                } else
                FileHandler.move(choiceFile, destination);
                break;

            case "copy":
                if(input.length == 1){
                    error(input[0].toLowerCase());
                } else
                FileHandler.copy(choiceFile, destination);
                break;

            case "delete":
                if(input.length == 1){
                    error(input[0].toLowerCase());
                } else
                FileHandler.delete(choiceFile);
                break;

            case "list":
                FileHandler.list(choiceFile);
                break;

            case "quit":
                System.out.println("Goodbye!");
                break;

            default:
                System.out.println("Illegal syntax or input.");
                break;
        }
    }

    public static void error(String type){
        System.out.println("No file or folder specified; cannot perform operation '"+ type +"'.");
    }

}
