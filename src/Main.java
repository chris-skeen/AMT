import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("-----------------------------");
        System.out.println("Welcome to your ATM!");
        System.out.println("-----------------------------");

        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.println("3. Exit");
        System.out.println("-----------------------------");

        String starterOption = scanner.nextLine();

        if (starterOption.equals("1")) {
            UserLogic userLogic = new UserLogic();
            userLogic.LogIn();
        }



    }
}