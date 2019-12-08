package mapGeist.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import mapGeist.Maptilities;

public class ModeratorDAO
{
	/**
	 * Get a Moderator with the given ID from the database.
	 * @param id the Moderator's ID.
	 * @return the retrieved Moderator, or null if none exist with the given ID.
	 */
	public static Moderator getModeratorByID(String id)
	{
		Connection conn = Connector.getConnection();
		try
		{
			PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Moderator WHERE id=?");
			stmt.setString(1, id.toString());
			
			ResultSet rs = stmt.executeQuery();
			if(rs.next())
			{
				return extractModerator(rs);
			}
		} catch(SQLException ex) {
			ex.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Get a Moderator with the given username from the database.
	 * @param username the Moderator's username.
	 * @return the retrieved Moderator, or null if none exist with the given username.
	 */
	public static Moderator getModeratorByUsername(String username)
	{
		Connection conn = Connector.getConnection();
		try
		{
			PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Moderator WHERE username=?");
			stmt.setString(1, username);
			ResultSet rs = stmt.executeQuery();
			if(rs.next())
			{
				return extractModerator(rs);
			}
		} catch(SQLException ex) {
			ex.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Get all Moderators in the database.
	 * @return the list of Moderators or null if there are none.
	 */
	public static List<Moderator> getAllModerators()
	{
		Connection conn = Connector.getConnection();
		try
		{
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Moderator");
			
			List<Moderator> modList = new ArrayList<Moderator>();
			
			while(rs.next())
			{
				modList.add(extractModerator(rs));
			}
			
			if(modList.size() > 0)
			{
				return modList;
			}
		} catch(SQLException ex) {
			ex.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Attempt to log in a Moderator with the given username and password.
	 * @param username the Moderator's username.
	 * @param password the Moderator's password.
	 * @return the Moderator if the username and password are correct, null otherwise
	 */
	public static Moderator attemptLogin(String username, String password)
	{
		Moderator logMeIn = getModeratorByUsername(username);
		if(logMeIn != null)
		{
			String encryptedPassword = Maptilities.encryptPassword(password, logMeIn.getSalt());
			
			if(encryptedPassword.equals(logMeIn.getPassword()))
			{
				return logMeIn;
			}
		}
		return null;
	}
	
	/**
	 * Delete a Moderator from the database with the given ID.
	 * @param id the ID of the Moderator to delete.
	 * @return true if the Moderated existed and was successfully deleted, false otherwise.
	 */
	public static boolean deleteModerator(String id)
	{
		Connection conn = Connector.getConnection();
		
		try
		{
	        Statement stmt = conn.createStatement();
	        int success = stmt.executeUpdate("DELETE FROM Moderator WHERE id=" + id.toString());
	        if(success == 1)
	        {
	        	return true;
	        }
	    } catch (SQLException ex) {
	        ex.printStackTrace();
	    }
	    return false;
	}
	
	/**
	 * Insert a Moderator into the database.
	 * @param mod the Moderator to insert.
	 * @return true if the Moderator was successfully inserted, false otherwise.
	 */
	public static boolean insertModerator(Moderator mod)
	{
		Connection conn = Connector.getConnection();
		
		try {
	        PreparedStatement ps = conn.prepareStatement("INSERT INTO Moderator VALUES (?, ?, ?, ?, ?)");
	        ps.setString(1, mod.getID().toString());
	        ps.setString(2, mod.getUsername());
	        ps.setString(3, mod.getPassword());
	        ps.setString(4, mod.getFirstName());
	        ps.setString(5, mod.getLastName());
	        
	        int success = ps.executeUpdate();
	        
	        if(success == 1)
	        {
	        	return true;
	        }
	    } catch (SQLException ex) {
	        ex.printStackTrace();
	    }
	    return false;
	}
	
	/**
	 * Update a Moderator in the database.
	 * @param mod the Moderator to update.
	 * @return true if the Moderator exists and was updated, false otherwise.
	 */
	public static boolean updateModerator(Moderator mod)
	{
		Connection conn = Connector.getConnection();
		
		try {
	        PreparedStatement stmt = conn.prepareStatement("UPDATE Moderator SET username=?, password=?, firstName=?, lastName=? WHERE id=?");
	        stmt.setString(1, mod.getUsername());
	        stmt.setString(2, mod.getPassword());
	        stmt.setString(3, mod.getFirstName());
	        stmt.setString(4, mod.getLastName());
	        
	        stmt.setString(5, mod.getID().toString());
	        
	        int success = stmt.executeUpdate();
	        if(success == 1)
	        {
	        	return true;
	        }
	    } catch (SQLException ex) {
	        ex.printStackTrace();
	    }
		return false;
	}
	
	/**
	 * Extract a Moderator from a ResutSet.
	 * @param rs the ResultSet to extract the Moderator from.
	 * @return the extracted Moderator.
	 * @throws SQLException
	 */
	private static Moderator extractModerator(ResultSet rs) throws SQLException
	{
		String id = rs.getString("id");
		String username = rs.getString("username");
		String password = rs.getString("password");
		String firstName = rs.getString("firstName");
		String lastName = rs.getString("lastName");

		return new Moderator(id, username, password, firstName, lastName);
	}
}
