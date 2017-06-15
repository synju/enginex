package enginex.core;

import java.io.File;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;

public class SoundEngine {
	public static void play(String path) {
		try {
			File soundFile = new File(path);
			if(!soundFile.exists()) throw new Exception("Wave file not found: " + path);
			
			AudioInputStream audioInputStream = null;
			audioInputStream = AudioSystem.getAudioInputStream(soundFile);
			
			AudioFormat format = audioInputStream.getFormat();
			SourceDataLine auline = null;
			DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
			
			auline = (SourceDataLine)AudioSystem.getLine(info);
			auline.open(format);
			auline.start();
			
			int nBytesRead = 0;
			byte[] abData = new byte[524288]; // 128Kb
			
			while(nBytesRead != -1) {
				nBytesRead = audioInputStream.read(abData, 0, abData.length);
				if(nBytesRead >= 0)
					auline.write(abData, 0, nBytesRead);
			}
			
			auline.drain();
			auline.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}
