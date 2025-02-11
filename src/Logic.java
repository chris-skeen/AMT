import java.sql.*;
import java.util.Scanner;

public class Logic {

    static ConnectToDB connectToDB = new ConnectToDB();

    public static void signup(String[] args) throws SQLException {
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
            String query = "INSERT INTO users (UserName, UserPassword) VALUES (?,?)";

            try (PreparedStatement stmt = tryConnect.prepareStatement(query)) {
                stmt.setString(1, userName);
                stmt.setString(2, userPassword);
                stmt.executeUpdate();
                System.out.println("User '" + userName + "' added successfully!");
            }
        }
    }
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

public void main() {
}

