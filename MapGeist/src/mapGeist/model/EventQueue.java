package mapGeist.model;

import java.util.ArrayList;
import java.util.List;

public class EventQueue
{
	private static final int EVENT_COUNT = 5;
	public static synchronized List<Event> loadModeratorQueue(Moderator mod)
	{
		// Get the moderator's already queued events
		List<Event> eventList = EventDAO.getQueuedModeratorEvents(mod.getID());

		if (eventList == null || eventList.size() < 5 )
		{
			int eventsToGet = EVENT_COUNT;
			
			if(eventList == null)
			{
				eventList = new ArrayList<Event>();
			}
			else
			{
				eventsToGet -= eventList.size();
			}
			
			List<Event> newEventList = EventDAO.getNewModeratorEventsForApproval(mod.getID(), eventsToGet);
			if(newEventList != null)
			{
				eventList.addAll(newEventList);
			}
		}
		
		return eventList;
	}
}
