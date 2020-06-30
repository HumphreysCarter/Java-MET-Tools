package WSW_LES_Polygons;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import org.apache.commons.net.ftp.FTPClient;

import Utilities.GeographicCoordinate;
import Utilities.Utility;

public class PlacefileWriter {

	public static void main(String[] args) throws IOException {

		URL test1 = new URL("http://weather.carterhumphreys.com/placefiles/wswbgm.txt");
		URL test2 = new URL("http://weather.carterhumphreys.com/placefiles/wswbuf.txt");
		
		URL wswbbgm = new URL("ftp://tgftp.nws.noaa.gov/data/raw/ww/wwus41.kbgm.wsw.bgm.txt");
		URL wswbuf = new URL("https://tgftp.nws.noaa.gov/data/raw/ww/wwus41.kbuf.wsw.buf.txt");

		ArrayList<String> lineData = new ArrayList<String>();
		lineData = getData(test1, lineData);
		lineData = getData(test2, lineData);
		
		lineData = getData(wswbbgm, lineData);
		lineData = getData(wswbuf, lineData);

		ArrayList<GeographicCoordinate> coord = new ArrayList<GeographicCoordinate>();

		PrintWriter outputFile = new PrintWriter("src\\wsw_polygons_outline.txt");

		outputFile.println("");
		outputFile.println("Title: ***Experimental*** LES Polygons - Updated: " + Utility.dateToString(new Date(), "yyyy-MM-dd HH:mm:ss") + " Eastern");
		outputFile.println("RefreshSeconds: 5");
		outputFile.println("Color: 255 105 180");
		outputFile.println("");

		for (int i = 0; i < lineData.size(); i++) {
			if (lineData.get(i).contains("COORD") || lineData.get(i).contains("        ")) {
				String dataString = lineData.get(i);
				dataString = dataString.replaceAll("COORD...", "");
				dataString = dataString.replaceAll("        ", "");

				String[] newCoord = dataString.split(" ");

				for (int j = 0; j < (newCoord.length); j += 2) {
					double lat = Integer.parseInt(newCoord[j]);
					double lon = Integer.parseInt(newCoord[j + 1]);

					coord.add(new GeographicCoordinate(lat / 100.0, lon / -100.0));
				}

			} else if (lineData.get(i).contains("TIME")) {

				String startTime = convertDate(lineData.get(i).substring(5, 20));
				String endTime = convertDate(lineData.get(i).substring(21, 36));

				outputFile.println("TimeRange: " + startTime + " " + endTime);
				outputFile.println("Line: 4, 0, Winter Storm Warning for Lake-Effect Snow\\nExpires: " + endTime);
				for (int j = 0; j < coord.size(); j++) {
					outputFile.println("\t" + coord.get(j));
				}
				outputFile.println("\t" + coord.get(0));
				outputFile.println("End:");
				outputFile.println();

				coord.clear();
			}
		}

		outputFile.close();

		outputFile = new PrintWriter("src\\wsw_polygons_shaded.txt");

		outputFile.println("");
		outputFile.println("Title: ***Experimental*** LES Polygons (Shaded) - Updated: " + Utility.dateToString(new Date(), "yyyy-MM-dd HH:mm:ss") + " Eastern");
		outputFile.println("RefreshSeconds: 5");
		outputFile.println("");

		for (int i = 0; i < lineData.size(); i++) {
			if (lineData.get(i).contains("COORD") || lineData.get(i).contains("        ")) {
				String dataString = lineData.get(i);
				dataString = dataString.replaceAll("COORD...", "");
				dataString = dataString.replaceAll("        ", "");

				String[] newCoord = dataString.split(" ");

				for (int j = 0; j < (newCoord.length); j += 2) {
					double lat = Integer.parseInt(newCoord[j]);
					double lon = Integer.parseInt(newCoord[j + 1]);

					coord.add(new GeographicCoordinate(lat / 100.0, lon / -100.0));
				}

			} else if (lineData.get(i).contains("TIME")) {

				String startTime = convertDate(lineData.get(i).substring(5, 20));
				String endTime = convertDate(lineData.get(i).substring(21, 36));

				outputFile.println("TimeRange: " + startTime + " " + endTime);
				outputFile.println("Color: 255 105 180");
				outputFile.println("Polygon: 4, 0, ");
				for (int j = 0; j < coord.size(); j++) {
					outputFile.println("\t" + coord.get(j));
				}
				outputFile.println("End:");
				outputFile.println("Color: 255 105 180");
				outputFile.println("Line: 4, 0, Winter Storm Warning for Lake-Effect Snow\\nExpires: " + endTime);
				for (int j = 0; j < coord.size(); j++) {
					outputFile.println("\t" + coord.get(j));
				}
				outputFile.println("\t" + coord.get(0));
				outputFile.println("End:");
				outputFile.println();

				coord.clear();
			}
		}

		outputFile.close();

		uploadFile("wsw_polygons_outline.txt");
		uploadFile("wsw_polygons_shaded.txt");
	}

	private static void uploadFile(String file) {
		FTPClient client = new FTPClient();
		String host = "example.com";
		String user = "placefiles@example.com";
		String pswd = "password";

		// Read the file from resources folder.
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

		try (InputStream is = classLoader.getResourceAsStream(file)) {
			client.connect(host);
			client.login(user, pswd);
			client.storeFile(file, is);
			client.logout();

			System.out.println(file + " uploaded to " + host);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static ArrayList<String> getData(URL dataSource, ArrayList<String> lineData) throws IOException {
		Scanner reader = new Scanner(dataSource.openStream());

		boolean saveText = false;
		while (reader.hasNextLine()) {

			String line = reader.nextLine();

			if (line.contains("COORD")) {
				saveText = true;
			} else if (line.contains("$$")) {
				saveText = false;
			}

			if (saveText && !line.equals("")) {
				line = line.replace("", "");
				lineData.add(line);
			}
		}
		return lineData;
	}

	public static String convertDate(String date) {
		date = date.replace("Y", "");
		date = date.replace("M", "");
		date = date.replace("D", "");
		date = date.replace("T", "");
		date = date.replace("Z", "");

		Date startT = Utility.stringToDate(date, "yyMMddHHmm");
		return Utility.dateToString(startT, "yyyy-MM-dd") + "T" + Utility.dateToString(startT, "HH:mm:ss") + "Z";
	}

}
