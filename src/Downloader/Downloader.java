package Downloader;

import java.util.ArrayList;

public class Downloader implements Runnable {
	boolean						complete		= false;
	boolean						downloading	= false;
	ArrayList<Minion>	minions			= new ArrayList<>();
	
	public static void main(String[] args) {
		Downloader downloader = new Downloader();
		downloader.run();
	}
	
	public void run() {
		Minion m = new Minion();
		m.download("https://3.bp.blogspot.com/N2ssmWcR3hCG4GKaDjGPxer1-u0vtOvib-HVbFHDuRkZvX7vNDW-Xm0YDBJBZ9-JfmzvVv4vIQ=m22?s=YQ/N0YVVXfRF6c6I2Ig=&type=video/mp4&title=[9anime.to]%20One%20Piece%20(Dub)%20-%20001%20-%20720p", "./");
	}
}
