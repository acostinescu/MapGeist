package mapGeist.controller;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;

import mapGeist.model.*;

import org.json.JSONArray;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController 
public class EventController
{
	
	/**
	 * Add a new event
	 */
	@RequestMapping(value = "/Event/new", method = { RequestMethod.POST })
	@ResponseStatus(value = HttpStatus.OK)
	public void newEvent(@RequestParam(value="Title", required=false) Optional<String> Title, 
									  @RequestParam(value="Description", required=false) Optional<String> Description,
									  @RequestParam(value="Location", required=false) Optional<String> Location,
									  @RequestParam(value="StartTime", required=false) Optional<String> StartTime,
									  @RequestParam(value="EndTime", required=false) Optional<String> EndTime,
									  @RequestParam(value="Longitude", required=false) Optional<String> Longitude,
									  @RequestParam(value="Latitude", required=false) Optional<String> Latitude,
									  @RequestParam(value="Email", required=false) Optional<String> Email) 
	{
		LocalDateTime startTime = LocalDateTime.parse(StartTime.get());
		Timestamp StartTimeStamp = Timestamp.valueOf(startTime);

		LocalDateTime endTime = LocalDateTime.parse(EndTime.get());		
		Timestamp EndTimeStamp = Timestamp.valueOf(endTime);

		Date todaysDate = new Date();
		double latitude = Double.parseDouble(Latitude.get());
		double longitude = Double.parseDouble(Longitude.get());
		
		Event NewEvent = new Event(Title.get(), Description.get(), StartTimeStamp, EndTimeStamp, Location.get(), longitude, latitude, Email.get(), todaysDate);
		EventDAO.insertEvent(NewEvent);
		return;
	}
	
	/**
	 * Get the list of active (approved/upcoming) Events
	 */
	@RequestMapping(value="/Event/active", method= {RequestMethod.GET}, produces = "application/json")
	@ResponseBody
	public ResponseEntity<String> getAllActiveEvents()
	{
		
		List<Event> activeEvents = EventDAO.getAllActiveEvents();
		if(activeEvents != null)
		{
			JSONArray response = Event.eventListToJson(activeEvents);
			return new ResponseEntity<String>(response.toString(), HttpStatus.OK);
		}
		else
		{
			return new ResponseEntity<String>("{}", HttpStatus.OK); 
		}
	}
	
	/**
	 *  Get the list of queued Events for the logged in Moderator.
	 */
	@RequestMapping(value="/Event/queued", method= {RequestMethod.GET}, produces = "application/json")
	@ResponseBody
	public ResponseEntity<String> getQueuedEvents(HttpServletRequest request)
	{
		Moderator mod = (Moderator)request.getSession().getAttribute("loggedInUser");
		if(mod != null) {
			List<Event> queuedEvents = EventQueue.loadModeratorQueue(mod);
			if(queuedEvents != null) {
				JSONArray response = Event.eventListToJson(queuedEvents);
				return new ResponseEntity<String>(response.toString(), HttpStatus.OK);
			}
			
		}
		
		return new ResponseEntity<String>("{}", HttpStatus.OK);
	}
	
	/**
	 * Review (approve/deny) an Event
	 */
	@RequestMapping(value="/Event/review")
	public void reviewEvent(
			@RequestParam(value="id") String id, 
			@RequestParam(value="approved") boolean approved, 
			HttpServletRequest request)
	{
		Moderator mod = (Moderator)request.getSession().getAttribute("loggedInUser");
		if(mod != null)
		{
			Event toReview = EventDAO.getEvent(id);
			
			// Make sure that the event exists
			if(toReview != null)
			{
				if(approved)
				{
					toReview.approve(mod);
				}
				else
				{
					toReview.deny(mod);
				}

				EventDAO.updateEvent(toReview);
			}
		}
	}
}