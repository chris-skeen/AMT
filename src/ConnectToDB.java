import java.sql.*;

public class ConnectToDB {

    public static Connection connect() {

//      Safely connecting to the database.
        Connection connection;
        try {
            connection = DriverManager.getConnection("jdbc:postgresql:postgres", "postgres", "MesPvs@71"  );
             return connection;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        }

    }
