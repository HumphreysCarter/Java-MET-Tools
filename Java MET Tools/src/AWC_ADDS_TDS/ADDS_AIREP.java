package AWC_ADDS_TDS;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.Date;
import java.util.Scanner;

import Utilities.Utility;

public class ADDS_AIREP {

	private URL dataURL;

	public ADDS_AIREP(int r, double hoursBeforeNow, boolean mostRecent) {
		try {
			dataURL = new URL(
					"https://www.aviationweather.gov/adds/dataserver_current/httpparam?dataSource=aircraftreports&requestType=retrieve&format=xml&hoursBeforeNow="
							+ hoursBeforeNow + "&mostRecent=" + mostRecent + "&radialDistance=" + r + ";-76.1,43.12");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	public ADDS_AIREP(int r, long startTime, long endTime, boolean mostRecent) {
		try {
			dataURL = new URL(
					"https://www.aviationweather.gov/adds/dataserver_current/httpparam?dataSource=aircraftreports&requestType=retrieve&format=xml&startTime="
							+ startTime + "&endTime=" + endTime + "&mostRecent=" + mostRecent + "&radialDistance=" + r + ";-76.1,43.12");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	public void ingestNewData() {
		if (Utility.pingServer(dataURL)) {
			File dataFile = downloadDataFile(dataURL);
			if (dataFile != null) {
				parseDataFile(dataFile);
			}
		} else {
			// TODO Server Connection Not Established
		}

	}

	private File downloadDataFile(URL dataLink) {
		try {
			String dataFile = "airep.txt";
			URLConnection hc = dataLink.openConnection();

			ReadableByteChannel rbc = Channels.newChannel(hc.getInputStream());
			FileOutputStream fos = new FileOutputStream(dataFile);

			fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);

			return new File(dataFile);

		} catch (IOException e) {
			return null;
		}
	}

	private void parseDataFile(File dataFile) {
		try {
			Scanner file = new Scanner(dataFile);

			boolean isForecastData = false;
			Date d = new Date();
			double s = 0;
			double f = 0;

			while (file.hasNextLine()) {
				String line = file.nextLine();

				if (line.contains("<raw_text>")) {
					line = line.replaceAll("      <raw_text>", "");
					line = line.replaceAll("</raw_text>", "");

					System.out.println(line);

				} else if (line.contains("<!--- start forecast table --->")) {
					isForecastData = true;
				}
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

}
