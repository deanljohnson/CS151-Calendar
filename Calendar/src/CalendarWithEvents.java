import java.util.ArrayList;
import java.util.GregorianCalendar;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class CalendarWithEvents extends GregorianCalendar {
	private ArrayList<ChangeListener> changeListeners = new ArrayList<ChangeListener>();
	
	public CalendarWithEvents(){	
		//TODO: Setup the events map and initialize the calendar	
	}
	
	public void addChangeListener(ChangeListener l){
		changeListeners.add(l);
	}
	
	@Override
	public void set(int field, int value){
		super.set(field, value);
		notifyOfChange();
	}
	
	private void notifyOfChange(){
		ChangeEvent ce = new ChangeEvent(this);
		for (ChangeListener l : changeListeners)
			l.stateChanged(ce);
	}
}
