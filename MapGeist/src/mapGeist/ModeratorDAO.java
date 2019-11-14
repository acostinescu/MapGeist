package mapGeist;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ModeratorDAO
{
	public Moderator getModerator(UUID id)
	{
		Connection conn = Connector.getConnection();
		try
		{
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Moderator WHERE id=" + id.toString());
			if(rs.next())
			{
				return extractModerator(rs);
			}
		} catch(SQLException ex) {
			ex.printStackTrace();
		}
		return null;
	}
	public Moderator getModerator(String username)
	{
		Connection conn = Connector.getConnection();
		try
		{
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Moderator WHERE username=" + username);
			if(rs.next())
			{
				return extractModerator(rs);
			}
		} catch(SQLException ex) {
			ex.printStackTrace();
		}
		return null;
	}
	
	public List<Moderator> getAllModerators()
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
	
	public Moderator attemptLogin(String username, String password)
	{
		Moderator logMeIn = getModerator(username);
		if(logMeIn != null)
		{
			String encryptedPassword = Maptilities.encryptPassword(password, logMeIn.getSalt());
			if(encryptedPassword == logMeIn.getPassword())
			{
				return logMeIn;
			}
		}
		return null;
	}
	
	public boolean deleteModerator(UUID id)
	{
		Connection conn = Connector.getConnection();
		
		try {
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
	
	public boolean insertModerator(Moderator mod)
	{
		Connection conn = Connector.getConnection();
		
		try {
	        PreparedStatement ps = conn.prepareStatement("INSERT INTO Moderator VALUES (?, ?, ?, ?, ?)");
	        ps.setString(1, mod.getID().toString());
	        ps.setString(2, mod.getUsername());
	        ps.setString(3, mod.getPassword());
	        ps.setString(4, mod.getFirstName());
	        ps.setString(5, mod.getLastName());
	        
	        System.out.println("\n\n\n\n" + mod.getLastName() + " \n" + ps.toString());
	        
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
	
	public boolean updateModerator(Moderator mod)
	{
		Connection conn = Connector.getConnection();
		
		try {
	        PreparedStatement ps = conn.prepareStatement("UPDATE user SET username=?, password=?, firstName=?, lastName=? WHERE id=?");
	        ps.setString(1, mod.getUsername());
	        ps.setString(2, mod.getPassword());
	        ps.setString(3, mod.getFirstName());
	        ps.setString(4, mod.getLastName());
	        
	        ps.setString(5, mod.getID().toString());
	        
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
	
	private Moderator extractModerator(ResultSet rs) throws SQLException
	{
		Moderator mod = new Moderator();
		
		mod.setID(rs.getString("id"));
		mod.setUsername(rs.getString("username"));
		mod.setPassword(rs.getString("password"));
		mod.setFirstName(rs.getString("firstName"));
		mod.setLastName(rs.getString("lastName"));
		
		return mod;
	}
}
