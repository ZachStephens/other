import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.LinkedList;

import zachStephens.platform.gameitems.GameObject;
import zachStephens.platform.gameitems.ObjectId;
import zachStephens.platform.window.Game;
import zachStephens.platform.window.Handler;

public class HealthUnit extends GameObject {
	
	private static Image HeartImage = Toolkit.getDefaultToolkit().getImage("res/Heart.png");
	public static int health;
	
	
	public HealthUnit(float x, float y, ObjectId id) {
		super(x, y, id);
		health = 20;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void tick(LinkedList<GameObject> object, Handler handler) {
		
	}

	@Override
	public void render(Graphics g) {
		for(int i = 0;i < health; i++){
			g.drawImage(HeartImage,10+i*10 , Game.HEIGHT/10, null);
		}
	}

}
