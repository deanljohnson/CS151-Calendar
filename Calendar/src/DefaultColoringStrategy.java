import java.awt.Color;

import javax.swing.JButton;
import java.util.Calendar;

/**
 * default coloring strategy for buttons, which implements IButtonCloringStrategy interface
 * @author Three Amigos
 */

public class DefaultColoringStrategy implements IButtonColoringStrategy {
	@Override
	public void color(JButton button, int date, CalendarWithEvents cal, boolean selected) {
		if (cal.getEventsOnDate(date, cal.get(Calendar.MONTH), cal.get(Calendar.YEAR)).size() > 0){
			button.setForeground(Color.BLUE);
		}else{
			button.setForeground(Color.BLACK);
		}
		
		button.setBackground((selected) ? Color.CYAN : null);	
	}
}
