package zachStephens.platform.window;
import java.io.*;
import javax.sound.sampled.*;

/**
    The SimpleSoundPlayer encapsulates a sound that can be opened
    from the file system and later played.
*/
public class SimpleSoundPlayer{


    private AudioFormat format;
    private Clip clip;

    /**
        Opens a sound from a file.
    */
    public SimpleSoundPlayer(String filename) {
        try {
            // open the audio input stream
            AudioInputStream stream =
                AudioSystem.getAudioInputStream(
                new File(filename));

            format = stream.getFormat();

            // get the audio samples
            try {
				clip = AudioSystem.getClip();
				clip.open(stream);
			} catch (LineUnavailableException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            
        }
        catch (UnsupportedAudioFileException ex) {
            ex.printStackTrace();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    
    public void play(){
    	if(clip == null) return;
    	stop();
    	clip.setFramePosition(0);
    	clip.start();
    }
    
    
    
    public void stop(){
    	if (clip.isRunning()) clip.stop();
    }
    
    public void close(){
    	stop();
    	clip.close();
    	
    }
    
    


}
