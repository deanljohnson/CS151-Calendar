import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Scanner;

import javax.swing.JOptionPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Model of this project, which handles events, receives changes from controller and notifies viewer.
 * @author Three Amigos
 */

public class CalendarWithEvents extends GregorianCalendar {
	private ArrayList<ChangeListener> changeListeners;
	private ArrayList<ChangeListener> eventListeners;
	private ArrayList<Event> events;
	private DAYS[] arrayOfDays;
	/**
	 * constructor
	 */
	public CalendarWithEvents(){
		changeListeners = new ArrayList<ChangeListener>();
		eventListeners = new ArrayList<ChangeListener>();
		events = new ArrayList<Event>();
		arrayOfDays = DAYS.values();
	}
	/**
	 * add change listener
	 * @param l: change listener
	 */
	public void addChangeListener(ChangeListener l){
		changeListeners.add(l);
	}
	/**
	 * add change listener to event
	 * @param l: change listener
	 */
	public void addEventListener(ChangeListener l){
		eventListeners.add(l);
	}
	@Override
	public void set(int field, int value){
		super.set(field, value);
		notifyOfChange();
	}
	/**
	 * add single event and notify changes
	 * @param event: Event
	 */
	public void addEvent(Event event){
		events.add(event);
		notifyOfChange();
		notifyOfEventChange();
	}
	/**
	 * add an arraylist of events and notify changes
	 * @param events: arrayList of event
	 */
	public void addEvents(ArrayList<Event> events){
		for (Event e : events)
			this.events.add(e);
		notifyOfChange();
		notifyOfEventChange();
	}
	/**
	 * load events from existed file
	 * @throws Exception
	 */
	public void loadEvent() throws Exception{
		Scanner inFile = new Scanner(Paths.get("input.txt"));
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		while(inFile.hasNextLine()){
			String[] line = inFile.nextLine().split(";");
			String evTitle = line[0];
			Date sdate = (Date) dateFormat.parse(line[2]+"/"+"1"+"/"+line[1]);
			
			//Event recurring days
			String days = line[4];
			String[] eventDays = days.split("");
			eventDays = transform(eventDays);
			
			//Create end date
			GregorianCalendar gc = new GregorianCalendar(Integer.parseInt(line[1]),Integer.parseInt(line[3]),1);
			Date edate = (Date) dateFormat.parse(line[3]+"/"+gc.getActualMaximum(Calendar.DAY_OF_MONTH)+"/"+line[1]);
			
			//Event start and end time
			LocalTime sTime = LocalTime.parse(line[5]+":00");
			LocalTime eTime = LocalTime.parse(line[6]+":00");
	
			//Convert Date to Calendar so Calendar.add method can be used to increment days
			Calendar cal = Calendar.getInstance();
			cal.setTime(sdate);
			while(cal.getTime().before(edate)){
				if(Arrays.asList(eventDays).contains(arrayOfDays[cal.get(Calendar.DAY_OF_WEEK)-1].toString())){
					Event aEvent = new Event(evTitle, (Date) cal.getTime(), sTime, eTime);
					if(!events.contains(aEvent)) events.add(aEvent);
					cal.add(Calendar.DAY_OF_MONTH,1);
				}else{
					cal.add(Calendar.DAY_OF_MONTH,1);
				}
			}
		}
		inFile.close();
		
		notifyOfEventChange();
	}
	/**
	 * get events agenda between certain dates
	 * @param startDate: Date
	 * @param endDate: Date
	 * @return arraylist of events
	 */
	public ArrayList<Event> getEventsAgenda(Date startDate, Date endDate){
		Calendar clStart = Calendar.getInstance();
		Calendar clEnd = Calendar.getInstance();
		clStart.setTime(startDate);
		clEnd.setTime(endDate);
		
		return getEventsAgenda(clStart, clEnd);
	}
	/**
	 * get events agenda between certain dates
	 * @param clStart: Calendar
	 * @param clEnd: Calendar
	 * @return arraylist of events
	 */
	public ArrayList<Event> getEventsAgenda(Calendar clStart, Calendar clEnd){
		ArrayList<Event> thisEvents = new ArrayList<Event>();
		
		if (clStart.get(Calendar.DAY_OF_MONTH) != clEnd.get(Calendar.DAY_OF_MONTH))
			clEnd.add(Calendar.DAY_OF_MONTH, 1);// +1 added to include the end date in the filter
		
		for (Event e : events){
			Calendar startEventCal = Calendar.getInstance();
			startEventCal.set(e.getYear(), e.getMonth(), e.getDay(), e.getStartTime().getHour(), e.getStartTime().getMinute());
			Calendar endEventCal = Calendar.getInstance();
			endEventCal.set(e.getYear(), e.getMonth(), e.getDay(), e.getEndTime().getHour(), e.getEndTime().getMinute());
			
			if((clStart.before(startEventCal) && clEnd.after(startEventCal))
					|| (clStart.before(endEventCal) && clEnd.after(endEventCal))
					|| (startEventCal.before(clStart) && endEventCal.after(clStart))
					|| (endEventCal.before(clEnd) && endEventCal.after(clEnd))
					|| clStart.compareTo(startEventCal) == 0
					|| clEnd.compareTo(endEventCal) == 0) {
				thisEvents.add(e);
			}
		}
		return thisEvents;
	}
	/**
	 * @return arraylist of events in this month
	 */
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
	/**
	 * @return arraylist of events in this week
	 */
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
	/**
	 * @return arraylist of today's events
	 */
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
	/**
	 * get certain date's events
	 * @param day: int
	 * @param month: int
	 * @param year: int
	 * @return arraylist of events on that date
	 */
	public ArrayList<Event> getEventsOnDate(int day, int month, int year){
		ArrayList<Event> dateEvents = new ArrayList<Event>();
		
		for (Event e : events){
			if (month == e.getMonth()
				&& year == e.getYear()
				&& day == e.getDay())
			{
				dateEvents.add(e);
			}
		}
		Collections.sort(dateEvents, new EventComparator());
		return dateEvents;
	}
	/**
	 * notify changes
	 */
	private void notifyOfChange(){
		ChangeEvent ce = new ChangeEvent(this);
		for (ChangeListener l : changeListeners)
			l.stateChanged(ce);
	}
	/**
	 * notify event changes
	 */
	private void notifyOfEventChange(){
		ChangeEvent ce = new ChangeEvent(this);
		for (ChangeListener l : eventListeners)
			l.stateChanged(ce);
	}
	/**
	 * transform letters in input file into day
	 * @param arr: input String array
	 * @return String array of day
	 */
	private String[] transform(String[] arr){ 	
		for(int i=0; i<arr.length;i++){
				if (arr[i].equals("M")){
					arr[i] = "Monday";
				}else if (arr[i].equals("T")){
					arr[i] = "Tuesday";
				}else if (arr[i].equals("W")){
					arr[i] = "Wednesday";
				}else if (arr[i].equals("Th")){
					arr[i] = "Thursday";
				}else if (arr[i].equals("F")){
					arr[i] = "Friday";
				}else if (arr[i].equals("S")){
					arr[i] = "Saturday";
				}else if (arr[i].equals("Su")){
					arr[i] = "Sunday";
				}
			}
		 return arr;
	 }
}
