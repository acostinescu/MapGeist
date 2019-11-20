package mapGeist.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.Date;

public class EventDAO
{
	public static Event getEvent(UUID id)
	{
		Connection conn = Connector.getConnection();
		
		try
		{
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Event WHERE id=" + id.toString());
			if(rs.next())
			{
				return extractEvent(rs);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return null;
	}
	
	public static List<Event> getAllEvents()
	{
		Connection conn = Connector.getConnection();
		try
		{
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Event");
			
			List<Event> eventList = new ArrayList<Event>();
			
			while(rs.next())
			{
				eventList.add(extractEvent(rs));
			}
			
			if(eventList.size() > 0)
			{
				return eventList;
			}
		} catch(SQLException ex) {
			ex.printStackTrace();
		}
		return null;
	}
	
	public static List<Event> getAllActiveEvents()
	{
		Connection conn = Connector.getConnection();
		try
		{
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Event WHERE startTime < NOW() AND endTime > NOW()");
			
			List<Event> eventList = new ArrayList<Event>();
			
			while(rs.next())
			{
				eventList.add(extractEvent(rs));
			}
			
			if(eventList.size() > 0)
			{
				return eventList;
			}
		} catch(SQLException ex) {
			ex.printStackTrace();
		}
		return null;
	}
	
	public static boolean deleteEvent(UUID id)
	{
		Connection conn = Connector.getConnection();
		
		try
		{
			Statement stmt = conn.createStatement();
			int success = stmt.executeUpdate("DELETE FROM Event WHERE id=" + id.toString());
			if(success == 1)
			{
				return true;
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return false;
	}
	
    public static boolean insertEvent(Event eve)
    {
    	Connection conn = Connector.getConnection();

        try
        {
            String query = "INSERT INTO Event (id, title, description, startTime, "
            		+ "endTime, location, longitude, latitude, emailAddress, dateSubmitted)" 
            		+ " values(?,?,?,?,?,?,?,?,?,?)";
            
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, eve.getID().toString());
            stmt.setString(2, eve.getTitle());
            stmt.setString(3, eve.getDescription());
            stmt.setTimestamp(4, new java.sql.Timestamp(eve.getStartTime().getTime()));
            stmt.setTimestamp(5, new java.sql.Timestamp(eve.getEndTime().getTime()));
            stmt.setString(6, eve.getLocation());
            stmt.setDouble(7, eve.getLongitude());
            stmt.setDouble(8, eve.getLatitude());
            stmt.setString(9, eve.getEmailAddress());
            stmt.setDate(10, new java.sql.Date(eve.getDateSubmitted().getTime()));
           
            
            int success = stmt.executeUpdate();
            if(success == 1)
            {
            	return true;
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return false;
    }
    
    public static boolean updateEvent(Event eve)
    {
    	Connection conn = Connector.getConnection();
    	try {
    		PreparedStatement stmt = conn.prepareStatement("UPDATE Event SET title=?, description=?, startTime=?, endTime=?, location=?, longitude=?, latitude=?, emailAddress=?, dateSubmitted=?, queued=?, approved=?, dateReviewed=?, reviewedBy=? WHERE id=?");
            stmt.setString(1, eve.getTitle());
            stmt.setString(2, eve.getDescription());
            stmt.setDate(3, new java.sql.Date(eve.getStartTime().getTime()));
            stmt.setDate(4, new java.sql.Date(eve.getEndTime().getTime()));
            stmt.setString(5, eve.getLocation());
            stmt.setDouble(6, eve.getLongitude());
            stmt.setDouble(7, eve.getLatitude());
            stmt.setString(8, eve.getEmailAddress());
            stmt.setDate(9, new java.sql.Date(eve.getDateSubmitted().getTime()));
            stmt.setBoolean(10, eve.isQueued());
            stmt.setBoolean(11, eve.isApproved());
            stmt.setDate(12, new java.sql.Date(eve.getDateReviewed().getTime()));
            stmt.setString(13, eve.getReviewedBy().toString());
            stmt.setString(14, eve.getID().toString());
            
            int success= stmt.executeUpdate();
            if(success == 1)
            {
            	return true;
            }
    		
    	} catch (SQLException ex) {
    		ex.printStackTrace();
    	}
    	return false;
    }
    
    private static Event extractEvent(ResultSet rs) throws SQLException
    {
    	String idString = rs.getString("id");
    	UUID id = UUID.fromString(idString);
    	
    	String title = rs.getString("title");
    	String description = rs.getString("description");
    	java.sql.Timestamp startTime = rs.getTimestamp("startTime");
    	java.sql.Timestamp endTime = rs.getTimestamp("endTime");
    	String location = rs.getString("location");
    	double longitude = rs.getDouble("longitude");
    	double latitude = rs.getDouble("latitude");
    	String emailAddress = rs.getString("emailAddress");
    	java.util.Date dateSubmitted = rs.getDate("dateSubmitted");
    	boolean queued = rs.getBoolean("queued");
    	
    	Boolean approved = rs.getBoolean("approved");
    	if(rs.wasNull()) approved = null;
    	
    	java.util.Date dateReviewed = rs.getDate("dateReviewed");
    	
    	String reviewedByString = rs.getString("reviewedBy");
    	UUID reviewedBy = null;
    	if(!rs.wasNull()) reviewedBy = UUID.fromString(reviewedByString);
    	
    	Event eve = new Event(id, title, description, startTime, endTime, location, longitude, latitude, emailAddress, dateSubmitted, queued, approved, dateReviewed, reviewedBy);
    	
    	eve.setID(rs.getString("id"));

    	return eve;
    }
}
