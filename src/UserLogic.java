import java.sql.*;
import java.util.Scanner;

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
            System.out.println("Account was not created!");}
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

//

}

