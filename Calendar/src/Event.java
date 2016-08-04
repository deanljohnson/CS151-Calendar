import java.util.Date;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;

public class Event {
	private Calendar cal;
	private LocalTime startTime;
	private LocalTime endTime;
	private String eventTitle;
	
	Event(String eventTitle, Date date, LocalTime startTime, LocalTime endTime) throws Exception{
		if(startTime.isAfter(endTime)) throw new Exception("Start time should be ahead of end time.");
		this.cal = Calendar.getInstance();
		this.cal.setTime(date);
		this.startTime = startTime;
		this.endTime = endTime;
		this.eventTitle = eventTitle;
	}
	
	public boolean equals(Object otherObject){
		Event other = (Event) otherObject;
		return this.getEventTitle().equals(other.getEventTitle()) && this.getYear() == other.getYear()
				&& this.getMonth() == other.getMonth() && this.getDay() == other.getDay() &&
				this.getStartTime().equals(other.getStartTime()) && this.getEndTime().equals(other.getEndTime());
	}
	
	public int getYear(){
		return cal.get(Calendar.YEAR);
	}
	
	public int getMonth(){
		return cal.get(Calendar.MONTH);
	}
	
	public int getDay(){
		return cal.get(Calendar.DAY_OF_MONTH);
	}
	
	public int getDayOfWeek(){
		return cal.get(Calendar.DAY_OF_WEEK);
	}
	
	public LocalTime getStartTime(){
		return this.startTime;
	}
	
	public LocalTime getEndTime(){
		return this.endTime;
	}
	
	public String getEventTitle(){
		return this.eventTitle;
	}
	
	public static String toStringEvent(ArrayList<Event> events){
		String str = "";
		for (Event e: events){
			str += e.getEventTitle()+"\n";
		}
		
		return str;
	}
}
