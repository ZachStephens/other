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

public class star extends GameObject{
	
	private static Image starImage = Toolkit.getDefaultToolkit().getImage("res/star.png");

	public star(float x, float y, ObjectId id) {
		super(x, y, id);
	}

	
	public void tick(LinkedList<GameObject> object, Handler handler) {
			//this.x = this.ix - Game.scrollposition;
	}

	
	public void render(Graphics g) {
		//g.setColor(Color.red);
		//g.fillRect((int)x, (int)y, 60, 60);
		g.drawImage(starImage, (int)x-(int)Game.scrollposition, (int)y, null);
		
	}
	
	public Rectangle getBounds(){
		return new Rectangle((int)x,(int)y,60,60);
	}

}


