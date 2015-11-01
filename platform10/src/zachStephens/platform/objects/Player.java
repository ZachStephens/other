package zachStephens.platform.objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.util.LinkedList;

import zachStephens.platform.gameitems.GameObject;
import zachStephens.platform.gameitems.ObjectId;
import zachStephens.platform.window.Game;
import zachStephens.platform.window.Handler;
import zachStephens.platform.window.HealthUnit;
import zachStephens.platform.window.SimpleSoundPlayer;

public class Player extends characters {

	public float movex = 0;
	private float width = Game.WIDTH/24, height = Game.HEIGHT/8;
	public boolean right;
	public boolean left;
	public boolean shooting;
	public boolean facing; //false for left true for right
	public boolean blockleft;
	public boolean blockright;
	private GameObject tempObject;
	
	//for increasing health with time/movement
	public int lastxhealthadd = 0;
	public long motionlessStart;
	
	public int continuousShots = 0;
	public boolean shootingCooldown = false;
	public long cooldownStart;
	
	//Gas block effect
	public boolean cantshoot = false;
	public float cantshootstartx;
	public long cantshootstart;
	
	//star effect
	public boolean invincible = false;
	public float invinciblestartx;
	public long invinciblestart;
	
	//win
	public boolean won = false;
	
	
	public boolean jumped = false;
	public long startS;
	private SimpleSoundPlayer explodesound;
	private SimpleSoundPlayer power;
	
	private static Image PlayerImage = Toolkit.getDefaultToolkit().getImage("res/player.png");
	private static Image PlayerImageR = Toolkit.getDefaultToolkit().getImage("res/playerR.png");
	private static Image PlayerImageG = Toolkit.getDefaultToolkit().getImage("res/playerG.png");
	private static Image PlayerImageRG = Toolkit.getDefaultToolkit().getImage("res/playerRG.png");
	private static Image PlayerImageI = Toolkit.getDefaultToolkit().getImage("res/playerI.png");
	private static Image PlayerImageRI = Toolkit.getDefaultToolkit().getImage("res/playerRI.png");
	public static SimpleSoundPlayer shot = new SimpleSoundPlayer("res/100465__awesomemann1234__silenced-shot.wav");
	

	public Player(float x, float y, ObjectId id) {
		super(x, y, id);
		this.fall = true;
		this.health  = 20;
		facing = true;
		
		blockleft = false;
		blockright = false;
		
		
	}

public void tick(LinkedList<GameObject> object, Handler handler) {
		
		//velx = movex;
		vely+=gravity;
		
		//System.out.println(this.x);
		blockleft = false;
		blockright = false;
		for(int i = 0; i< object.size(); i++){
			tempObject= object.get(i);
			//goal 
			if(tempObject.getId() == ObjectId.goal){
				goal tempgoal = (goal) tempObject;
				if(this.getBoundsBottom().intersects(tempgoal.getBounds()) || this.getBoundsTop().intersects(tempgoal.getBounds()) || this.getBoundsLeft().intersects(tempgoal.getBounds()) || this.getBoundsRight().intersects(tempgoal.getBounds())){				
					//win
					won = true;
				}
			}
			//star
			if(tempObject.getId() == ObjectId.star){
				star tempstar = (star) tempObject;
				if(this.getBoundsBottom().intersects(tempstar.getBounds()) || this.getBoundsTop().intersects(tempstar.getBounds()) || this.getBoundsLeft().intersects(tempstar.getBounds()) || this.getBoundsRight().intersects(tempstar.getBounds())){
					
					object.remove(tempObject);
					invincible = true;
					cantshoot = false;
					invinciblestart = System.currentTimeMillis();
					invinciblestartx = x;
					power = new SimpleSoundPlayer("res/power.wav");
					power.play();

				}
			}
			//mushroom
			if(tempObject.getId() == ObjectId.mushroom){
				mushroom tempmushroom = (mushroom) tempObject;
				if(this.getBoundsBottom().intersects(tempmushroom.getBounds()) || this.getBoundsTop().intersects(tempmushroom.getBounds()) || this.getBoundsLeft().intersects(tempmushroom.getBounds()) || this.getBoundsRight().intersects(tempmushroom.getBounds())){
					HealthUnit.health+=5;
					object.remove(tempObject);
					
				}
			}
			//check Gasblock
			if(tempObject.getId() == ObjectId.GasBlock){
				Block tempGasblock = (GasBlock) tempObject;
				if(!invincible  &&  this.getBoundsBottom().intersects(tempGasblock.getBounds()) || this.getBoundsTop().intersects(tempGasblock.getBounds()) || this.getBoundsLeft().intersects(tempGasblock.getBounds()) || this.getBoundsRight().intersects(tempGasblock.getBounds())){
					//hit GasBlock
					cantshoot = true;
					cantshootstart = System.currentTimeMillis();
					cantshootstartx = x;
				}
			}
			//check explosive Block
			if(tempObject.getId() == ObjectId.ExplodingBlock){
				Block tempExplodingblock = (ExplodingBlock) tempObject;
				if(this.getBoundsBottom().intersects(tempExplodingblock.getBounds()) || this.getBoundsTop().intersects(tempExplodingblock.getBounds()) || this.getBoundsLeft().intersects(tempExplodingblock.getBounds()) || this.getBoundsRight().intersects(tempExplodingblock.getBounds())){
					//hit ExplodingBlock
					explodesound = new SimpleSoundPlayer("res/explode.wav");
					explodesound.play();
					this.vely = (tempExplodingblock.y - this.y)/10.0f; //Explosion blasts player away
					//this.velx = (tempExplodingblock.x - this.x)/10.0f;
					if(!invincible){HealthUnit.health-=10;}
					object.remove(tempObject);
				}
			}
			//check general Block
			if(tempObject.getId() == ObjectId.Block || tempObject.getId() == ObjectId.GasBlock || tempObject.getId() == ObjectId.ExplodingBlock ){
				
				Block tempblock = (Block) tempObject;
				if(this.getBoundsBottom().intersects(tempblock.getBounds())){
					//System.out.println("Block ID");
					if(this.getVely()>0){
					this.setVely(0); this.setY((int)(1+tempblock.getBounds().y-tempblock.getBounds().getHeight()));
					jumped = false;
					}
				}
				if(this.getBoundsTop().intersects(tempblock.getBounds())){
					//System.out.println("Block ID");
					if(this.getVely()<0){this.setVely(0); this.setY((int)(tempblock.getBounds().y+tempblock.getBounds().getHeight()));}
				}
				if(!blockright){
					if(this.getBoundsRight().intersects(tempblock.getBounds())){
						//System.out.println("Block ID");
						if(this.getVelx()>0){blockright = true; this.setX((int)(-1+tempblock.getBounds().x-tempblock.getBounds().getWidth()));}
					}
				}
				if(!blockleft){
					if(this.getBoundsLeft().intersects(tempblock.getBounds())){
						//System.out.println("Block ID");
						if(this.getVelx()<0){blockleft = true; this.setX((int)(1+tempblock.getBounds().x+tempblock.getBounds().getWidth()));}
					}
				}
			}
			if(tempObject.getId() == ObjectId.Creature){
				Creature tempCreature = (Creature) tempObject;
				if(tempCreature.activated){
					if(this.getBoundsBottom().intersects(tempCreature.getBoundsTop())){
						//kill creature
						object.remove(tempObject);
						this.vely = -12;
					}
					if(this.getBoundsLeft().intersects(tempCreature.getBoundsRight())){
						if(!invincible){HealthUnit.health=0;}
						else{object.remove(tempObject);}
						/*HealthUnit.health--;
						if(HealthUnit.health < 1 ){						
							this.velx += 30;
						}
						else{
							this.velx = 5;
							this.vely = -3;
							}*/
					}
					if(this.getBoundsRight().intersects(tempCreature.getBoundsLeft())){
						//game over
						if(!invincible){HealthUnit.health=0;}
						else{object.remove(tempObject);}
						/*HealthUnit.health--;
						if(HealthUnit.health < 1){
							this.velx += 20;
						}
						else{
							this.velx = -5;
							this.vely = -1;
							}*/
					}
					if(this.getBoundsTop().intersects(tempCreature.getBoundsBottom())){
						//game over
						if(!invincible){HealthUnit.health=0;}
						else{object.remove(tempObject);}
					}
				}		
			}
			
			//enemy bullet
			if(tempObject.getId() == ObjectId.EnemyBullet){
				//play sound
				EnemyBullet bullet = (EnemyBullet) tempObject;
				if(this.getBoundsRight().intersects(bullet.getBounds())  ||  this.getBoundsLeft().intersects(bullet.getBounds())){
					if(!invincible){HealthUnit.health-=5;}
					bullet.dead = true;
				}
		
			}
		}
		
		double prevx = x;
		x += velx ;
		y += vely;
		if(x < 0){
			x = 0;
		}
		//Health regenerating
		if(Math.abs(x - lastxhealthadd) > 60 && HealthUnit.health < 40){
			HealthUnit.health++;
			lastxhealthadd = (int)(x);
		}
		if(Math.abs(velx) < 0.0001 && Math.abs(velx) < 0.0001){
			if(System.currentTimeMillis() - motionlessStart > 1500){
				if(HealthUnit.health<36){HealthUnit.health+=5;}else{HealthUnit.health=40;}
				motionlessStart = System.currentTimeMillis();
			}
		}else{
			motionlessStart = System.currentTimeMillis();
		}
		
		//gasblock
		if(cantshoot && (System.currentTimeMillis() - cantshootstart > 3000  ||  Math.abs(x-cantshootstartx) > 60*10)){
			cantshoot = false;
		}
		//star
		if(invincible && (System.currentTimeMillis() - invinciblestart > 3000  ||  Math.abs(x-invinciblestartx) > 60*10)){
			invincible = false;
		}
		
		if(x - Game.scrollposition > 400)
		{
			this.right = true;
			Game.scrollposition+=(x - Game.scrollposition -400);
			//x-=5;
		}
		else if(x - Game.scrollposition < 150){
			this.left = true;
			Game.scrollposition-=(150 - (x - Game.scrollposition));
			if(Game.scrollposition < 0){
				Game.scrollposition = 0;
			}
			//x+=5;
		}
		else
		{
			this.right = false;
			this.left = false;
		}
		
		if(y > Game.HEIGHT){
			HealthUnit.health=0;
		}
		
		
		//shooting cooldown
		if(System.currentTimeMillis() - cooldownStart > 1500){
			shootingCooldown = false;
		}

	}


	public void render(Graphics g) {
		//g.setColor(Color.BLUE);
		//g.fillRect((int)x,(int)y, (int)width, (int)height);
		
		
		if(facing)
			if(invincible){
				g.drawImage(PlayerImageI, (int)x -(int)Game.scrollposition, (int)y, null);
			}else if(cantshoot){
				g.drawImage(PlayerImageG, (int)x -(int)Game.scrollposition, (int)y, null);
			}else{
				g.drawImage(PlayerImage, (int)x -(int)Game.scrollposition, (int)y, null);
			}
		else{
			if(invincible){
				g.drawImage(PlayerImageI, (int)x -(int)Game.scrollposition, (int)y, null);
			}else if(cantshoot){
				g.drawImage(PlayerImageRG, (int)x -(int)Game.scrollposition, (int)y, null);
			}else{
				g.drawImage(PlayerImageR, (int)x -(int)Game.scrollposition, (int)y, null);
			}
		}
		
		/*Graphics2D g2d = (Graphics2D) g;
		//g.setColor(Color.red);
		//g2d.draw(getBoundsRight());
		g2d.draw(new Rectangle(getBoundsRight().x -(int)Game.scrollposition, getBoundsRight().y, getBoundsRight().width, getBoundsRight().height));
		g2d.draw(new Rectangle(getBoundsLeft().x -(int)Game.scrollposition, getBoundsLeft().y, getBoundsLeft().width, getBoundsLeft().height));
		g2d.draw(new Rectangle(getBoundsTop().x -(int)Game.scrollposition, getBoundsTop().y, getBoundsTop().width, getBoundsTop().height));
		g2d.draw(new Rectangle(getBoundsBottom().x -(int)Game.scrollposition, getBoundsBottom().y, getBoundsBottom().width, getBoundsBottom().height));
		*/
		
			
	}
	
	
	public Rectangle getBoundsBottom(){
		return new Rectangle((int)x+5,(int)y+45, 45, 15);
	}
	public Rectangle getBoundsRight(){
		return new Rectangle((int)x+40,(int)y+5, 15, 50);
	}
	public Rectangle getBoundsLeft(){
		return new Rectangle((int)x,(int)y+5, 15, 50);
	}
	public Rectangle getBoundsTop(){
		return new Rectangle((int)x+5,(int)y, 45, 15);
	}


	

	

}
