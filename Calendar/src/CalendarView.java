import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

enum MONTHS{
	January, February, March, April, May, June, July, August, September, October, November, December;
}
enum DAYS{
	Sunday, Monday, Tuesday, Wednesday, Thursday, Friday, Saturday;
}

public class CalendarView extends JPanel {
	private CalendarWithEvents cal;
	private int width;
	private int height;
	JLabel currentMonth ;
	DAYS[] arrayOfDays = DAYS.values();
	MONTHS[] arrayOfMonths = MONTHS.values();
	JButton createButton;
	JButton selectedButton; // saved selected button
	int selectedDate; // track selected date & probably need to change to Date type or Calendar
	
	public CalendarView(CalendarWithEvents calendar, int w, int h){
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
		
		createButton = new JButton("Create");
		createButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				launchDialog();
			}
		});
		add(createButton);
		
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
			int day = i;
			
			//TODO: Add factory method listeners
			dayButton.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					// erase previous button background and select new button
					select(dayButton, day);
					//TODO: link to event display
					cal.set(cal.get(Calendar.YEAR), 
							cal.get(Calendar.MONTH), day);
				}
			});
			
			// highlight today's date button
			if(day == selectedDate){
				select(dayButton, day);
			}
			// when change month in calendar view if selected date is out of range
			// set the selected button and date to the maximum date
			if(selectedDate > c.getActualMaximum(Calendar.DAY_OF_MONTH) &&
					day == c.getActualMaximum(Calendar.DAY_OF_MONTH)){
				select(dayButton, day);
			}
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
	// erase previous button background and select new button
	public void select(JButton b, int d){
		// if there are button has been selected, set background to null
		if(selectedButton != null){
			selectedButton.setBackground(null);
		}
		
		selectedButton = b;
		selectedButton.setBackground(Color.CYAN);
		selectedDate = d; // update selected day
	}
	public void moveToToday(){
		if(cal.get(Calendar.MONTH) == Calendar.getInstance().get(Calendar.MONTH) &&
				cal.get(Calendar.YEAR) == Calendar.getInstance().get(Calendar.YEAR)){
			/*
			if(cal.get(Calendar.DAY_OF_MONTH) == selectedDate){
				return;
			}
			else{
				System.out.println("!!!");
				selectedDate = cal.get(Calendar.DAY_OF_MONTH);
				refreshCalendar();
			}
			*/
			return;
		}
		else{
		cal.setTime(Calendar.getInstance().getTime());
		selectedDate = cal.get(Calendar.DAY_OF_MONTH);
		refreshCalendar();
		}
	}
	
	public void moveToPrevMonth(){
		cal.add(Calendar.MONTH, -1);
		refreshCalendar();
	}
	
	public void moveToNextMonth(){
		cal.add(Calendar.MONTH, 1);
		refreshCalendar();
	}
	
	private void refreshCalendar(){
		currentMonth.setText(arrayOfMonths[cal.get(Calendar.MONTH)].toString()+", "+cal.get(Calendar.YEAR));
		repaint();
		remove(1);
		add(drawCal(cal));
	}
	
	private void launchDialog(){
		JTextField  titleField = new JTextField(20);
		JTextField dateField = new JTextField(10);
		JTextField sTimeField = new JTextField(5);
		JTextField eTimeField = new JTextField(5);
		
		JPanel consolePanel = new JPanel();
		consolePanel.add(new JLabel("Event Title"));
		consolePanel.add(titleField);
		consolePanel.add(new JLabel("Date"));
		consolePanel.add(dateField);
		consolePanel.add(new JLabel("Start Time"));
		consolePanel.add(sTimeField);
		consolePanel.add(new JLabel("End Time"));
		consolePanel.add(eTimeField);
		
		int response = JOptionPane.showConfirmDialog(null, consolePanel, "Create Event", JOptionPane.OK_CANCEL_OPTION);
		if (response == JOptionPane.OK_OPTION){
			String eTitle = titleField.getText();
			SimpleDateFormat eDate = new SimpleDateFormat("mm/dd/yyyy");
			SimpleDateFormat time = new SimpleDateFormat("hh:mm");
			try {
				Date date = (Date) eDate.parse(dateField.getText());
				Date sTime = (Date) time.parse(sTimeField.getText());
				Date eTime = (Date) time.parse(sTimeField.getText());
				
				Event ev = new Event(eTitle, date, sTime, eTime);
				cal.addEvent(ev);
				cal.notifyOfChange();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
		}
	}
}
