import javax.swing.JButton;

public interface IButtonColoringStrategy {
	public void color(JButton button, int date, CalendarWithEvents cal, boolean selected);
}
