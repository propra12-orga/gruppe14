package axel;


import java.applet.Applet;
import java.applet.AudioClip;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;


// opens a music file
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

	//loops the opened file 
	public void loop() {
		
				music.loop();
			
			
				
				
		}
	} 