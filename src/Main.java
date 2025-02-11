import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("-----------------------------");
        System.out.println("Welcome to your ATM!");
        System.out.println("-----------------------------");
        System.out.println("1. Register");
        System.out.println("2. Login");
        System.out.println("3. Exit");
        System.out.println("-----------------------------");

        String starterOption = scanner.nextLine();
        boolean loop = true;
        while (loop) {
            if (starterOption.equals("1")) {
        //          Sign Up
                UserLogic userLogic = new UserLogic();
                userLogic.signup();
            } else if (starterOption.equals("2")) {
        //          Log In
                UserLogic userLogic = new UserLogic();
                userLogic.LogIn();
            } else if (starterOption.equals("3")) {
                System.exit(0);
            } else {
                System.out.println("Invalid option");
            }
        }
    }
}