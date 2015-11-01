package zachStephens.platform.objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.LinkedList;

import zachStephens.platform.gameitems.GameObject;
import zachStephens.platform.gameitems.ObjectId;
import zachStephens.platform.window.Handler;

public class Test extends GameObject{

	public Test(float x, float y, ObjectId id) {
		super(x, y, id);
	}

	
	public void tick(LinkedList<GameObject> object, Handler handler) {
		// TODO Auto-generated method stub
		
	}

	
	public void render(Graphics g) {
		g.setColor(Color.red);
		g.fillRect((int)x, (int)y, 32, 32);
		
	}
	public Rectangle getBounds(){
		return null;
	}

}
