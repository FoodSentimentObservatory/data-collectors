package ac.uk.abdn.foobs.db;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {
   private static Connection connection = null;
   
   public static Connection getConnection(String connectionUrl) {
      try {
         Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
         connection = DriverManager.getConnection(connectionUrl);
      } catch (Exception e) {
         e.printStackTrace();
      }

      return connection;
   }
}
