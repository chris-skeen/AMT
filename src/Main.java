import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {
        ConnectToDB connectToDB = new ConnectToDB();

        Scanner scanner = new Scanner(System.in);

        System.out.println("-----------------------------");
        System.out.println("Welcome to your ATM!");
        System.out.println("-----------------------------");


        boolean loop = true;
        String username = null;


        while (loop) {
            System.out.println("-----------------------------");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.println("-----------------------------");
            String starterOption = scanner.nextLine();
            if (starterOption.equals("1")) {
                //          Sign Up
                UserLogic userLogic = new UserLogic();
                userLogic.SignUp();
            } else if (starterOption.equals("2")) {
                //          Log In
                UserLogic userLogic = new UserLogic();
                username = userLogic.LogIn();
                if (username != null) {
                    break;
                }
            } else if (starterOption.equals("3")) {
                System.exit(0);
            } else {
                System.out.println("Invalid option");
            }
        }

            if (!username.equals(null)) {
//              Check if user has an ATM
                Integer atmID = 0;
                String atmName = "";
                Integer atmBalance = 0;
                try {
                    Connection tryConnect = connectToDB.connect();
                    CreateStatement createStatement = new CreateStatement();

                    PreparedStatement preparedStatement = tryConnect.prepareStatement("SELECT * FROM Users WHERE UserName = ?");
                    preparedStatement.setString(1, username);
                    ResultSet resultSet = preparedStatement.executeQuery();

                    if (resultSet.next()) {
                        Integer userID = resultSet.getInt("UserID");

                        while (true) {
                                PreparedStatement preparedStatement2 = tryConnect.prepareStatement("SELECT * FROM Atm WHERE UserID = ?");
                                preparedStatement2.setInt(1, userID);
                                ResultSet resultSet2 = preparedStatement2.executeQuery();
                                if (resultSet2.next()) {
                                    atmID = resultSet2.getInt("AtmID");
                                    atmName = resultSet2.getString("AtmName");
                                    atmBalance = resultSet2.getInt("AtmBalance");
                                    System.out.println("Welcome to your Atm - (" + atmName + ") " + username);
                                    break;
                                }
                                System.out.println(atmID);
                                if (atmID == 0) {
                                    System.out.println("-----------------------------------------------");
                                    System.out.println("Our system says you do not have an ATM created");
                                    System.out.println("You will create one now");
                                    System.out.println("------------------------------------------------");
                                    System.out.println("Name of ATM: ");
                                    String createAtmName = scanner.nextLine();


                                    Integer initialPayment = 0;
                                    while (true){
                                        System.out.println("Initial Payment Amount (Must be over 500): ");
                                        initialPayment = scanner.nextInt();
                                        if (initialPayment < 500) {
                                            System.out.println("This amount will not be enough.");
                                        } else if (initialPayment >= 500) {
                                            System.out.println("Thank you. (Creating ATM)");
                                            break;
                                        } else {
                                            System.out.println("Invalid Input.");
                                        }

                                    }
//                                  Creating ATM
                                    PreparedStatement preparedStatement3 = tryConnect.prepareStatement("INSERT INTO ATM (AtmName, AtmBalance, UserID) VALUES (?, ?, ?)");
                                    preparedStatement3.setString(1, createAtmName);
                                    preparedStatement3.setInt(2, initialPayment);
                                    preparedStatement3.setInt(3, userID);
                                    preparedStatement3.executeUpdate();
                                    break;

                                }
                        }
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
//              Start Of Actual system.
        }
    }
}