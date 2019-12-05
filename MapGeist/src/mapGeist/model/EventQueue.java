package mapGeist.model;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class EventQueue {
	public static synchronized List<Event> loadModeratorQueue(Moderator mod) {
		List<Event> eventList = new ArrayList<Event>();
		
		eventList = EventDAO.getQueuedModeratorEvents(mod.getID());
		
		if (eventList !=null && eventList.size() < 5 ) {
			List<Event> newEventList = EventDAO.getNewModeratorEventsForApproval(mod.getID(), 5-eventList.size());
			eventList.addAll(newEventList);
		}
		
		return eventList;
	}
}
