import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.BorderFactory;
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
		
		//This helps keep parent LayoutManagers from changing our size
		setPreferredSize(new Dimension(width, height));
		setMaximumSize(new Dimension(width, height));
		
		//Set a border, just so we can visualize where it is for now
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		///
		currentMonth = new JLabel(arrayOfMonths[cal.get(Calendar.MONTH)]+", "+cal.get(Calendar.YEAR));
		add(currentMonth);

		cal.set(Calendar.DAY_OF_MONTH, 1);
		JButton monthArrow1 = new JButton ("<");
		JButton monthArrow2 = new JButton (">");
		
		monthArrow1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				cal.add(Calendar.MONTH, -1);
				currentMonth.setText(arrayOfMonths[cal.get(Calendar.MONTH)].toString()+", "+cal.get(Calendar.YEAR));
				repaint();
				remove(3); //removes the old calendar
				add(drawCal(cal)); //draws the new calendar
			}
		});
		monthArrow2.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				cal.add(Calendar.MONTH, 1);
				currentMonth.setText(arrayOfMonths[cal.get(Calendar.MONTH)].toString()+", "+cal.get(Calendar.YEAR));
				repaint();
				remove(3);
				add(drawCal(cal));
			}
		});
		
		add(monthArrow1);
		add(monthArrow2);
		add(drawCal(cal));
		
	}
	//Calendar panel
	JPanel drawCal(Calendar c){
		int noOfWeeks = 6;
		if(c.getActualMaximum(Calendar.DAY_OF_MONTH)==31) {
			if(c.get(Calendar.DAY_OF_WEEK) == 6 || c.get(Calendar.DAY_OF_WEEK) == 7 ){
				noOfWeeks = 7;
			}
		}else noOfWeeks=6;
		JPanel calDays = new JPanel(new GridLayout(noOfWeeks,7));
		int w = (int) (width/1.2);
		int h = (int) (height/1.2);
		calDays.setPreferredSize(new Dimension(w, h));
		calDays.setMaximumSize(new Dimension(w, h));
		
		
		for (int j=0; j<arrayOfDays.length; j++){
			JButton kButton = new JButton(""+arrayOfDays[j].toString().charAt(0));
			calDays.add(kButton);
		};
		
		int start  = c.get(Calendar.DAY_OF_WEEK)-1;
		for (int j = 0; j< start; j++){
			JButton dummyButton = new JButton("");
			calDays.add(dummyButton);

		}
		for (int i=1; i<=c.getActualMaximum(Calendar.DAY_OF_MONTH);i++){
			JButton dayButton = new JButton(""+i);
			calDays.add(dayButton);
			
			//TODO: Add factory method listeners
		}	
		
		return calDays;
	}

}
