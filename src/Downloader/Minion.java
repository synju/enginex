package Downloader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class Minion {
	private static final int	BUFFER_SIZE			= 4096;
	boolean										complete				= false;
	boolean										displayDetails	= false;
	boolean										displayProgress	= true;
	String										address					= "";
	float											progress				= 0;
	float											currentSize			= 0;
	float											totalSize				= 0;
	
	void download(String address, String saveLocation) {
		new Thread(()-> {
			// Format Address, remove stuff...
			this.address = address.replace("%20", " ");
			
			// URL Part A
			String url = address.substring(0, address.indexOf("s=") + 2);
			
			// URL Part B - Encoded... (Necessary)
			String params = encodeParams(address.substring(address.lastIndexOf("s=") + 2));
			
			// Proper Address
			this.address = url + params;
			
			// Download File
			startDownload(this.address, saveLocation);
			
			// Complete
			complete = true;
		}).start();
	}
	
	void startDownload(String fileURL, String saveDir) {
		try {
			URL url = new URL(fileURL);
			HttpURLConnection httpConn = (HttpURLConnection)url.openConnection();
			int responseCode = httpConn.getResponseCode();
			
			// always check HTTP response code first
			if(responseCode == HttpURLConnection.HTTP_OK) {
				String fileName = "";
				String disposition = httpConn.getHeaderField("Content-Disposition");
				String contentType = httpConn.getContentType();
				int contentLength = httpConn.getContentLength();
				totalSize = getSize(contentLength);
				
				if(disposition != null) {
					// extracts file name from header field
					int index = disposition.indexOf("filename=");
					if(index > 0) {
						fileName = disposition.substring(index + 10, disposition.length() - 1);
					}
				}
				else {
					// extracts file name from URL
					fileName = fileURL.substring(fileURL.lastIndexOf("/") + 1, fileURL.length());
				}
				
				if(displayDetails) {
					System.out.println("Content-Type = " + contentType);
					System.out.println("Content-Disposition = " + disposition);
					System.out.println("Content-Length = " + contentLength);
					System.out.println("fileName = " + fileName);
				}
				
				// opens input stream from the HTTP connection
				InputStream inputStream = httpConn.getInputStream();
				String saveFilePath = saveDir + File.separator + fileName;
				
				// opens an output stream to save into file
				FileOutputStream outputStream = new FileOutputStream(saveFilePath);
				
				int bytesRead = -1;
				byte[] buffer = new byte[BUFFER_SIZE];
				while((bytesRead = inputStream.read(buffer)) != -1) {
					outputStream.write(buffer, 0, bytesRead);
					currentSize = getSize(outputStream.getChannel().size());
					updateProgress();
				}
				
				outputStream.close();
				inputStream.close();
				
				if(displayDetails)
					System.out.println("File downloaded");
			}
			else {
				System.out.println("No file to download. Server replied HTTP code: " + responseCode);
			}
			httpConn.disconnect();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	void updateProgress() {
		progress = ((100 / totalSize) * currentSize);
		displayProgress();
	}
	
	void displayProgress() {
		if(displayProgress)
			System.out.println("Download Progress: " + String.format("%.2f", progress) + "%");
	}
	
	String encodeParams(String data) {
		String v = null;
		
		try {
			v = URLEncoder.encode(data, "UTF-8");
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return v;
	}
	
	public String getHumanFriendlySize(long bytes, boolean si) {
		int unit = si ? 1000 : 1024;
		if(bytes < unit)
			return bytes + " B";
		int exp = (int)(Math.log(bytes) / Math.log(unit));
		String pre = (si ? "kMGTPE" : "KMGTPE").charAt(exp - 1) + (si ? "" : "i");
		return String.format("%.1f %sB", bytes / Math.pow(unit, exp), pre);
	}
	
	public float getSize(long bytes) {
		boolean si = true;
		int unit = si ? 1000 : 1024;
		int exp = (int)(Math.log(bytes) / Math.log(unit));
		String v = String.format("%.1f", bytes / Math.pow(unit, exp));
		float f = Float.parseFloat(v);
		return f;
	}
}
