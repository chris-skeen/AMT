import java.sql.*;

public class Logic {

    static ConnectToDB connectToDB = new ConnectToDB();

    public static void signup(String[] args) {
        // Create connection and statement.

        Connection tryConnect = connectToDB.connect();
        CreateStatement createStatement = new CreateStatement();
        Statement st = createStatement.ReturnStatement(tryConnect);

    }
}
