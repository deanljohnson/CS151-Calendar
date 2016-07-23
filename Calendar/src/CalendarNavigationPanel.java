import javax.swing.JButton;
import javax.swing.JPanel;

public class CalendarNavigationPanel extends JPanel {
	private JButton todayButton;
	private JButton prevButton;
	private JButton nextButton;
	private JButton createButton;
	
	public CalendarNavigationPanel(){
		todayButton = new JButton("Today");
		prevButton = new JButton("<");
		nextButton = new JButton(">");
		createButton = new JButton("Create");
		
		add(todayButton);
		add(prevButton);
		add(nextButton);
		add(createButton);
	}
	
	public JButton getTodayButton() { return todayButton; }
	public JButton getPrevButton() { return prevButton; }
	public JButton getNextButton() { return nextButton; }
	public JButton getCreateButton() { return createButton; }
}
