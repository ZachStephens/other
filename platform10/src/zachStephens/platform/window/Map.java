
//Map gameMap = new Map("testmap.txt");
//gameMap.addMapToGame(handler);

package zachStephens.platform.window;
import zachStephens.platform.*;

import java.io.*;
import zachStephens.platform.gameitems.ObjectId;
import zachStephens.platform.objects.Block;
import zachStephens.platform.objects.Creature;
import zachStephens.platform.objects.ExplodingBlock;
import zachStephens.platform.objects.GasBlock;
import zachStephens.platform.objects.goal;
import zachStephens.platform.objects.mushroom;
import zachStephens.platform.objects.star;

public class Map {
	
	// 0 - Nothing
	// 1 - Normal Block
	// 2 - Exploding Block
	// 3 - Gas Block
	// 4 - Goal
	// 5 - Creature
	// 6 - star
	// 7 - mushroom
	
	public static int Xsize = 200;
	public static int Ysize = 10;
	
	public byte[][] MapArray = new byte[Xsize][Ysize];
	
	
    public Map(){
		initEmpty();
	}
	public Map(String fileName){
		
		try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader = 
                new FileReader(fileName);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader = 
                new BufferedReader(fileReader);
            String line;
            for(int y=0; y<Ysize; y++){
            	//read map line by line
            	if((line = bufferedReader.readLine()) != null){
            		//set appropriate block in the MapArray (fill with normal Blocks if file is to short)
            		for(int x=0; x<Xsize; x++){
            			
                		if(x < line.length()){
                			switch(line.charAt(x)){
                			case ' ': MapArray[x][y] = 0; break;
                			case 'O': MapArray[x][y] = 1; break;
                			case 'X': MapArray[x][y] = 2; break;
                			case 'G': MapArray[x][y] = 3; break;
                			case '@': MapArray[x][y] = 4; break;
                			case 'C': MapArray[x][y] = 5; break;
                			case 'S': MapArray[x][y] = 6; break;
                			case 'M': MapArray[x][y] = 7; break;
                			default: MapArray[x][y] = 0; break;
                			}
                		}
                		else{
                			MapArray[x][y] = 0;
                		}
            		}         		
            	}
            	else{
            		System.out.println("Error: Mapfile has not enough lines!");
            		initEmpty();
            	}
            }

            // Always close files.
            bufferedReader.close();         
        }
        catch(FileNotFoundException ex) {
            System.out.println("Unable to open file '" + fileName + "'");
            initEmpty();
            
        }
        catch(IOException ex) {
            System.out.println("Error reading file '"  + fileName + "'");   
            initEmpty();
        }
		
	}
	
	public void initEmpty(){
		
		for(int y=0; y<Ysize-1; y++){
    		for(int x=0; x<Xsize; x++){
    			MapArray[x][y] = 0;
    		}
    	}
    	for(int x=0; x<Xsize; x++){
    		MapArray[x][Ysize-1] = 1;
		}
    	
    	MapArray[Xsize-1][Ysize-1] = 4;
	
	}
	
	public void addMapToGame(Handler handler){
		
		for(int y=0; y<Ysize; y++){
    		for(int x=0; x<Xsize; x++){
    			switch(MapArray[x][y]){
    				case 1: handler.addObject(new Block(x*60,y*60,ObjectId.Block)); break;
    				case 2: handler.addObject(new ExplodingBlock(x*60,y*60,ObjectId.ExplodingBlock)); break;
    				case 3: handler.addObject(new GasBlock(x*60,y*60,ObjectId.GasBlock)); break;
    				case 4: handler.addObject(new goal(x*60,y*60,ObjectId.goal)); break;
    				case 5: handler.addObject(new Creature(x*60,y*60,ObjectId.Creature)); MapArray[x][y] = 0; break;
    				case 6: handler.addObject(new star(x*60,y*60,ObjectId.star)); break;
    				case 7: handler.addObject(new mushroom(x*60,y*60,ObjectId.mushroom)); break;
    				default: break;
    			}
    		}
    	}
	
	}
	

}
