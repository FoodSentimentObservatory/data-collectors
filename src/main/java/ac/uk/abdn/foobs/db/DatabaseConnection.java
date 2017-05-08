package ac.uk.abdn.foobs.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import ac.uk.abdn.foobs.Establishment;

public class DatabaseConnection {
   private String connectionUrl;

   public DatabaseConnection(String url) {
      this.connectionUrl = url;
   }

   public void insertEstbalishments(List<Establishment> establishments) {
	  // It would be better to do this as batch, but we need the returned keys
      for (Establishment establishment : establishments) {
    	  insertEstablishment(establishment);
      }
   }

   public void insertEstablishment(Establishment establishment) {
      Connection connection = null;
      PreparedStatement preparedStatementAgent = null;
      PreparedStatement preparedStatementLocation = null;
      PreparedStatement preparedStatementGeo = null;
      PreparedStatement preparedStatementAddress = null;
      PreparedStatement preparedStatementPremises = null;
      PreparedStatement preparedStatementUserAccount = null;
      PreparedStatement preparedStatementUserAccountPremisesMapping = null;
      String insertAgent = "INSERT INTO [dbo].[Agent] " +
                              "([agentType]) " +
                           "VALUES " +
                              "('premises')";
      String insertLocation = "INSERT INTO [dbo].[Location] " +
                                 "([locationType], [displayString]) " +
                              "VALUES " +
                                 "(NULL, ?)";
      String insertGeo = "INSERT INTO [dbo].[GeoPoint] " +
                            "([locationPoint], [locationId]) " +
                         "VALUES " +
                            "(?, ?)";
      String insertAddress = "INSERT INTO [dbo].[Address] " +
                                "([line1], [line2], [postCode], [county], [country], [locationId], [city]) " +
                             "VALUES " +
                                "(?, NULL, ?, NULL, ?, ?, ?)";
      String insertPremises = "INSERT INTO [dbo].[Premises] " +
                                 "([Id], [businessName], [belongToAgent], [locationId], [businessType]) " +
                              "VALUES " +
                                 "(?, ?, ?, ?, ?)";
      String insertUserAccount = "INSERT INTO [dbo].[Premises] " +
                                    "([platformAccountId], [accountURL], [displayName], [accountCreatedAt], [profileDescription], [verified], [platformId], [agentId], [lastCheckedDate]) " +
                                 "VALUES " +
                                    "(?, ?, ?, ?, ?, ?, ?, ?, ?)";
      String insertUserAccountPremisesMapping = "INSERT INTO [dbo].[UserAccountPremisesMapping] " +
    		  										"([UserAccount_Id], [Premises_Id]) " +
    		  									"VALUES " +
    		  										"(?, ?)";

      try {
    	 // this should be done as a transaction, but we need the returned keys
         connection = getConnection();

         preparedStatementAgent = connection.prepareStatement(insertAgent, Statement.RETURN_GENERATED_KEYS);
         preparedStatementLocation = connection.prepareStatement(insertLocation, Statement.RETURN_GENERATED_KEYS);
         preparedStatementGeo = connection.prepareStatement(insertGeo);
         preparedStatementAddress = connection.prepareStatement(insertAddress);
         preparedStatementPremises = connection.prepareStatement(insertPremises);
         preparedStatementUserAccount = connection.prepareStatement(insertUserAccount);
         preparedStatementUserAccountPremisesMapping = connection.prepareStatement(insertUserAccountPremisesMapping);

         int agentId = 0;
         int locationId = 0;

         preparedStatementAgent.executeUpdate();

         try (ResultSet generatedKeys = preparedStatementAgent.getGeneratedKeys()) {
            if (generatedKeys.next()) {
               agentId = generatedKeys.getInt(1);
            } else {
               throw new SQLException();
            }
         }

         preparedStatementLocation.setString(1, establishment.getAddress() + ", " + establishment.getPostCode() + ", " +establishment.getCity());
         preparedStatementLocation.executeUpdate();

         try (ResultSet generatedKeys = preparedStatementLocation.getGeneratedKeys()) {
            if (generatedKeys.next()) {
               locationId = generatedKeys.getInt(1);
            } else {
               throw new SQLException();
            }
         }
         
         preparedStatementGeo.setString(1, "geography::STGeomFromText('POINT(" + establishment.getLocation().getLatitude() + ", "
        		 							+ establishment.getLocation().getLongitude() + ")', 4326)");
         preparedStatementGeo.setInt(2, locationId);
         preparedStatementGeo.executeUpdate();
         
         preparedStatementAddress.setString(1, establishment.getAddress());
         preparedStatementAddress.setString(2, establishment.getPostCode());
         preparedStatementAddress.setString(3, "Scotland"); // TODO
         preparedStatementAddress.setInt(4, locationId);
         preparedStatementAddress.setString(5, establishment.getCity());
         preparedStatementAddress.executeUpdate();
         
         preparedStatementPremises.setLong(1, establishment.getFHRSID());
         preparedStatementPremises.setString(2, establishment.getBusinessName());
         preparedStatementPremises.setInt(3, agentId);
         preparedStatementPremises.setInt(4, locationId);
         preparedStatementPremises.setString(5, establishment.getBusinessType());
         preparedStatementPremises.executeUpdate();
         
         if (establishment.getTwitterHandle() == null) {
        	 
         } else if (establishment.getTwitterHandle().equals("NONE")) {
        	 
         } else {
        	 
         }
         
      } catch (SQLException e) {
         try {
            connection.rollback();
         } catch (SQLException err) {}
         e.printStackTrace();
      } finally {
         if (preparedStatementAgent != null) {
            try {
               preparedStatementAgent.close();
            } catch (SQLException e) {}
         }

         if (preparedStatementUserAccount != null) {
            try {
               preparedStatementUserAccount.close();
            } catch (SQLException e) {}
         }

         if (preparedStatementUserAccountPremisesMapping != null) {
            try {
               preparedStatementUserAccountPremisesMapping.close();
            } catch (SQLException e) {}
         }

         if (preparedStatementAddress != null) {
            try {
               preparedStatementAddress.close();
            } catch (SQLException e) {}
         }

         if (preparedStatementPremises != null) {
            try {
               preparedStatementPremises.close();
            } catch (SQLException e) {}
         }

         if (preparedStatementGeo != null) {
            try {
               preparedStatementGeo.close();
            } catch (SQLException e) {}
         }

         if (preparedStatementLocation != null) {
            try {
               preparedStatementLocation.close();
            } catch (SQLException e) {}
         }

         if (connection != null) {
            try {
               connection.close();
            } catch (SQLException e) {}
         }
      }
   }
   
   private Connection getConnection() {
      Connection connection = null;
      try {
         Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
      } catch (ClassNotFoundException e) {
         System.out.println("MsSQL driver not found!");
         e.printStackTrace();
         System.exit(1);
      }

      try {
         connection = DriverManager.getConnection(connectionUrl);
      } catch (SQLException e) {
         e.printStackTrace();
      }

      return connection;
   }

}
