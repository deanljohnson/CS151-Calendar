import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * Main class for calendar where set up main frame and construct other panels.
 * @author Three Amigos
 */

public class Calendar {
	private static final int WIDTH = 1400;
	private static final int HEIGHT = 800;
	/**
	 * set up main frame and other panels
	 * @param args: args
	 */
	public static void main(String[] args){
		CalendarWithEvents calendar = new CalendarWithEvents();
		
		JFrame frame = new JFrame();
		frame.setSize(WIDTH, HEIGHT);
		
		frame.setLayout(new FlowLayout());
		
		JPanel leftPanel = new JPanel();
		JPanel rightPanel = new JPanel();
		
		//Setup the left panel. This includes the buttons to navigate the calendar,
		//as well as the calendar itself
		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.PAGE_AXIS));
		CalendarView calView = new CalendarView(calendar, WIDTH/3, WIDTH/3);
		CalendarNavigationPanel calNavPanel = new CalendarNavigationPanel(calendar, calView);
		
		leftPanel.add(calNavPanel);
		leftPanel.add(calView);
		
		
		//Setup the right panel. This includes the row of buttons to filter events,
		//(day, week, month, agenda), and a panel that show the events
		rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.PAGE_AXIS));
		EventDisplayPanel eventDisplay = new EventDisplayPanel(calendar,WIDTH/2, WIDTH/2);
		EventDisplaySelectionPanel eventSelPanel = new EventDisplaySelectionPanel(calendar, eventDisplay);
		
		rightPanel.add(eventSelPanel);
		rightPanel.add(eventDisplay);

		
		frame.add(leftPanel);
		frame.add(rightPanel);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}
