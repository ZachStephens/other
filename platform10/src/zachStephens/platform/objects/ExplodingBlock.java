package zachStephens.platform.objects;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import zachStephens.platform.gameitems.ObjectId;
import zachStephens.platform.window.Game;

public class ExplodingBlock extends Block{

	private static Image ExplodingBlockImage = Toolkit.getDefaultToolkit().getImage("res/Explodingbrick.png");
	
	public ExplodingBlock(float x, float y, ObjectId id) {
		super(x, y, id);
	}
	
	public void render(Graphics g) {
		//g.setColor(Color.red);
		//g.fillRect((int)x, (int)y, 60, 60);
		g.drawImage(ExplodingBlockImage, (int)x-(int)Game.scrollposition, (int)y, null);
		
	}
}