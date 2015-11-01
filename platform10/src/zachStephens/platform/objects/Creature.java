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
import zachStephens.platform.window.HealthUnit;

public class Creature extends characters{
	
	private static float width = 30, height = 60;
	public boolean activated = false;
	private GameObject tempObject;
	private boolean shooting;
	private double shoottime;
	public long lastshot = 0;
	public long spottedPlayer;
	
	private static Image CreatureImage = Toolkit.getDefaultToolkit().getImage("res/creature.png");
	private static Image CreatureImageR = Toolkit.getDefaultToolkit().getImage("res/creatureR.png");

	public Creature(float x, float y, ObjectId id) {
		super(x, y, id);
		this.health  = 100;
		this.dead = false;
		shoottime = System.currentTimeMillis();
	}

	public void tick(LinkedList<GameObject> object, Handler handler) {
		
		//check if player is close, if so: activate
		if(!activated && (Math.abs(object.get(0).x - this.x) < 380)){
			activated = true;
			this.spottedPlayer = System.currentTimeMillis();
		}
		
		
		if(activated){
			//wait a little before shooting
			if((System.currentTimeMillis()-spottedPlayer > 1000  ||  (Math.abs(object.get(0).x - this.x) < 280))   &&  System.currentTimeMillis()-lastshot > 500)
			{
					if(Math.abs(Handler.p1.y - y )< 20){
						lastshot = System.currentTimeMillis();
						handler.addObject(new EnemyBullet(this.x+16, this.y+16, ObjectId.EnemyBullet, (object.get(0).x < this.x)?false:true));
					}
			}
			
			vely+=gravity;
			
			if(object.get(0).x < this.x){
				velx = -1.3f;
			}else{
				velx = 1.3f;
			}
		
		
		
			for(int i = 0; i< object.size(); i++){
				tempObject= object.get(i);
				if(tempObject.getId() == ObjectId.Block || tempObject.getId() == ObjectId.ExplodingBlock || tempObject.getId() == ObjectId.GasBlock){
				
					Block tempblock = (Block) tempObject;
					if(this.getBoundsBottom().intersects(tempblock.getBounds())){
						//System.out.println("Block ID");
						if(this.getVely()>0){this.setVely(0); this.setY((int)(1+tempblock.getBounds().y-tempblock.getBounds().getHeight()));}
					}
					if(this.getBoundsTop().intersects(tempblock.getBounds())){
						//System.out.println("Block ID");
						if(this.getVely()<0){this.setVely(0); this.setY((int)(tempblock.getBounds().y+tempblock.getBounds().getHeight()));}
					}
					if(this.getBoundsRight().intersects(tempblock.getBounds())){
						//System.out.println("Block ID");
						if(this.getVelx()>0){this.setVelx(0); this.setVely(-6); /*this.setY(this.getY()-2);*/  /*this.setX((int)(tempblock.getBounds().x-tempblock.getBounds().getWidth())+1);*/}
					}
					if(this.getBoundsLeft().intersects(tempblock.getBounds())){
						//System.out.println("Block ID");
						if(this.getVelx()<0){this.setVelx(0); this.setVely(-2); /*this.setY(this.getY()-2);*/ /*this.setX((int)(tempblock.getBounds().x+tempblock.getBounds().getWidth())-1);*/}
					}
				
								
				}
				
				if(tempObject.getId() == ObjectId.torpedo){
						//play sound
					torpedo tempedo = (torpedo) tempObject;
					if(this.getBoundsRight().intersects(tempedo.getBounds())){
						this.dead = true;
						tempedo.dead = true;
						HealthUnit.health+=10;
					}
				
				}
				
			}	
		
			double prevx = x;
			x += velx ;
			y += vely;
		}
		
		

	}


	public void render(Graphics g) {
		//g.setColor(Color.BLUE);
		//g.fillRect((int)x,(int)y, (int)width, (int)height);
		
		
		if(Handler.p1.x < this.x){
			g.drawImage(CreatureImage, (int)x -(int)Game.scrollposition, (int)y, null);
		}else{
			g.drawImage(CreatureImageR, (int)x -(int)Game.scrollposition, (int)y, null);
		}
		Graphics2D g2d = (Graphics2D) g;
		g.setColor(Color.red);
		//g2d.draw(getBoundsRight());
		//g2d.draw(getBoundsLeft());
		//g2d.draw(getBoundsTop());
		//g2d.draw(getBoundsBottom());
		//g2d.draw(new Rectangle(getBoundsRight().x -(int)Game.scrollposition, getBoundsRight().y, getBoundsRight().width, getBoundsRight().height));
		//g2d.draw(new Rectangle(getBoundsLeft().x -(int)Game.scrollposition, getBoundsLeft().y, getBoundsLeft().width, getBoundsLeft().height));
		//g2d.draw(new Rectangle(getBoundsTop().x -(int)Game.scrollposition, getBoundsTop().y, getBoundsTop().width, getBoundsTop().height));
		//g2d.draw(new Rectangle(getBoundsBottom().x -(int)Game.scrollposition, getBoundsBottom().y, getBoundsBottom().width, getBoundsBottom().height));
	

	}
	
	public Rectangle getBoundsBottom(){
		return new Rectangle((int)x+10,(int)y+45, 40, 15);
	}
	public Rectangle getBoundsRight(){
		return new Rectangle((int)x+35,(int)y+5, 15, 50);
	}
	public Rectangle getBoundsLeft(){
		return new Rectangle((int)x+5,(int)y+5, 15, 50);
	}
	public Rectangle getBoundsTop(){
		return new Rectangle((int)x+10,(int)y, 40, 15);
	}


}
