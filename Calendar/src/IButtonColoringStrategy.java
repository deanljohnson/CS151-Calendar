import javax.swing.JButton;

/**
 * Interface for different strategies of coloring buttons
 * @author Three Amigos
 */

public interface IButtonColoringStrategy {
	public void color(JButton button, int date, CalendarWithEvents cal, boolean selected);
}
