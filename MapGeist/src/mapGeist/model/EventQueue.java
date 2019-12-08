package mapGeist.model;

import java.util.ArrayList;
import java.util.List;

public class EventQueue
{
	private static final int EVENT_COUNT = 5;
	
	/**
	 * Get the list of Events in the Moderator's working event queue. If they have less than EVENT_COUNT events in their queue, refill the queue up to 5 events.
	 * @param mod the Moderator whose queue will be filled
	 * @return the list of Events in the Moderator's queue (including added events)
	 */
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
