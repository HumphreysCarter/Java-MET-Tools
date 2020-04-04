package CAP_Alerts;

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

public class nws_alerts {

	
	private URL dataURL;
	
	public nws_alerts(String zone) {
		try {
			dataURL = new URL(
					"https://alerts.weather.gov/cap/wwaatmget.php?x=" + zone + "&y=0");
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
			String dataFile = "metar.txt";
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

				if (line.contains("<cap:event>")) {
					line = line.replaceAll("<cap:event>", "");
					line = line.replaceAll("</cap:event>", "");

					System.out.println(line);
				} else if (line.contains("<cap:effective>")) {
					line = line.replaceAll("<cap:effective>", "");
					line = line.replaceAll("</cap:effective>", "");

					System.out.println(line);
				} else if (line.contains("<cap:expires>")) {
					line = line.replaceAll("<cap:expires>", "");
					line = line.replaceAll("</cap:expires>", "");

					System.out.println(line);
				}
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

} 