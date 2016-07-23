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
				LocalTime sTime = LocalTime.parse(sTimeField.getText());
				LocalTime eTime = LocalTime.parse(eTimeField.getText());
				
				Event ev = new Event(eTitle, date, sTime, eTime);
				cal.addEvent(ev);
			} catch (DateTimeParseException | ParseException e) {
				JOptionPane.showMessageDialog(null, "Time format must be HH:MM", "Input Error", JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}
}
