package jonst.Models;

import jonst.Session;
import jonst.Tools;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

        /*
        User class stores user data. It also works as a DAO.

        Keep things private and within the class. Only allow the system to access user data
        (passwords especially) at secure checkpoints.

         */

public class User implements Serializable {
    private String userName;
    private String password;

    private static List<User> userList;

    private static File listFile = new File("src/main/Database/users.ser");

    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public static boolean checkForAvailability(String userName){        //Check if this name is taken.

        List<User> userList = getUserList();

        return userList.stream()
                .anyMatch(user -> user.userName.equalsIgnoreCase(userName));

    }


    private static List<User> getUserList(){

        List<User> userList = new ArrayList<>();

        if(!listFile.exists()) {
            return userList;
        }

        try(ObjectInputStream obj = new ObjectInputStream(new BufferedInputStream(new FileInputStream(listFile)))) {

            userList = (List<User>) obj.readObject();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return userList;
    }

    private static boolean addUserToList(User user){

        List<User> userList = getUserList();

        userList.add(user);

        boolean success = saveUserList(userList);

        return success;
    }

    private static boolean saveUserList(List<User> userList){

        try(ObjectOutputStream obj = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(listFile)))) {

            obj.writeObject(userList);

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }


        return true;
    }

    public static User register(String userName, String password){
        User newUser = new User(userName, password);

        boolean success = addUserToList(newUser);

        if(success){
            System.out.println("Registration successful.");
        } else {
            System.out.println("Registration failed.");
        }

        return newUser;
    }

    public static void login(String userName, String password){

        Optional<User> success = getUserList().stream()
                .filter(user -> user.userName.equalsIgnoreCase(userName) && user.password.equals(password))
                .findAny();



        if(success.isPresent()){
            System.out.println("Login successful.");
            Tools.pause();
            Session.runSession(success.get());


        } else {
            System.out.println("Login failed. Please try again.");
            Tools.pause();

        }

    }

        //Getter for userName. No setter (would mess up the system!).
        //ABSOLUTELY NO SETTER OR GETTER FOR PASSWORD!

    public String getUserName() {
        return userName;
    }

    public Path getUserFolder(){

        Path userFolder = Paths.get("src/main/UserFiles/" + userName);
        //Path userFolder = Paths.get(userName);

        if(!Files.exists(userFolder)){
            try {
                Files.createDirectory(userFolder);
            } catch (IOException e) {
                System.out.println("An error occured creating user folder: " + userFolder.toString());
                e.printStackTrace();
            }
        }
        return userFolder;

    }

}
