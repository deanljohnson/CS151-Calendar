import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeParseException;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Dialog pop up for user input dates to display events between selected dates.
 * @author Three Amigos
 */

public class AgendaDialog {
	private static Date sDate;
	private static Date eDate;
	/**
	 * show dialog and allow user to input dates
	 * @param cal: CalendarWithEvents
	 */
	public static void ShowDialog(CalendarWithEvents cal){
		JTextField sDateField = new JTextField(10);
		JTextField eDateField = new JTextField(10);
			
		JPanel consolePanel = new JPanel();
		consolePanel.add(new JLabel("Start Date"));
		consolePanel.add(sDateField);
		consolePanel.add(new JLabel("End Date"));
		consolePanel.add(eDateField);
		
		sDateField.setText((cal.get(Calendar.MONTH) + 1) + "/" + cal.get(Calendar.DAY_OF_MONTH) + "/" + cal.get(Calendar.YEAR));
		
		int response = JOptionPane.showConfirmDialog(null, consolePanel, "Agenda", JOptionPane.OK_CANCEL_OPTION);
		if (response == JOptionPane.OK_OPTION){
			
			SimpleDateFormat date = new SimpleDateFormat("MM/dd/yyyy");
			// throw and catch exceptions, for date format and end date has to be after start date
			try {
				sDate = (Date) date.parse(sDateField.getText());
				eDate = (Date) date.parse(eDateField.getText());
				if(eDate.before(sDate)) throw new Exception("End date has to be after start date.");
			} catch (DateTimeParseException | ParseException e) {
				JOptionPane.showMessageDialog(null, "Date format must be MM/DD/YYY", "Input Error", JOptionPane.INFORMATION_MESSAGE);
			} catch (Exception e){
				JOptionPane.showMessageDialog(null, e.getMessage(), "Input Error", JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}
	/**
	 * @return start date
	 */
	public static Date getStartDate(){
		return sDate;
	}
	/**
	 * @return end date
	 */
	public static Date getEndDate(){
		return eDate;
	}
}
