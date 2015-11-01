package zachStephens.platform.gameitems;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.LinkedList;

import zachStephens.platform.window.Handler;

public abstract class GameObject {
	public float x,y;
	final protected float ix;
	protected float velx=0, vely;
	public boolean fall;
	public static float gravity = .45f;
	protected ObjectId id;
	public boolean dead = false;
	
	public GameObject(float x, float y, ObjectId id)
	{
		this.x = x;
		this.y = y;
		this.id = id;
		this.fall = false;
		this.ix = x;
	}
	
	public abstract void tick(LinkedList<GameObject> object, Handler handler);
	public abstract void render(Graphics g);
	
	public float getX(){return this.x;}
	public void setX(float x){this.x = x;}

	public float getY(){return this.y;};
	public void setY(float y){this.y = y;}
	
	public float getVelx(){return velx;}
	public void setVelx(float velx){this.velx = velx;}

	public float getVely(){return this.vely;}
	public void setVely(float vely){this.vely = vely;}
	
	public ObjectId getId(){return this.id;}

	
}
