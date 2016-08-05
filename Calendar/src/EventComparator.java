import java.util.Comparator;

/**
 * EventComparator class implements Compartor interface and for sorting events
 * @author Three Amigos
 */

public class EventComparator implements Comparator<Event> {
	@Override
	public int compare(Event e1, Event e2) {
		int year = e1.getYear() - e2.getYear();
		int month = e1.getMonth() - e2.getMonth();
		int day = e1.getDay() - e2.getDay();
		int sTime = e1.getStartTime().compareTo(e2.getStartTime());
		int eTime = e1.getEndTime().compareTo(e2.getEndTime());
		int title = e1.getEventTitle().compareTo(e2.getEventTitle());
		
		if(year == 0){
			if(month == 0){
				if(day == 0){
					if(sTime == 0){
						if(eTime == 0){
							return title;
						}
						else return eTime;
					}
					else return sTime;
				}
				else return day;
			}
			else return month;
		}
		else return year;
	}
}
