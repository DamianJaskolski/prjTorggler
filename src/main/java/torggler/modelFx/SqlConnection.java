package torggler.modelFx;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SqlConnection {

    public static Connection Conector() {


        try {
            DriverManager.registerDriver (new org.postgresql.Driver ( ));
            Connection conn = DriverManager.getConnection ("jdbc:postgresql://localhost:5432/dbTorggler",
                    "postgres",
                    "dagdam");
            return conn;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;

    }
}
