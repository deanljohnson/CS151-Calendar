import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 * Viewer to display events on a single day
 * @author Three Amigos
 */

public class DayEventsView extends JPanel {
	/**
	 * constructor
	 * @param events: arraylist of event
	 * @param w: width
	 * @param h: height
	 */
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
	/**
	 * display events
	 * @param eventList: ArrayList<Event>
	 * @param textArea: JTextArea
	 */
	private void display(ArrayList<Event> eventList, JTextArea textArea){
		// display sorted events
		Collections.sort(eventList, new EventComparator());
		String disp="";
		for(Event e : eventList){
			disp += e.getStartTime() + "-" + e.getEndTime()  + "\t" 
					+ e.getEventTitle() + "\n";
		}
		textArea.setText(disp);
	}
}
