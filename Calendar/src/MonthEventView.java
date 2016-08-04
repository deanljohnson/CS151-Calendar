import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class MonthEventView extends JPanel{
	private DAYS[] arrayOfDays = DAYS.values();
	public MonthEventView(CalendarWithEvents c, ArrayList<Event> events, int w, int h){

		//Maintain the dimension
		setPreferredSize(new Dimension(w,h));
		setMaximumSize(new Dimension(w,h));
		
		//Since CalendarWithEvents' set method has been modified, so the days are extracted to a calendar instance
		Calendar cl = Calendar.getInstance();
		cl.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), 1);

		int nRows = 6;
		if(cl.getActualMaximum(Calendar.DAY_OF_MONTH)==31) {
			if(cl.get(Calendar.DAY_OF_WEEK) == 6 || cl.get(Calendar.DAY_OF_WEEK) == 7 ){
				nRows = 7;
			}
		}else if(cl.getActualMaximum(Calendar.DAY_OF_MONTH)==30){
			if(cl.get(Calendar.DAY_OF_WEEK) == 7 ){
				nRows = 7;
			}
		}
		else nRows=6;
		
		setLayout(new GridLayout(nRows,7));
		
		int boxesAdded=0;
		
		for (int j=0; j<arrayOfDays.length; j++, boxesAdded++){
			JButton tAreas = new JButton(""+arrayOfDays[j].toString().charAt(0));
			tAreas.setBorder(BorderFactory.createBevelBorder(1));
			tAreas.setEnabled(false);
			add(tAreas);
		};
		
		int start  = cl.get(Calendar.DAY_OF_WEEK)-1;
		for (int j = 0; j< start; j++, boxesAdded++){
			JTextArea emptyAreas = new JTextArea();
			emptyAreas.setBorder(BorderFactory.createBevelBorder(1));
			emptyAreas.setEditable(false);
			add(emptyAreas);
			cl.set(Calendar.DAY_OF_MONTH, j);
		}
		
		for (int i=1; i<=cl.getActualMaximum(Calendar.DAY_OF_MONTH); i++, boxesAdded++){
			JTextArea textArea = new JTextArea();
			textArea.setEditable(false);
			textArea.setBorder(BorderFactory.createBevelBorder(1));
			
			cl.set(Calendar.DAY_OF_MONTH, i);
			textArea.setText(""+i+"\n"+Event.toStringEvent(c.getEventsOnDate(cl.get(Calendar.DAY_OF_MONTH), cl.get(Calendar.MONTH), cl.get(Calendar.YEAR))));
			
			add(textArea);
		}
		for (; boxesAdded < nRows * 7; boxesAdded++){
			JTextArea dummyArea = new JTextArea();
			dummyArea.setEnabled(false);
			dummyArea.setBorder(BorderFactory.createBevelBorder(1));
			add(dummyArea);
		}

	}
}
