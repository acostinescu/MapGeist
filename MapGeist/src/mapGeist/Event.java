package mapGeist;

import java.util.Date;
import java.util.UUID;

public class Event
{
	private UUID id;
    private String title;
    private String description;
    
    private Date startTime;
    private Date endTime; 
    
    private String location;
    private float longitude ;
    private float latitude;
    private String emailAddress;
    
    private Date dateSubmitted;
    
    private Boolean queued = false; 
    
    private Boolean approved = null;
    private Date reviewDate;
    private UUID reviewedBy;
    
    public Event(String title, String description, Date startTime, Date endTime, String location, float longitude, float latitude, String emailAddr, Date dateSubmitted)
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
    }
    
    public UUID getID()
    {
        return this.id;
    }
    public String getTitle()
    {
        return this.title;
    }
    public String getDescription()
    {
        return this.description;
    }
    public Date getStartTime()
    {
        return this.startTime;
    }
    public Date getEndTime()
    {
        return this.endTime;
    }
    public String getLocation()
    {
        return this.location;
    }
    public float getLongitude()
    {
        return this.longitude;
    }    
    public float getLatitude()
    {
        return this.latitude;
    }
    public String getEmailAddress()
    {
        return this.emailAddress;
    }
    public Date getDateSubmitted()
    {
        return this.dateSubmitted;
    }
    
    public void queue(UUID moderatorID)
    {
        this.queued = true;
        this.reviewedBy = moderatorID;
    }
    public void approve()
    {
        this.approved = true;
        this.reviewDate = new Date();
    }
    
    public void deny()
    {
        this.approved = false;
        this.reviewDate = new Date();
    }
}
