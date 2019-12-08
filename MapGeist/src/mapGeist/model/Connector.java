package mapGeist.model;

import java.sql.*;

import com.mysql.cj.jdbc.Driver;

public class Connector {
    public static String URL = "jdbc:mysql://localhost:8889/MapGeist";
    public static String USER = "root";
    public static String PASS = "root";
    
    /**
     * Establish a Connection to the database
     * @return the Connection
     */
    public static Connection getConnection()
    {
    	try {
    		DriverManager.registerDriver(new Driver());
    		return DriverManager.getConnection(URL, USER, PASS);
    	}
    	catch (SQLException ex) {
    		throw new RuntimeException("Error connecting to the database", ex);
    	}
    }
}