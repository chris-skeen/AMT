import java.sql.*;

public class ConnectToDB {

    public static Connection connect() {

//      Safely connecting to the database.
        Connection connection;
        try {
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/ATM", "postgres", "Markelg06"  );
             return connection;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        }

    }
