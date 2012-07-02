package axel;


import java.applet.Applet;
import java.applet.AudioClip;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;


/**
 *   constructor
 * 
 *        opens a file in order to play its sound clip
 */
public class Sound {

	
	private AudioClip music;
	
	
	public Sound(String Url) {
		File file = new File(Url);

		try {
			
			if (!file.exists()) {
				throw new FileNotFoundException() ;
			}
			URL url = file.toURI().toURL();
			music = Applet.newAudioClip(url);
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 *   loop
	 * 
	 *        loops a sound file constantly
	 */
	public void loop() {
		
				music.loop();
			
			
				
				
		}
	} 