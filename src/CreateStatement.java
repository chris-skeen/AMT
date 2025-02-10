import java.sql.*;

public class CreateStatement {
//    Try to create a statement
    public static Statement ReturnStatement(Connection tryConnect) {
        boolean statementWorked = false;
        try {
            Statement st = tryConnect.createStatement();
            return st;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
