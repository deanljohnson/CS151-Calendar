import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class CalendarNavigationPanel extends JPanel {
	private JButton todayButton;
	private JButton prevButton;
	private JButton nextButton;
	private JButton createButton;
	
	public CalendarNavigationPanel(CalendarWithEvents calendar, CalendarView calView){
		todayButton = new JButton("Today");
		prevButton = new JButton("<");
		nextButton = new JButton(">");
		createButton = new JButton("Create");
		
		todayButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				calView.moveToToday();
			}
		});
		prevButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				calView.moveToPrevMonth();
			}
		});
		nextButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				calView.moveToNextMonth();
			}
		});
		createButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				CreateEventDialog.ShowDialog(calendar);
			}
		});
		
		add(todayButton);
		add(prevButton);
		add(nextButton);
		add(createButton);
	}
}
