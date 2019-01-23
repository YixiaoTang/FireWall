package firewall;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class ReadCSV {
	/**
	 * @param fileName, csv format
	 * @return List of String array, each array contains 1 rule with 4 string.
	 */
	public static List<String[]> readCSV(String fileName) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(fileName));
		List<String[]> lines = new ArrayList<>();
		String line = null;
		while ((line = reader.readLine()) != null) {
			lines.add(line.split(Pattern.quote(",")));
		}
		return lines;
	}
}
