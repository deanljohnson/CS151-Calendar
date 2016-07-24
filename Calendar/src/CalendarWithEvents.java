import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Scanner;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class CalendarWithEvents extends GregorianCalendar {
	private ArrayList<ChangeListener> changeListeners = new ArrayList<ChangeListener>();
	private ArrayList<Event> events = new ArrayList<Event>();
	
	public CalendarWithEvents(){	
	}
	
	public void addChangeListener(ChangeListener l){
		changeListeners.add(l);
	}
	
	@Override
	public void set(int field, int value){
		super.set(field, value);
		notifyOfChange();
	}
	
	public void addEvent(Event event){
		events.add(event);
		notifyOfChange();
	}
	
	public void addEvents(ArrayList<Event> events){
		for (Event e : events)
			this.events.add(e);
		
		notifyOfChange();
	}
	
	public void loadEvent() throws Exception{
		Scanner inFile = new Scanner(Paths.get("events.txt"));
		SimpleDateFormat eDate = new SimpleDateFormat("MM/dd/yyyy");
		while(inFile.hasNextLine()){
			String[] line = inFile.nextLine().split(";");
			System.out.println(Arrays.toString(line));
			Date date = (Date) eDate.parse(line[0]);
			LocalTime sTime = LocalTime.parse(line[1]);
			LocalTime eTime = LocalTime.parse(line[2]);
			Event aEvent = new Event(line[3], date, sTime, eTime);
			if(!events.contains(aEvent)) events.add(aEvent);
		}
		inFile.close();
	}
	
	public ArrayList<Event> getEventsAgenda(){
		return events;
	}
	
	public ArrayList<Event> getEventsThisMonth(){
		ArrayList<Event> monthEvents = new ArrayList<Event>();
		
		for (Event e : events){
			if (get(Calendar.MONTH) == e.getMonth()
				&& get(Calendar.YEAR) == e.getYear())
			{
				monthEvents.add(e);
			}
		}
		
		return monthEvents;
	}
	
	public ArrayList<Event> getEventsThisWeek(){
		ArrayList<Event> weekEvents = new ArrayList<Event>();
		
		for (Event e : events){
			if (get(Calendar.MONTH) == e.getMonth()
				&& get(Calendar.YEAR) == e.getYear()
				&& e.getDay() >= get(Calendar.DAY_OF_MONTH) - (get(Calendar.DAY_OF_WEEK) - 1)
				&& e.getDay() <= get(Calendar.DAY_OF_MONTH) + (7 - get(Calendar.DAY_OF_WEEK)))
			{
				weekEvents.add(e);
			}
		}
		
		return weekEvents;
	}
	
	public ArrayList<Event> getEventsToday(){
		ArrayList<Event> dayEvents = new ArrayList<Event>();
		
		for (Event e : events){
			if (get(Calendar.MONTH) == e.getMonth()
				&& get(Calendar.YEAR) == e.getYear()
				&& get(Calendar.DAY_OF_MONTH) == e.getDay())
			{
				dayEvents.add(e);
			}
		}
		
		return dayEvents;
	}
	
	private void notifyOfChange(){
		ChangeEvent ce = new ChangeEvent(this);
		for (ChangeListener l : changeListeners)
			l.stateChanged(ce);
	}
}
