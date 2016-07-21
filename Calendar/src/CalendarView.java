import java.awt.Color;
import java.awt.Dimension;
import java.util.GregorianCalendar;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class CalendarView extends JPanel {
	private CalendarWithEvents calendar;
	private int width;
	private int height;
	
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
	}
}
