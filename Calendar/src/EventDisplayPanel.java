import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class EventDisplayPanel extends JPanel {
	public enum FilterType{
		Day, Week, Month, Agenda
	}
	
	private CalendarWithEvents calendar;
	private FilterType filter = FilterType.Day;
	private int width;
	private int height;
	private JTextArea textArea;
	
	public EventDisplayPanel(CalendarWithEvents cal, int w, int h){
		calendar = cal;
		
		textArea = new JTextArea();
		textArea.setEditable(false);
		
		setLayout(new BorderLayout());
		add(textArea, BorderLayout.CENTER);
		
		calendar.addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent e) {
				refreshView();
			}
		});
		//We want to track the width and height - we will use this info
		//to create the icons for each individual day later
		//when we are viewing events by week/month
		this.width = w;
		this.height = h;
		
		//This helps keep parent LayoutManagers from changing our size
		setPreferredSize(new Dimension(width, height));
		setMaximumSize(new Dimension(width, height));
		
		//Set a border, just so we can visualize where it is for now
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
	}
	
	public void setFilterType(FilterType t){
		filter = t;
		refreshView();
	}
	
	private void refreshView(){
		//In here we will create and change the view to display
		//the events from a particular day/week/month or to show
		//the agenda view
		
		ArrayList<Event> eventList = new ArrayList<Event>();
		if (filter == FilterType.Day){
			eventList = calendar.getEventsToday();
			display(eventList);
		}else if (filter == FilterType.Week){
			eventList = calendar.getEventsThisWeek();
			display(eventList);
		}else if (filter == FilterType.Month){
			eventList = calendar.getEventsThisMonth();
			display(eventList);
		}else if (filter == FilterType.Agenda){
			eventList = calendar.getEventsAgenda();
			display(eventList);
		}
	}
	
	private void display(ArrayList<Event> eventList){
		// display sorted events
		Collections.sort(eventList, new Comparator<Event>(){
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
						else return month;
					}
					else return month;
				}
				else return year;
			}
		});
		String disp="";
		for(Event e : eventList){
			disp+=e.getEventTitle() + "\t" + e.getStartTime() + "-" + e.getEndTime()+"\n";
		}
		textArea.setText(disp);
	}
}
