import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
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
		calNavPanel.getCreateButton().addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				CreateEventDialog.ShowDialog(calendar);
			}
		});
		leftPanel.add(calNavPanel);
		leftPanel.add(calView);
		
		
		//Setup the right panel. This includes the row of buttons to filter events,
		//(day, week, month, agenda), and a panel that show the events
		rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.PAGE_AXIS));
		EventDisplaySelectionPanel eventSelPanel = new EventDisplaySelectionPanel();
		EventDisplayPanel eventDisplay = new EventDisplayPanel(calendar, 500, 500);
		eventSelPanel.getDayButton().addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				eventDisplay.setFilterType(EventDisplayPanel.FilterType.Day);
			}
		});
		eventSelPanel.getWeekButton().addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				eventDisplay.setFilterType(EventDisplayPanel.FilterType.Week);
			}
		});
		eventSelPanel.getMonthButton().addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				eventDisplay.setFilterType(EventDisplayPanel.FilterType.Month);
			}
		});
		eventSelPanel.getAgendaButton().addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				AgendaDialog.ShowDialog(calendar);
				eventDisplay.setFilterType(EventDisplayPanel.FilterType.Agenda);
			}
		});
		eventSelPanel.getFromFileButton().addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				try{
					calendar.loadEvent();
					JOptionPane.showMessageDialog(null, "File found and Events loaded.\n"
							+ "Click Day, Week, Month, Agenda to view loaded events.", 
							"Success", JOptionPane.INFORMATION_MESSAGE);
				}catch(Exception e){
					JOptionPane.showMessageDialog(null, "Error found while loading file and events.\n"
							+ "Try examining events.txt file and fix errors.", "Input Error",
							JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		rightPanel.add(eventSelPanel);
		rightPanel.add(eventDisplay);

		
		frame.add(leftPanel);
		frame.add(rightPanel);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}
