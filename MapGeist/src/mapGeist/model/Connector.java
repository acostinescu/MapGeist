package mapGeist.model;

import java.sql.*;

import com.mysql.cj.jdbc.Driver;

//Static access to the connection method since you don't want to instantiate an object
//of the jdbc_controller class, you just want to receive the established connection object
//which is created via the getConnection method.
public class Connector {
    public static String URL = "jdbc:mysql://localhost:8889/MapGeist";
    public static String USER = "root";
    public static String PASS = "root";
    
    public static Connection getConnection() {
    	try {
    		DriverManager.registerDriver(new Driver());
    		return DriverManager.getConnection(URL, USER, PASS);
    	}
    	catch (SQLException ex) {
    		throw new RuntimeException("Error connecting to the database", ex);
    	}
    }
    
    private static Connection conn = null;
    
    //Example of how to Query a database from https://alvinalexander.com/java/edu/pj/jdbc/jdbc0003
    public void queryDatabase(String query) throws SQLException {
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        while ( rs.next() ) {
           String lastName = rs.getString("Lname");
           System.out.println(lastName);
       }
       conn.close();
    }
    
    //Example of how to update the database from https://www.boraji.com/jdbc-update-record-example
    public void updateDatabase(String updateQuery) throws SQLException {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(updateQuery);
            System.out.println("Database updated successfully ");
    }
    
    
}