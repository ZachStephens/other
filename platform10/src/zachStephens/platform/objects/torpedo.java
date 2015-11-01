package zachStephens.platform.objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.util.LinkedList;

import zachStephens.platform.gameitems.GameObject;
import zachStephens.platform.gameitems.ObjectId;
import zachStephens.platform.window.Game;
import zachStephens.platform.window.Handler;
import zachStephens.platform.window.SimpleSoundPlayer;

public class torpedo extends GameObject {
	
	private static Image TorpedoImage = Toolkit.getDefaultToolkit().getImage("res/DOT.png");
	private boolean right;

	private SimpleSoundPlayer shootsound;
	
	public torpedo(float x, float y, ObjectId id, boolean direction) {
		super(x, y, id);
		// TODO Auto-generated constructor stub
		
		this.x = x;
		this.y = y;
		this.id = id;
		this.right = direction; 
		shootsound = new SimpleSoundPlayer("res/shoot.wav");
		shootsound.play();
		
	}

	@Override
	public void tick(LinkedList<GameObject> object, Handler handler) {
		
		if(right){
			this.x += 12;
		}else
			this.x -= 12;
		if(Math.abs(Handler.getPlayer().x - this.x) > 800)
			this.dead = true;
		
		// TODO Auto-generated method stub
	}

	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub
		g.drawImage(TorpedoImage, (int)x-(int)Game.scrollposition, (int)y, null);
		Graphics2D g2d = (Graphics2D) g;
		//g.setColor(Color.red);
		//g2d.draw(getBounds());
		
	}
	public Rectangle getBounds(){
		return new Rectangle((int)x,(int)y,20,20);		
	}

}
