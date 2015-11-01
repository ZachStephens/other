package zachStephens.platform.objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.util.LinkedList;

import zachStephens.platform.gameitems.GameObject;
import zachStephens.platform.gameitems.ObjectId;
import zachStephens.platform.window.Game;
import zachStephens.platform.window.Handler;

public class goal extends GameObject{
	
	private static Image goalImage = Toolkit.getDefaultToolkit().getImage("res/goal.png");

	public goal(float x, float y, ObjectId id) {
		super(x, y, id);
	}

	
	public void tick(LinkedList<GameObject> object, Handler handler) {
			//this.x = this.ix - Game.scrollposition;
	}

	
	public void render(Graphics g) {
		//g.setColor(Color.red);
		//g.fillRect((int)x, (int)y, 60, 60);
		g.drawImage(goalImage, (int)x-(int)Game.scrollposition, (int)y, null);
		
	}
	
	public Rectangle getBounds(){
		return new Rectangle((int)x,(int)y,60,60);
	}

}


