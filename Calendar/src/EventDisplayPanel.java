import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Viewer for displaying events
 * @author Three Amigos
 */

public class EventDisplayPanel extends JPanel {
	public enum FilterType{
		Day, Week, Month, Agenda
	}
	
	private CalendarWithEvents calendar;
	private FilterType filter = FilterType.Day;
	private int width;
	private int height;
	private JPanel eventView;
	/**
	 * constructor
	 * @param cal: CalendarWithEvents
	 * @param w: width
	 * @param h: height
	 */
	public EventDisplayPanel(CalendarWithEvents cal, int w, int h){
		calendar = cal;
		
		eventView = new DayEventsView(cal.getEventsToday(), w, h);
		
		setLayout(new BorderLayout());
		add(eventView, BorderLayout.CENTER);
		
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
	/**
	 * set filter type
	 * @param t: FilterType
	 */
	public void setFilterType(FilterType t){
		filter = t;
		refreshView();
	}
	/**
	 * repaint view
	 */
	private void refreshView(){
		//In here we will create and change the view to display
		//the events from a particular day/week/month or to show
		//the agenda view
		
		ArrayList<Event> eventList = new ArrayList<Event>();
		removeAll();
		if (filter == FilterType.Day){
			eventList = calendar.getEventsToday();
			eventView = new DayEventsView(eventList, width, height);
		}else if (filter == FilterType.Week){
			eventList = calendar.getEventsThisWeek();
			eventView = new WeekEventsView(eventList, width, height);
		}else if (filter == FilterType.Month){
			eventList = calendar.getEventsThisMonth();
			eventView = new MonthEventView(calendar, width, height);
		}else if (filter == FilterType.Agenda){
			eventList = calendar.getEventsAgenda(AgendaDialog.getStartDate(),AgendaDialog.getEndDate());
			eventView = new AgendaEventsView(eventList, width, height);
		}
		
		add(eventView, BorderLayout.CENTER);
		revalidate();
		repaint();
	}
}
