import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Calendar {
	private static final int WIDTH = 1050;
	private static final int HEIGHT = 700;
	
	public static void main(String[] args){
		CalendarWithEvents calendar = new CalendarWithEvents();
		
		JFrame frame = new JFrame();
		frame.setSize(WIDTH, HEIGHT);
		
		frame.setLayout(new FlowLayout());
		
		JPanel leftPanel = new JPanel();
		JPanel rightPanel = new JPanel();
		
		//Setup the left panel. This includes the buttons to navigate the calendar,
		//as well as the calendar itself
		//TODO: Hook action listeners from calNavPanel into the calView
		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.PAGE_AXIS));
		CalendarNavigationPanel calNavPanel = new CalendarNavigationPanel();
		CalendarView calView = new CalendarView(calendar, 500, 500);
		
		calNavPanel.getTodayButton().addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				calView.moveToToday();
			}
		});
		calNavPanel.getPrevButton().addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				calView.moveToPrevMonth();
			}
		});
		calNavPanel.getNextButton().addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				calView.moveToNextMonth();
			}
		});
		
		leftPanel.add(calNavPanel);
		leftPanel.add(calView);
		
		
		//Setup the right panel. This includes the row of buttons to filter events,
		//(day, week, month, agenda), and a panel that show the events
		//TODO: Hook action listeners from eventSelPanel into the eventDisplay
		rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.PAGE_AXIS));
		EventDisplaySelectionPanel eventSelPanel = new EventDisplaySelectionPanel();
		EventDisplayPanel eventDisplay = new EventDisplayPanel(calendar, 500, 500);
		rightPanel.add(eventSelPanel);
		rightPanel.add(eventDisplay);

		
		frame.add(leftPanel);
		frame.add(rightPanel);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}
