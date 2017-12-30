package breakout;
import javax.swing.*;

public class BreakOut extends JFrame{

	public BreakOut() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(556,579);
		add(new Board());
		setTitle("BreakOut");
		setLocationRelativeTo(null);
		setResizable(false);
	}

	public static void main(String[] args) {
		BreakOut b = new BreakOut();
		b.setVisible(true);

	}

}
