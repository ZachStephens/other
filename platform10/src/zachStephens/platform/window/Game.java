package zachStephens.platform.window;

import java.awt.Canvas;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.util.Random;


import zachStephens.platform.gameitems.GameObject;
import zachStephens.platform.gameitems.KeyInput;
import zachStephens.platform.gameitems.ObjectId;
import zachStephens.platform.objects.Block;
import zachStephens.platform.objects.Player;
import zachStephens.platform.*;
public class Game extends Canvas implements Runnable {
	/**
	 * 
	 */

	static boolean running = false;
	private Thread thread;
	
	public static String input_args[];
	
	public static int WIDTH, HEIGHT;
	public static float scrollposition = 0;
	private static Image backgroundImage = Toolkit.getDefaultToolkit().getImage("res/mariobackground.png");
	
	
	Random rand  = new Random();
	Handler handler;
	private SimpleSoundPlayer music;
	public static boolean Done;
	
	private void init(){
		
		
		WIDTH = getWidth();
		HEIGHT = getHeight();
		System.out.println(WIDTH);
		handler = new Handler();
		handler.addObject(new Player(400,100,ObjectId.Player));
		handler.addObject(new HealthUnit(0,0,ObjectId.HealthUnit));
		
		if(input_args.length > 0){
			Map gameMap = new Map(input_args[0]);
			gameMap.addMapToGame(handler);
		}else
		{
			Map gameMap =new Map("");
			gameMap.addMapToGame(handler);
		}
		
		this.addKeyListener(new KeyInput(handler));
		music = new SimpleSoundPlayer("res/mariopacman.wav");
		music.play();

	}
	
	
	public void run() {
		init();
		this.requestFocus();
        long startTime = System.currentTimeMillis();
        long currTime = startTime;
        

        while (running) {
        	long FrameStartTime = System.currentTimeMillis();
            long elapsedTime =
                System.currentTimeMillis() - currTime;
            currTime += elapsedTime;

            // update
            tick();

            // draw the screen
            
            render();
     
            // don't take a nap! run as fast as possible
            long sleeptime = 15-(System.currentTimeMillis() - FrameStartTime);
            if(sleeptime > 0){
            	try {
            		//System.out.println((System.currentTimeMillis() - FrameStartTime));
            		Thread.sleep(sleeptime);
            	}
            	catch (InterruptedException ex) { }
            }
            if(Done == true) running = false;
            if(HealthUnit.health < 1  ||  handler.getPlayer().won){ 
            	render();
            }
            
        }
        
    }
	
	
	
	private void tick(){
		handler.tick();
	}
	
	private void render(){
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null){
			this.createBufferStrategy(2);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		//
		g.setColor(Color.black);
		g.fillRect(0,0,getWidth(),getHeight());
		g.drawImage(backgroundImage, 0, 5, null);
		
		
		handler.render(g);
		
		///
		g.dispose();
		bs.show();
			
	}
	
	
	
	public synchronized void start(){
		if (running)
			return;
		
		running = true;
		
		thread = new Thread(this);
		thread.start();
	}
	
	public static void main(String args[]){
		Game.input_args = args;
		new Window(800,600, "New Game",new Game());
	}
	
}
