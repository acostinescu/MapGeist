package mapGeist.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.text.DateFormat;  
import java.text.SimpleDateFormat;  
import java.util.Calendar;

public class Event
{
	private UUID id;
    private String title;
    private String description;
    private Timestamp startTime;
    private Timestamp endTime; 
    private String location;
    private double longitude;
    private double latitude;
    private String emailAddress;
    private Date dateSubmitted;
    private Boolean queued; 
    private Boolean approved;
    private Date dateReviewed;
    private UUID reviewedBy;
    
    public Event(String title, String description, Timestamp startTime, Timestamp endTime, String location, double longitude, double latitude, String emailAddr, Date dateSubmitted)
    {
        
        this.id = UUID.randomUUID();
        this.title = title;
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
        this.location = location;
        this.longitude = longitude;
        this.latitude = latitude;
        this.emailAddress = emailAddr;
        this.dateSubmitted = dateSubmitted;
        this.queued = false;
        this.approved = null;
        this.dateReviewed = null;
        this.reviewedBy = null;
    }
    
    public Event(UUID id, String title, String description, Timestamp startTime, Timestamp endTime, String location, double longitude, double latitude, String emailAddr, Date dateSubmitted, boolean queued, Boolean approved, Date dateReviewed, UUID reviewedBy)
    {
        
        this.id = id;
        this.title = title;
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
        this.location = location;
        this.longitude = longitude;
        this.latitude = latitude;
        this.emailAddress = emailAddr;
        this.dateSubmitted = dateSubmitted;
        this.queued = queued;
        this.approved = approved;
        this.dateReviewed = dateReviewed;
        this.reviewedBy = reviewedBy;
    }
    
    public UUID getID()
    {
        return this.id;
    }
    public void setID(String id)
    {
    	this.id = UUID.fromString(id);
    }
    public void setID(UUID id)
    {
    	this.id = id;
    }
    public String getTitle()
    {
        return this.title;
    }
    public void setTitle(String title)
    {
    	this.title = title;
    }
    public String getDescription()
    {
        return this.description;
    }
    public void setDescription(String description)
    {
    	this.description = description;
    }
    public Date getStartTime()
    {
        return this.startTime;
    }
    public void setStartTime(Timestamp startTime)
    {
    	this.startTime = startTime;
    }
    public Date getEndTime()
    {
        return this.endTime;
    }
    public void setEndTime(Timestamp endTime)
    {
    	this.endTime = endTime;
    }
    public String getLocation()
    {
        return this.location;
    }
    public void setLocation(String location)
    {
    	this.location = location;
    }
    public double getLongitude()
    {
        return this.longitude;
    }    
    public void getLongitude(double longitude)
    {
    	this.longitude = longitude;
    }
    public double getLatitude()
    {
        return this.latitude;
    }
    public void setLatitude(double latitude)
    {
    	this.latitude = latitude;
    }
    public String getEmailAddress()
    {
        return this.emailAddress;
    }
    public void setEmailAddress(String emailAddress)
    {
    	this.emailAddress = emailAddress;
    }
    public Date getDateSubmitted()
    {
        return this.dateSubmitted;
    }
    public void setDateSubmitted(Date dateSubmitted)
    {
    	this.dateSubmitted = dateSubmitted;
    }
    public boolean isQueued()
    {
    	return this.queued;
    }
    public Boolean isApproved()
    {
    	return this.approved;
    }
    public Date getDateReviewed()
    {
    	return this.dateReviewed;
    }
    public UUID getReviewedBy()
    {
    	return this.reviewedBy;
    }
    
    public void addToModeratorQueue(UUID moderatorID)
    {
        this.queued = true;
        this.reviewedBy = moderatorID;
    }
    public void approve()
    {
        this.approved = true;
        this.dateReviewed = new Date();
    }
    public void deny()
    {
        this.approved = false;
        this.dateReviewed = new Date();
    }
    public static JSONArray getEventsJson()
	{
		List<Event> eventList = EventDAO.getAllEvents();
		String pattern = "MM/dd/yyyy HH:mm:ss";
		
		JSONArray response = new JSONArray();
		
		for(Event e : eventList)
		{
			JSONObject eventMap = new JSONObject();
			
			DateFormat df = new SimpleDateFormat(pattern);
			
			
			eventMap.put("id", e.getID().toString());
			eventMap.put("title", e.getTitle());
			eventMap.put("description", e.getDescription());
			eventMap.put("location", e.getLocation());
			eventMap.put("latitude", Double.toString(e.getLatitude()));
			eventMap.put("longitude", Double.toString(e.getLongitude()));
			eventMap.put("email", e.getEmailAddress());
			if(e.getDateReviewed() == null) 
			{
				eventMap.put("date_reviewed", "null");
			}
			else
			{
				eventMap.put("date_reviewed", df.format(e.getDateReviewed()));
			}			
			eventMap.put("date_submitted", df.format(e.getDateSubmitted()));
			
			response.put(eventMap);
		}
		return response;
	}
}
