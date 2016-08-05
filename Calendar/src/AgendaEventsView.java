import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class AgendaEventsView extends JPanel{
	private DAYS[] arrayOfDays = DAYS.values();
	private MONTHS[] arrayOfMonths = MONTHS.values();
	public AgendaEventsView(ArrayList<Event> events, int w, int h){
		
		setPreferredSize(new Dimension(w,h));
		setMaximumSize(new Dimension(w,h));
		
		JTextArea textArea = new JTextArea();
		textArea.setEditable(false);
		display(events, textArea);
		setLayout(new BorderLayout());
		add(textArea, BorderLayout.CENTER);
		
	}

	private void display(ArrayList<Event> eventList, JTextArea textArea){
		// display sorted events
		Collections.sort(eventList, new EventComparator());
		String disp="";
		for(Event e : eventList){
			disp += arrayOfDays[e.getDayOfWeek()-1].toString().substring(0,3)+" "
					+ arrayOfMonths[e.getMonth()].toString().substring(0,3) 
					+ " " + e.getDay() + "\t"
					+ e.getStartTime() + " - " + e.getEndTime()  + "\t" 
					+ e.getEventTitle() + "\n";
		}
		textArea.setText(disp);
	}
}
