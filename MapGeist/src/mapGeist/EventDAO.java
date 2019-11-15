package mapGeist;

import java.sql.*;

public class EventDAO
{
    public boolean insertEvent(Event Event)
    {
    	Connection conn = Connector.getConnection();

        try
        {
            String query = "INSERT INTO Event (id, title, description, startTime, endTime, location, longitude, latitude, emailAddress, dateSubmitted) values(?,?,?,?,?,?,?,?,?,?)";
            
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(1, Event.getID().toString());
            preparedStmt.setString(2, Event.getTitle());
            preparedStmt.setString(3, Event.getDescription());
            preparedStmt.setDate(4, new java.sql.Date(Event.getStartTime().getTime()));
            preparedStmt.setDate(5, new java.sql.Date(Event.getEndTime().getTime()));
            preparedStmt.setString(6, Event.getLocation());
            preparedStmt.setFloat(7, Event.getLongitude());
            preparedStmt.setFloat(8, Event.getLatitude());
            preparedStmt.setString(9, Event.getEmailAddress());
            preparedStmt.setDate(10, new java.sql.Date(Event.getDateSubmitted().getTime()));
            
            int success = preparedStmt.executeUpdate();
            
            if(success == 1)
            {
            	return true;
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return false;
    }
    
    private Event extractEvent(ResultSet rs)
    {
    	// TODO: Extract Event values from ResultSet
    	return null;
    }
}
