package mapGeist.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONObject;

import mapGeist.Maptilities;

import java.sql.Timestamp;
import java.text.DateFormat;  
import java.text.SimpleDateFormat;  
import java.util.Calendar;

public class Event
{
	private String id;
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
    private String reviewedBy;
    
    public Event(String title, String description, Timestamp startTime, Timestamp endTime, String location, double longitude, double latitude, String emailAddr, Date dateSubmitted)
    {
        
        this.id = UUID.randomUUID().toString();
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
    
    public Event(String id, String title, String description, Timestamp startTime, Timestamp endTime, String location, double longitude, double latitude, String emailAddr, Date dateSubmitted, boolean queued, Boolean approved, Date dateReviewed, String reviewedBy)
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
    
    public String getID()
    {
        return this.id;
    }
    public void setID(String id)
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
    public String getReviewedBy()
    {
    	return this.reviewedBy;
    }
    
    public void addToModeratorQueue(String moderatorID)
    {
        this.queued = true;
        this.reviewedBy = moderatorID;
    }
    public void approve(Moderator mod)
    {
        this.approved = true;
        this.reviewedBy = mod.getID();
        this.dateReviewed = new Date();
    }
    public void deny(Moderator mod)
    {
        this.approved = false;
        this.reviewedBy = mod.getID();
        this.dateReviewed = new Date();
    }
    
    public static JSONArray eventListToJson(List<Event> eventList)
    {
    	JSONArray response = new JSONArray();
    	String pattern = "MM/dd/yyyy HH:mm:ss";
    	
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
			eventMap.put("starttime", Maptilities.formatDateString(e.getStartTime()));
			eventMap.put("approved", e.isApproved());
			eventMap.put("queued", e.isQueued());
			
			
			if(e.getEndTime() != null)
			{
				eventMap.put("endtime", Maptilities.formatDateString(e.getEndTime()));
			}
			else
			{
				eventMap.put("endtime", "null");
			}
			
			if(e.getDateReviewed() != null) 
			{
				eventMap.put("date_reviewed", df.format(e.getDateReviewed()));
			}
			else
			{
				eventMap.put("date_reviewed", "null");
			}
			
			eventMap.put("date_submitted", df.format(e.getDateSubmitted()));
			
			response.put(eventMap);
		}
    	return response;
    }
    
    public static JSONArray getEventsJson()
	{
		List<Event> eventList = EventDAO.getAllEvents();		
		return eventListToJson(eventList);
	}
    
    public static JSONArray getActiveMapEventsJson()
	{
		List<Event> eventList = EventDAO.getAllActiveEvents();
		return eventListToJson(eventList);
	}
}
