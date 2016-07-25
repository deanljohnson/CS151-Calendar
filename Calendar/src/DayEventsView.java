import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

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
						else return day;
					}
					else return month;
				}
				else return year;
			}
		});
		String disp="";
		for(Event e : eventList){
			disp += (e.getMonth() + 1) + "/" + e.getDay() + "/" + e.getYear() + "\t"
					+ e.getStartTime() + "-" + e.getEndTime()  + "\t" 
					+ e.getEventTitle() + "\n";
		}
		textArea.setText(disp);
	}
}
