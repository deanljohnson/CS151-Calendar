import java.util.Date;
import java.util.Calendar;

public class Event {
	private Calendar cal;
	private Date startTime;
	private Date endTime;
	private String eventTitle;
	
	Event(String eventTitle, Date date, Date startTime, Date endTime){
		this.cal = Calendar.getInstance();
		this.cal.setTime(date);
		this.startTime = startTime;
		this.endTime = endTime;
		this.eventTitle = eventTitle;
		
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
	
	public Date getStartTime(){
		return this.startTime;
	}
	
	public Date getEndTime(){
		return this.endTime;
	}
	
	public String getEventTitle(){
		return this.eventTitle;
	}
}
