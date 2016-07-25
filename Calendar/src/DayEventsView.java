import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JPanel;
import javax.swing.JTextArea;

public class DayEventsView extends JPanel {
	public DayEventsView(ArrayList<Event> events, int w, int h){
		JTextArea textArea = new JTextArea();
		textArea.setEditable(false);
		display(events, textArea);
		setLayout(new BorderLayout());
		add(textArea, BorderLayout.CENTER);
		
		//This helps keep parent LayoutManagers from changing our size
		setPreferredSize(new Dimension(w, h));
		setMaximumSize(new Dimension(w, h));
	}
	
	private void display(ArrayList<Event> eventList, JTextArea textArea){
		// display sorted events
		Collections.sort(eventList, new EventComparator());
		String disp="";
		for(Event e : eventList){
			disp += (e.getMonth() + 1) + "/" + e.getDay() + "/" + e.getYear() + "\t"
					+ e.getStartTime() + "-" + e.getEndTime()  + "\t" 
					+ e.getEventTitle() + "\n";
		}
		textArea.setText(disp);
	}
}
