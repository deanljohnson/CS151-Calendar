import java.time.LocalTime;
import java.util.Date;


public class Events {
	private Date eventDate;
	private LocalTime startTime;
	private LocalTime endTime;
	private String eventTitle;
	
	Events(String eventTitle, Date date, LocalTime startTime, LocalTime endTime){
		this.eventDate = date;
		this.startTime = startTime;
		this.endTime = endTime;
		this.eventTitle = eventTitle;
		
	}
	
	String getEventTitle(){
		return this.eventTitle;
	}

	LocalTime getStartTime(){
		return this.startTime;
	}

	Date getEventDate(){
		return this.eventDate;
	}
	
	LocalTime getEndTime(){
		return this.endTime;
	}
	
}
