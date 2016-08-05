import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

enum MONTHS{
	January, February, March, April, May, June, July, August, September, October, November, December;
}
enum DAYS{
	Sunday, Monday, Tuesday, Wednesday, Thursday, Friday, Saturday;
}

/**
 * Viewer for displaying current month with dates as buttons to select and show events in event display panel.
 * @author Three Amigos
 */

public class CalendarView extends JPanel {
	private IButtonColoringStrategy colorStrat;
	private CalendarWithEvents cal;
	private int width;
	private int height;
	private JLabel currentMonth;
	private DAYS[] arrayOfDays = DAYS.values();
	private MONTHS[] arrayOfMonths = MONTHS.values();
	private JButton selectedButton; // saved selected button
	int selectedDate; // track selected date & probably need to change to Date type or Calendar
	/**
	 * constructor
	 * @param calendar: CalendarWithEvents
	 * @param w: width
	 * @param h: height
	 * @param colorStrat: color strategy
	 */
	public CalendarView(CalendarWithEvents calendar, int w, int h, IButtonColoringStrategy colorStrat){
		this.colorStrat = colorStrat;
		cal = calendar;
		selectedDate = cal.get(Calendar.DAY_OF_MONTH); // initialize date to highlight button
		
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
		
		cal.addEventListener(new ChangeListener(){
			@Override
			public void stateChanged(ChangeEvent arg0) {
				refreshCalendar();
			}
		});
	}
	/**
	 * constructor with default coloring strategy
	 * @param calendar: CalendarWithEvents
	 * @param w: width
	 * @param h: height
	 */
	public CalendarView(CalendarWithEvents calendar, int w, int h){
		this(calendar, w, h, new DefaultColoringStrategy());
	}
	/**
	 * erase previous button background and select new button
	 * @param b: selected button
	 * @param d: selected date
	 */
	public void select(JButton b, int d){
		// if there are button has been selected, set background to null
		if(selectedButton != null){
			colorStrat.color(selectedButton, selectedDate, cal, false);
		}
		
		selectedButton = b;
		selectedDate = d; // update selected day
		colorStrat.color(selectedButton, selectedDate, cal, true);
	}
	/**
	 * jump to today's date and button
	 */
	public void moveToToday(){
		cal.setTime(Calendar.getInstance().getTime());
		selectedDate = cal.get(Calendar.DAY_OF_MONTH);
		refreshCalendar();
	}
	/**
	 * move to previous month
	 */
	public void moveToPrevMonth(){
		cal.add(Calendar.MONTH, -1);
		refreshCalendar();
	}
	/**
	 * move to next month
	 */
	public void moveToNextMonth(){
		cal.add(Calendar.MONTH, 1);
		refreshCalendar();
	}
	/**
	 * re-draw calendar
	 */
	private void refreshCalendar(){
		currentMonth.setText(arrayOfMonths[cal.get(Calendar.MONTH)].toString()+", "+cal.get(Calendar.YEAR));
		repaint();
		remove(1);
		add(drawCal(cal));
	}
	/**
	 * draw calendar
	 * @param c: Calendar
	 * @return fixed calendar panel
	 */
	private JPanel drawCal(Calendar c){
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
			int day = i;
			
			dayButton.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					// erase previous button background and select new button
					select(dayButton, day);
					cal.set(Calendar.DAY_OF_MONTH, day);
				}
			});
			
			boolean selected = (day == selectedDate) || (selectedDate > c.getActualMaximum(Calendar.DAY_OF_MONTH) &&
					day == c.getActualMaximum(Calendar.DAY_OF_MONTH));
			colorStrat.color(dayButton, day, cal, selected);
			if (selected)
				selectedButton = dayButton;
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
}
