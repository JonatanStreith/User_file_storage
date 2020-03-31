package jonst;

import java.nio.file.Path;
import java.util.Scanner;

public class Tools {

    private static Scanner inputReader = new Scanner(System.in);

    public static String getReply(String line) {
        System.out.print(line);
        return inputReader.nextLine();
    }

    public static void pause() {
        getReply("[Press return to continue]");
    }

    public static boolean testForSanity(Path path, Path userFolder){        //Make sure user doesn't "stray"...
        String testChoiceFile = path.normalize().toString();
        if(testChoiceFile.contains(userFolder.toString())){
            return true;
        }
        return false;
    }




}
