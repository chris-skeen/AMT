import java.sql.*;
import java.util.Scanner;

public class UserLogic {

    static ConnectToDB connectToDB = new ConnectToDB();
    public static void signup() {

    }



    public static String LogIn() throws SQLException {
        // Create connection, statement and scanner.
        Scanner scanner = new Scanner(System.in);
        Connection tryConnect = connectToDB.connect();
        CreateStatement createStatement = new CreateStatement();
        Statement st = createStatement.ReturnStatement(tryConnect);
        String username = "";

        try {
            //      Check if the user is in the system.
            System.out.println("Please State Your Username:");
            username = scanner.nextLine();
            PreparedStatement preparedStatement = tryConnect.prepareStatement("SELECT * FROM Users WHERE username = ?");
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();

            System.out.println("Please State Your Password:");
            String password = scanner.nextLine();

            PreparedStatement preparedStatement2 = tryConnect.prepareStatement("SELECT * FROM Users WHERE username = ? AND userpassword = ?");
            preparedStatement2.setString(1, username);
            preparedStatement2.setString(2, password);
            resultSet = preparedStatement2.executeQuery();
        } catch (Exception e) {
            System.out.println("This username or password is incorrect!");
        }

        return username;

    }
}
