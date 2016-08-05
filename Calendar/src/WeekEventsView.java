import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 * Viewer for displaying weekly events
 * @author Three Amigos
 */

public class WeekEventsView extends JPanel {
	private DAYS[] arrayOfDays = DAYS.values();
	/**
	 * constructor
	 * @param events: arraylist of event
	 * @param w: width
	 * @param h: height
	 */
	public WeekEventsView(ArrayList<Event> events, int w, int h){
		setLayout(new GridLayout(1, 7));
		
		ArrayList<JTextArea> dayAreas = new ArrayList<JTextArea>(7);
		Collections.sort(events, new EventComparator());
		
		int eventIndex = 0;
		for (int i = 0; i < 7; i++){
			dayAreas.add(new JTextArea());
			dayAreas.get(i).setEditable(false);
			dayAreas.get(i).setBorder(BorderFactory.createSoftBevelBorder(1));
			String disp=arrayOfDays[i].toString() + "\n\n";
			
			for (; eventIndex < events.size() 
				   && events.get(eventIndex).getDayOfWeek() == (i + 1); 
				   eventIndex++)
			{
				disp += events.get(eventIndex).getStartTime() + "-" + events.get(eventIndex).getEndTime()  + "\n" 
						+ events.get(eventIndex).getEventTitle() + "\n\n";
			}
			dayAreas.get(i).setText(disp);
			add(dayAreas.get(i));
		}
	
		//This helps keep parent LayoutManagers from changing our size
		setPreferredSize(new Dimension(w, h));
		setMaximumSize(new Dimension(w, h));
	}
}
