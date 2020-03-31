package jonst;

import jonst.Models.User;

import java.io.Console;

/**
 * Hello world!
 */
public class App {

    public static Console console = System.console();

    public static void main(String[] args) {


        System.out.println("User File Storage\n");


        while (true) {       //Just your regular menu stuff

            String reply = Tools.getReply("Would you like to (L)og in, (R)egister, or (Q)uit? ");

            switch (reply.toLowerCase()) {
                case "log in":
                case "login":
                case "l":
                    login();
                    break;

                case "register":
                case "r":
                    register();
                    break;

                case "quit":
                case "q":
                    quit();
                    break;

                default:
                    System.out.println("I'm sorry, that's not a legitimate choice.");
                    break;
            }
        }

    }

    public static void register() {

        String userName;
        String password;

        while (true) {

            if (console != null) {                                //Console is not always available. If it is, we can use password shielding!
                userName = console.readLine("Name? ");
            } else {
                userName = Tools.getReply("Name? ");
            }

            if (User.checkForAvailability(userName)) {
                System.out.println("That name is already taken.");
            } else {
                break;
            }
        }

        if (console != null) {
            char[] pw = console.readPassword("Password? ");
            password = new String(pw);

        } else {
            password = Tools.getReply("Password? ");
        }
        User.register(userName, password);

        User.login(userName, password);     //Once you've registered, you automatically log in with your new creds.

    }

    public static void login() {

        String userName;
        String password;

        while(true) {
            if (console != null) {
                userName = console.readLine("Name? ");
            } else {
                userName = Tools.getReply("Name? ");
            }


            if (console != null) {
                char[] pw = console.readPassword("Password? ");
                password = new String(pw);

            } else {
                password = Tools.getReply("Password? ");
            }

            User.login(userName, password);
            break;
            //Note: Once you've done everything in your session, the thread returns to here and you can log in / register again.

        }
    }

    public static void quit(){
        System.out.println("Thank you for your patronage. Your files will be safe until the next session.");
        System.exit(0);
    }

}
