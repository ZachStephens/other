package zachStephens.platform.gameitems;


import zachStephens.platform.objects.Player;
import zachStephens.platform.objects.torpedo;
import zachStephens.platform.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


import zachStephens.platform.window.Game;
import zachStephens.platform.window.Handler;
import zachStephens.platform.window.HealthUnit;
import zachStephens.platform.*;

public class KeyInput extends KeyAdapter {
	Handler handler;
	Player p1;
	private GameObject tempObject;
	
	public KeyInput(Handler handler){
		this.handler = handler;
		p1 = handler.getPlayer();
		
	}
	
	public void keyPressed(KeyEvent e){
		int key = e.getKeyCode();
		if(key == KeyEvent.VK_RIGHT) {
			if(!p1.blockright){
				p1.velx = 4.5f;
			}
			p1.facing = true;
		}
		if(key == KeyEvent.VK_LEFT) {
			if(!p1.blockleft){
				p1.velx = -4.5f;
			}
			p1.facing = false;
		}
		if(key == KeyEvent.VK_UP && !p1.jumped) {
			p1.jumped = true;
			p1.vely = -12;
		}
		if(key == KeyEvent.VK_DOWN) {
			p1.jumped = true;
			p1.vely += 5;
		}
		
		if(key == KeyEvent.VK_S  &&  !p1.cantshoot) {
			if(!p1.shooting){
				p1.shooting = true;
				p1.startS = System.currentTimeMillis();
				//p1.shot.start();
				handler.addObject(new torpedo(p1.x+16,p1.y+16, ObjectId.torpedo,p1.facing));
				HealthUnit.health--; //player looses health for every shot, so the game isn't to easy
			}
			else if(System.currentTimeMillis()-p1.startS >250){
				if(!p1.shootingCooldown){
					//p1.shot.start();
					handler.addObject(new torpedo(p1.x+16,p1.y+16, ObjectId.torpedo,p1.facing));
					HealthUnit.health--; //player looses health for every shot, so the game isn't to easy
					p1.startS = System.currentTimeMillis();
					p1.continuousShots++;
					if(p1.continuousShots == 10){
						p1.continuousShots = 0;
						p1.shootingCooldown = true;
						p1.cooldownStart = System.currentTimeMillis();
					}
				}
			}
		}
			
	
		
		if(key == KeyEvent.VK_ESCAPE)
			System.exit(1);
		
	}
		
	public void keyReleased(KeyEvent e){
		int key = e.getKeyCode();
		
		if(key == KeyEvent.VK_RIGHT){ 
			p1.velx =0;
		}

		if(key == KeyEvent.VK_LEFT) {
			p1.velx = 0;
		}
		if(key == KeyEvent.VK_S) 
			p1.shooting = false;

		
	}

	
	


}
