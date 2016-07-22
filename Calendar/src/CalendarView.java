import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Calendar;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

enum MONTHS{
	January, February, March, April, May, June, July, August, September, October, November, December;
}
enum DAYS{
	Sunday, Monday, Tuesday, Wednesday, Thursday, Friday, Saturday;
}

public class CalendarView extends JPanel {
	private CalendarWithEvents calendar;
	private int width;
	private int height;
	JLabel currentMonth ;
	DAYS[] arrayOfDays = DAYS.values();
	MONTHS[] arrayOfMonths = MONTHS.values();
	
	public CalendarView(CalendarWithEvents cal, int w, int h){
		calendar = cal;
		
		//We want to track the width and height - we will use this info
		//to create the icons for each individual day later
		this.width = w;
		this.height = h;
		
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		//This helps keep parent LayoutManagers from changing our size
		setPreferredSize(new Dimension(width, height));
		setMaximumSize(new Dimension(width, height));
		
		currentMonth = new JLabel(arrayOfMonths[cal.get(Calendar.MONTH)]+", "+cal.get(Calendar.YEAR));
		currentMonth.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(currentMonth);
		
		add(drawCal(cal));
		
	}
	//Calendar panel
	JPanel drawCal(Calendar c){
		int initialDay = c.get(Calendar.DAY_OF_MONTH);
		c.set(Calendar.DAY_OF_MONTH, 1);
		int buttonsAdded = 0;
		
		int nRows = 6;
		if(c.getActualMaximum(Calendar.DAY_OF_MONTH)==31) {
			if(c.get(Calendar.DAY_OF_WEEK) == 6 || c.get(Calendar.DAY_OF_WEEK) == 7 ){
				nRows = 7;
			}
		}else if(c.getActualMaximum(Calendar.DAY_OF_MONTH)==30){
			if(c.get(Calendar.DAY_OF_WEEK) == 7 ){
				nRows = 7;
			}
		}
		else nRows=6;
		
		JPanel calDays = new JPanel(new GridLayout(nRows,7));
		int w = (int) (width/1.2);
		int h = (int) (height/1.2);
		calDays.setPreferredSize(new Dimension(w, h));
		calDays.setMaximumSize(new Dimension(w, h));
		
		for (int j=0; j<arrayOfDays.length; j++, buttonsAdded++){
			JButton kButton = new JButton(""+arrayOfDays[j].toString().charAt(0));
			kButton.setBackground(new Color(75, 75, 255, 255));
			kButton.setForeground(Color.BLACK);
			kButton.setEnabled(false);
			calDays.add(kButton);
		};
		
		int start  = c.get(Calendar.DAY_OF_WEEK)-1;
		for (int j = 0; j< start; j++, buttonsAdded++){
			JButton dummyButton = new JButton("");
			dummyButton.setEnabled(false);
			calDays.add(dummyButton);

		}
		for (int i=1; i<=c.getActualMaximum(Calendar.DAY_OF_MONTH); i++, buttonsAdded++){
			JButton dayButton = new JButton(""+i);
			calDays.add(dayButton);
			
			//TODO: Add factory method listeners
		}
		
		//Add dummy buttons for days at the end of the month
		for (; buttonsAdded < nRows * 7; buttonsAdded++){
			JButton dummyButton = new JButton("");
			dummyButton.setEnabled(false);
			calDays.add(dummyButton);
		}

		c.set(Calendar.DAY_OF_MONTH, initialDay);
		return calDays;
	}

	public void moveToToday(){
		calendar.setTime(Calendar.getInstance().getTime());
		refreshCalendar();
	}
	
	public void moveToPrevMonth(){
		calendar.add(Calendar.MONTH, -1);
		refreshCalendar();
	}
	
	public void moveToNextMonth(){
		calendar.add(Calendar.MONTH, 1);
		refreshCalendar();
	}
	
	private void refreshCalendar(){
		currentMonth.setText(arrayOfMonths[calendar.get(Calendar.MONTH)].toString()+", "+calendar.get(Calendar.YEAR));
		repaint();
		remove(1);
		add(drawCal(calendar));
	}
}
