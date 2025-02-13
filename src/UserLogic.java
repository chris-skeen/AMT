import java.math.BigDecimal;
import java.sql.*;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Scanner;
import java.time.LocalDateTime;

public class UserLogic {

    static ConnectToDB connectToDB = new ConnectToDB();

    //    LOG IN AREA ----------------------------------------------------------
    public static String LogIn() throws SQLException {
        // Create connection, statement and scanner.
        Scanner scanner = new Scanner(System.in);
        Connection tryConnect = connectToDB.connect();
        CreateStatement createStatement = new CreateStatement();
        Statement st = createStatement.ReturnStatement(tryConnect);
        String username = "";
        String realUsername = null;
        try {
            //      Check if the user is in the system.
            System.out.println("Please State Your Username:");
            username = scanner.nextLine();
            PreparedStatement preparedStatement = tryConnect.prepareStatement("SELECT * FROM Users WHERE username = ?");
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                realUsername = resultSet.getString("username");
            }


            System.out.println("Please State Your Password:");
            String password = scanner.nextLine();

            PreparedStatement preparedStatement2 = tryConnect.prepareStatement("SELECT * FROM Users WHERE username = ? AND userpassword = ?");
            preparedStatement2.setString(1, username);
            preparedStatement2.setString(2, password);
            resultSet = preparedStatement2.executeQuery();
            if (resultSet.next()) {
                realUsername = resultSet.getString("username");
            }
        } catch (Exception e) {
            System.out.println("This username or password is incorrect!");
        }

        return realUsername;

    }

    //    LOG IN AREA ----------------------------------------------------------

    //    SIGN UP AREA ----------------------------------------------------------

    public static String SignUp() throws SQLException {
        // Create connection and statement.

        Scanner scanner = new Scanner(System.in);
        Connection tryConnect = connectToDB.connect();
        CreateStatement createStatement = new CreateStatement();
        Statement st = createStatement.ReturnStatement(tryConnect);


        System.out.print("Enter new User Name: ");
        String userName = scanner.nextLine();
        System.out.print("Enter new User Password: ");
        String userPassword = scanner.nextLine();

        if (userExists(tryConnect, userName)) {
            System.out.println("User '" + userName + "' already exists. Please choose a different name.");
        } else {
            String query = "INSERT INTO Users (UserName, UserPassword) VALUES (?,?)";

            try {
                PreparedStatement stmt = tryConnect.prepareStatement(query);
                stmt.setString(1, userName);
                stmt.setString(2, userPassword);
                stmt.executeUpdate();
                System.out.println("User '" + userName + "' added successfully!");
                return userName;
            } catch (SQLException e) {
                System.out.println("Account was not created!");
            }
        }
        return null;
    }

    public static boolean userExists(Connection tryConnect, String userName) throws SQLException {
        String query = "SELECT 1 FROM users WHERE UserName = ?";

        boolean var5;
        try (PreparedStatement stmt = tryConnect.prepareStatement(query)) {
            stmt.setString(1, userName);

            try (ResultSet rs = stmt.executeQuery()) {
                var5 = rs.next();
            }
        }
        return var5;
    }
//            SIGN UP AREA ----------------------------------------------------------

    //            WITHDRAW AREA ---------------------------------------------------------
    public static void Withdrawal(String username) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("What amount will you be withdrawing?");
        BigDecimal withdrawAmt = scanner.nextBigDecimal();
        try {
//          Get user ID
            Connection tryConnect = connectToDB.connect();
            CreateStatement createStatement = new CreateStatement();
            PreparedStatement preparedStatement = tryConnect.prepareStatement("SELECT * FROM Users WHERE username = ?");
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();

//            Withdraw

            if (resultSet.next()) {
                Integer userID = resultSet.getInt("userID");

                PreparedStatement preparedStatement2 = tryConnect.prepareStatement("SELECT * FROM Atm WHERE UserID = ?");
                preparedStatement2.setInt(1, userID);
                ResultSet resultSet2 = preparedStatement2.executeQuery();
                if (resultSet2.next()) {
                    BigDecimal atmAmt = resultSet2.getBigDecimal("atmBalance");
                    if (atmAmt.compareTo(withdrawAmt) >= 0) {
                        BigDecimal finalNumber = atmAmt.subtract(withdrawAmt);

                        PreparedStatement preparedStatement3 = tryConnect.prepareStatement("UPDATE Atm SET AtmBalance = ? WHERE UserID = ?");
                        preparedStatement3.setBigDecimal(1, finalNumber);
                        preparedStatement3.setInt(2, userID);
                        preparedStatement3.executeUpdate();

                        PreparedStatement preparedStatement4 = tryConnect.prepareStatement("SELECT * FROM Atm WHERE UserID = ?");
                        preparedStatement4.setInt(1, userID);
                        ResultSet resultSet4 = preparedStatement4.executeQuery();
//                        Make new statement
                        String type = "Withdrawal";
                        PreparedStatement preparedStatement1 = tryConnect.prepareStatement("INSERT INTO Statements (Type, Amt, UserID, TimeDate) VALUES (?, ?, ?, ?)");
                        preparedStatement1.setString(1, type);
                        preparedStatement1.setBigDecimal(2, withdrawAmt);
                        preparedStatement1.setInt(3, userID);
                        preparedStatement1.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
                        preparedStatement1.executeUpdate();





                        if (resultSet4.next()) {
                        }

                    } else {
                        System.out.println("You do not have enough money!");
                    }
                }
            }


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void Deposit(String username) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("What amount will you be depositing?");
        BigDecimal depositAmt = scanner.nextBigDecimal();
        try {
            Connection tryConnect = connectToDB.connect();
            CreateStatement createStatement = new CreateStatement();
            PreparedStatement preparedStatement = tryConnect.prepareStatement("SELECT * FROM Users WHERE username = ?");
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
            }
            Integer userID = resultSet.getInt("userID");
            PreparedStatement preparedStatement2 = tryConnect.prepareStatement("SELECT * FROM Atm WHERE UserID = ?");
            preparedStatement2.setInt(1, userID);
            ResultSet resultSet2 = preparedStatement2.executeQuery();
            if (resultSet2.next()) {
            }
            BigDecimal atmAmt = resultSet2.getBigDecimal("atmBalance");
            BigDecimal finalNumber = atmAmt.add(depositAmt);

            PreparedStatement preparedStatement3 = tryConnect.prepareStatement("UPDATE Atm SET AtmBalance = ? WHERE UserID = ?");
            preparedStatement3.setBigDecimal(1, finalNumber);
            preparedStatement3.setInt(2, userID);
            preparedStatement3.executeUpdate();

            String type = "Deposit";
            PreparedStatement preparedStatement1 = tryConnect.prepareStatement("INSERT INTO Statements (Type, Amt, UserID, TimeDate) VALUES (?, ?, ?,?)");
            preparedStatement1.setString(1, type);
            preparedStatement1.setBigDecimal(2, depositAmt);
            preparedStatement1.setInt(3, userID);
            preparedStatement1.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
            preparedStatement1.executeUpdate();


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static void viewStatements(String username) {

        try {
            Connection tryConnect = connectToDB.connect();
            CreateStatement createStatement = new CreateStatement();

            PreparedStatement preparedStatement2 = tryConnect.prepareStatement("SELECT * FROM Users WHERE username = ?");
            preparedStatement2.setString(1, username);
            ResultSet resultSet2 = preparedStatement2.executeQuery();
            Integer userID = 0;
            if (resultSet2.next()) {
                userID = resultSet2.getInt("userID");
            }

            PreparedStatement preparedStatement = tryConnect.prepareStatement("SELECT * FROM Statements WHERE UserID = ?");
            preparedStatement.setInt(1, userID);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                var timedate = resultSet.getTimestamp("TimeDate");
                if (timedate != null) {
                    LocalDateTime localtimedate = timedate.toLocalDateTime();
                    System.out.println(String.format(resultSet.getString("Type") + ": $" + resultSet.getBigDecimal("Amt") + " on " + localtimedate.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM))));
                } else {
                    System.out.println(resultSet.getString("Type") + ": $" + resultSet.getBigDecimal("Amt"));
                }
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public static void updateAtmName(String username) {
        Connection tryConnect = connectToDB.connect();
        CreateStatement createStatement = new CreateStatement();

        Scanner scanner = new Scanner(System.in);
        System.out.println("New ATM name: ");
        String newAtmName = scanner.nextLine();

        try {
            PreparedStatement preparedStatement2 = tryConnect.prepareStatement("SELECT * FROM Users WHERE username = ?");
            preparedStatement2.setString(1, username);
            ResultSet resultSet2 = preparedStatement2.executeQuery();
            Integer userID = 0;
            if (resultSet2.next()) {
                userID = resultSet2.getInt("UserID");
            }

            PreparedStatement preparedStatement3 = tryConnect.prepareStatement("UPDATE Atm SET AtmName = ? WHERE UserID = ?");
            preparedStatement3.setString(1, newAtmName);
            preparedStatement3.setInt(2, userID);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

}

