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
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(path));			
			AudioFormat format = audioInputStream.getFormat();
			SourceDataLine auline = (SourceDataLine)AudioSystem.getLine(new DataLine.Info(SourceDataLine.class, format));
			
			auline.open(format);
			auline.start();
			
			int nBytesRead = 0;
			byte[] abData = new byte[524288*100]; // 128Kb
			
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
