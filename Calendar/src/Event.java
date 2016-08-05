import java.util.Date;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Event class to store title, date, start and end time of event.
 * @author Three Amigos
 */

public class Event {
	private Calendar cal;
	private LocalTime startTime;
	private LocalTime endTime;
	private String eventTitle;
	/**
	 * constructor
	 * @param eventTitle: String
	 * @param date: Date
	 * @param startTime: LocalTime
	 * @param endTime: LocalTime
	 * @throws Exception: time confilct
	 */
	Event(String eventTitle, Date date, LocalTime startTime, LocalTime endTime) throws Exception{
		if(startTime.isAfter(endTime)) throw new Exception("Start time should be ahead of end time.");
		this.cal = Calendar.getInstance();
		this.cal.setTime(date);
		this.startTime = startTime;
		this.endTime = endTime;
		this.eventTitle = eventTitle;
	}
	/**
	 * override equals()
	 */
	public boolean equals(Object otherObject){
		Event other = (Event) otherObject;
		return this.getEventTitle().equals(other.getEventTitle()) && this.getYear() == other.getYear()
				&& this.getMonth() == other.getMonth() && this.getDay() == other.getDay() &&
				this.getStartTime().equals(other.getStartTime()) && this.getEndTime().equals(other.getEndTime());
	}
	/**
	 * @return year of event
	 */
	public int getYear(){
		return cal.get(Calendar.YEAR);
	}
	/**
	 * @return month of event
	 */
	public int getMonth(){
		return cal.get(Calendar.MONTH);
	}
	/**
	 * @return day of event
	 */
	public int getDay(){
		return cal.get(Calendar.DAY_OF_MONTH);
	}
	/**
	 * @return day of week of event
	 */
	public int getDayOfWeek(){
		return cal.get(Calendar.DAY_OF_WEEK);
	}
	/**
	 * @return start time of event
	 */
	public LocalTime getStartTime(){
		return this.startTime;
	}
	/**
	 * @return end time of event
	 */
	public LocalTime getEndTime(){
		return this.endTime;
	}
	/**
	 * @return event title of event
	 */
	public String getEventTitle(){
		return this.eventTitle;
	}
	/**
	 * @param events: arraylist of event
	 * @return String of events' titles
	 */
	public static String toStringEvent(ArrayList<Event> events){
		String str = "";
		for (Event e: events){
			str += e.getEventTitle()+"\n";
		}
		
		return str;
	}
}
