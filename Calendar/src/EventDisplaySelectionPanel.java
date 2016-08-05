import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * Controller to select day, week, month, agenda view for events and load events from file.
 * @author Three Amigos
 */

public class EventDisplaySelectionPanel extends JPanel {
	private JButton dayButton;
	private JButton weekButton;
	private JButton monthButton;
	private JButton agendaButton;
	private JButton fromFileButton;
	/**
	 * constructor
	 * @param calendar: CalendarWithEvents
	 * @param eventDisplay: EventDisplayPanel
	 */
	public EventDisplaySelectionPanel(CalendarWithEvents calendar, EventDisplayPanel eventDisplay){
		dayButton = new JButton("Day");
		weekButton = new JButton("Week");
		monthButton = new JButton("Month");
		agendaButton = new JButton("Agenda");
		fromFileButton = new JButton("From File");
		
		dayButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				eventDisplay.setFilterType(EventDisplayPanel.FilterType.Day);
			}
		});
		weekButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				eventDisplay.setFilterType(EventDisplayPanel.FilterType.Week);
			}
		});
		monthButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				eventDisplay.setFilterType(EventDisplayPanel.FilterType.Month);
			}
		});
		agendaButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				AgendaDialog.ShowDialog(calendar);
				eventDisplay.setFilterType(EventDisplayPanel.FilterType.Agenda);
			}
		});
		fromFileButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				try{
					calendar.loadEvent();
					JOptionPane.showMessageDialog(null, "File found and Events loaded.\n"
							+ "Click Day, Week, Month, Agenda to view loaded events.", 
							"Success", JOptionPane.INFORMATION_MESSAGE);
				}catch(Exception e){
					JOptionPane.showMessageDialog(null, "Error found while loading file and events.\n"
							+ "Try examining input.txt file and fix errors.", "Input Error",
							JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		
		add(dayButton);
		add(weekButton);
		add(monthButton);
		add(agendaButton);
		add(fromFileButton);
	}
}
