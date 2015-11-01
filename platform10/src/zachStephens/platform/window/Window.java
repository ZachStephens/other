package zachStephens.platform.window;

import java.awt.Dimension;

import javax.swing.JFrame;

public class Window {

	public Window(double w,double h, String title, Game game){
		game.setPreferredSize(new Dimension((int)w,(int)h));
		game.setMaximumSize(new Dimension((int)w,(int)h));
		game.setMinimumSize(new Dimension((int)w,(int)h));
		
		JFrame frame = new JFrame(title);
		frame.add(game);
		frame.pack();
		frame.add(game);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		game.start();
		
		
	}
	
}
