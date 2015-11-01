package zachStephens.platform.window;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.util.LinkedList;
import zachStephens.platform.*;
import zachStephens.platform.gameitems.GameObject;
import zachStephens.platform.gameitems.ObjectId;
import zachStephens.platform.objects.Block;
import zachStephens.platform.objects.Creature;
import zachStephens.platform.objects.Player;
import zachStephens.platform.objects.characters;
import zachStephens.platform.objects.torpedo;


public class Handler {
	public LinkedList<GameObject> object = new LinkedList<GameObject>();
	public LinkedList<Creature> CreatureList = new LinkedList<Creature>();
	public LinkedList<torpedo> torpedoList = new LinkedList<torpedo>();
	public LinkedList<Block> BlockList = new LinkedList<Block>();
	public LinkedList<characters> charactersList = new LinkedList<characters>();
	private GameObject tempObject;
	public static Player p1;
	
	private static Image wastedImage = Toolkit.getDefaultToolkit().getImage("res/wasted.png");
	private static Image wonImage = Toolkit.getDefaultToolkit().getImage("res/won.png");
	
	public void tick(){
		
	for(int i = 0; i< object.size(); i++){
		tempObject= object.get(i);
		tempObject.tick(object, this);
		if(tempObject.dead){
			this.removeObject(tempObject);
		}
	}
	}

	public void render(Graphics g){		
		for(int i = 0; i< object.size(); i++){
			tempObject= object.get(i);
			tempObject.render(g);
			
		}
		if(getPlayer().won){
			renderwin(g);
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Game.Done = true;
		}
		else if(HealthUnit.health < 1 ){
			renderlast(g);
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Game.Done = true;
		}
	
	}
	
	private void renderlast(Graphics g){
		//System.out.println("drawing");
		g.drawImage(wastedImage, Game.WIDTH/2-wastedImage.getWidth(null)/2, Game.HEIGHT/2-wastedImage.getHeight(null)/2, null);
	}
	private void renderwin(Graphics g){
		//System.out.println("drawingwin");
		g.drawImage(wonImage, Game.WIDTH/2-wonImage.getWidth(null)/2, Game.HEIGHT/2-wonImage.getHeight(null)/2, null);
	}
	
	public static Player getPlayer(){
		return p1;
	}
	
	public void addObject(GameObject object){
		if(object.getId() == ObjectId.Player)
		{
			p1 = (Player) object;
		}
		this.object.add(object);
	}
	
	public void removeObject(GameObject object){
		this.object.remove(object);
	}
	

	
}
