import javax.swing.JButton;
import javax.swing.JPanel;

public class EventDisplaySelectionPanel extends JPanel {
	private JButton dayButton;
	private JButton weekButton;
	private JButton monthButton;
	private JButton agendaButton;
	private JButton fromFileButton;
	
	public EventDisplaySelectionPanel(){
		dayButton = new JButton("Day");
		weekButton = new JButton("Week");
		monthButton = new JButton("Month");
		agendaButton = new JButton("Agenda");
		fromFileButton = new JButton("From File");
		
		add(dayButton);
		add(weekButton);
		add(monthButton);
		add(agendaButton);
		add(fromFileButton);
	}
	
	public JButton getDayButton() { return dayButton; }
	public JButton getWeekButton() { return weekButton; }
	public JButton getMonthButton() { return monthButton; }
	public JButton getAgendaButton() { return agendaButton; }
	public JButton getFromFileButton() { return fromFileButton; }
}
