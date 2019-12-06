package mapGeist.model;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class EventQueue {
	public static synchronized List<Event> loadModeratorQueue(Moderator mod) {
		List<Event> eventList = new ArrayList<Event>();
		
		eventList = EventDAO.getQueuedModeratorEvents(mod.getID());
		
		if (eventList == null || eventList.size() < 5 ) {
			int eventsToGet = 5;
			if(eventList == null) {
				eventsToGet = 5;
				eventList = new ArrayList<Event>();
			}
			else {
				eventsToGet = 5-eventList.size();
			}
			List<Event> newEventList = EventDAO.getNewModeratorEventsForApproval(mod.getID(), eventsToGet);
			System.out.println(newEventList);
			eventList.addAll(newEventList);
		}
		
		return eventList;
	}
}
