package enginex;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class FileManager {
	// Single Line Save & Load
	void saveSingle(String filename, String content) {
		try {
			Files.write(Paths.get(filename), content.getBytes(), StandardOpenOption.CREATE);
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}

	String loadSingle(String fileName) {
		String content = null;
		try {
			content = new String(Files.readAllBytes(Paths.get(fileName)));
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		return content;
	}

	// Multiline Save & Load
	public void save(String filename, List<String> content) {
		try {
			Files.write(Paths.get(filename), content, StandardOpenOption.CREATE);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	public List<String> load(String fileName) {
		List<String> lines = null;
		try {
			lines = Files.readAllLines(Paths.get(fileName));
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return lines;
	}

	// Conversion Methods
	// Integer To String
	public String toString(int v) {
		return Integer.toString(v);
	}

	// Float To String
	public String toString(float v) {
		return Float.toString(v);
	}

	// Double To String
	public String toString(double v) {
		return Double.toString(v);
	}

	// Long To String
	public String toString(Long v) {
		return Long.toString(v);
	}

	// String To Integer
	public Integer toInt(String v) {
		return Integer.parseInt(v);
	}

	// String To Float
	public Float toFloat(String v) {
		return Float.parseFloat(v);
	}

	// String To Double
	public Double toDouble(String v) {
		return Double.parseDouble(v);
	}

	// String To Long
	public Long toLong(String v) {
		return Long.parseLong(v);
	}
}
