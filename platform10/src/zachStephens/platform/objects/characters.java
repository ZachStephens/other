package zachStephens.platform.objects;

import java.awt.Rectangle;

import zachStephens.platform.gameitems.GameObject;
import zachStephens.platform.gameitems.ObjectId;
import zachStephens.platform.window.Game;

public abstract class characters extends GameObject {
	
	private float width = Game.WIDTH/24, height = Game.HEIGHT/8;

	
	protected float health;
	protected float attack;
	protected float defense;
	
	
	public characters(float x, float y, ObjectId id) {
		super(x, y, id);
		// TODO Auto-generated constructor stub		
	}
	
	public float getHealth() {
		return health;
	}

	public void setHealth(float health) {
		this.health = health;
	}

	public float getAttack() {
		return attack;
	}

	public void setAttack(float attack) {
		this.attack = attack;
	}

	public float getDefense() {
		return defense;
	}

	public void setDefense(float defense) {
		this.defense = defense;
	}

	public Rectangle getBoundsBottom(){
		return new Rectangle((int)x+(int)width/8,(int)y+(int)height/2, (int)width*3/4, (int)height/2);
	}
	public Rectangle getBoundsRight(){
		return new Rectangle((int)x+(int)width-(int)width/8,(int)y+(int)height/16, (int)width/8, (int)height*7/8);
	}
	public Rectangle getBoundsLeft(){
		return new Rectangle((int)x,(int)y+(int)height/16, (int)width/8, (int)height*7/8);
	}
	public Rectangle getBoundsTop(){
		return new Rectangle((int)x+(int)width/8,(int)y, (int)width*3/4, (int)height/2);
	}

}
