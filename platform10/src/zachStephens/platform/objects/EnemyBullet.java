package zachStephens.platform.objects;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;

import zachStephens.platform.gameitems.ObjectId;
import zachStephens.platform.window.Game;

public class EnemyBullet extends torpedo {
	
	private static Image TorpedoImageS = Toolkit.getDefaultToolkit().getImage("res/DOTs.png");

	public EnemyBullet(float x, float y, ObjectId id, boolean direction) {
		super(x, y, id, direction);
		this.x = x;
		this.y = y;
		this.id = id;
		// TODO Auto-generated constructor stub
	}
	
	
	public void render(Graphics g) {
		// TODO Auto-generated method stub
		g.drawImage(TorpedoImageS, (int)x-(int)Game.scrollposition, (int)y, null);
		Graphics2D g2d = (Graphics2D) g;
		//g.setColor(Color.red);
		//g2d.draw(getBounds());
		
	}
	public Rectangle getBounds(){
		return new Rectangle((int)x,(int)y,15,15);		
	}

}
