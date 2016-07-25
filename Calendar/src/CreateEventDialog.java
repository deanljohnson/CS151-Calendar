import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.Calendar;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class CreateEventDialog {
	public static void ShowDialog(CalendarWithEvents cal){
		JTextField titleField = new JTextField(20);
		JTextField dateField = new JTextField(10);
		JTextField sTimeField = new JTextField(5);
		JTextField eTimeField = new JTextField(5);
		
		dateField.setText((cal.get(Calendar.MONTH) + 1) + "/" + cal.get(Calendar.DAY_OF_MONTH) + "/" + cal.get(Calendar.YEAR));
		
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
			SimpleDateFormat eDate = new SimpleDateFormat("MM/dd/yyyy");

			try {
				Date date = (Date) eDate.parse(dateField.getText());
				LocalTime sTime = parseTime(sTimeField.getText());
				LocalTime eTime = parseTime(eTimeField.getText());
				
				Event ev = new Event(eTitle, date, sTime, eTime);
				
				if (overlappingEvent(cal, ev))
					throw new Exception("The given event overlaps with an already existing event.");
				
				cal.addEvent(ev);
			} catch (DateTimeParseException | ParseException e) {
				JOptionPane.showMessageDialog(null, "Time format must be HH", "Input Error", JOptionPane.INFORMATION_MESSAGE);
			} catch (Exception e){
				JOptionPane.showMessageDialog(null, e.getMessage(), "Input Error", JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}
	
	private static LocalTime parseTime(String s) throws Exception{
		boolean am = s.endsWith("am") || s.endsWith("AM");
		boolean pm = s.endsWith("pm") || s.endsWith("PM");
		int hour = 0, min = 0;
		
		if (am && pm)
			throw new Exception("Cannot parse " + s + " as a time.");
		if (am)
		{
			s = s.replaceAll("am", "");
			s = s.replaceAll("AM", "");
		}
		if (pm)
		{
			s = s.replaceAll("pm", "");
			s = s.replaceAll("PM", "");
		}
		
		if (s.contains(":")){
			String[] tokens = s.split(":");
			hour = Integer.parseInt(tokens[0]);
			min = Integer.parseInt(tokens[1]);
		}
		else{
			hour = Integer.parseInt(s);
			min = 0;
		}
		
		if (pm && hour < 12) hour += 12;
		
		return LocalTime.of(hour, min);
	}
	
	private static boolean overlappingEvent(CalendarWithEvents cal, Event ev){
		Calendar clStart = Calendar.getInstance();
		clStart.set(ev.getYear(), ev.getMonth(), ev.getDay(), ev.getStartTime().getHour(), ev.getStartTime().getMinute());
		
		Calendar clEnd = Calendar.getInstance();
		clEnd.set(ev.getYear(), ev.getMonth(), ev.getDay(), ev.getEndTime().getHour(), ev.getEndTime().getMinute());
		
		return cal.getEventsAgenda(clStart, clEnd).size() > 0;
	}
}
