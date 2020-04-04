package Utilities;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utility {

	//
	// Date Tools
	public static Date stringToDate(String date, String format) {
		try {
			return new SimpleDateFormat(format).parse(date);
		} catch (Exception e) {
			return null;
		}
	}

	public static String dateToString(Date date, String format) {
		DateFormat dateFormat = new SimpleDateFormat(format);
		return dateFormat.format(date);
	}
	
	public static Date addSecondsToDate(Date d1, long seconds) {
		return new Date(d1.getTime() - (1000 * seconds));
	}
	
	public static Date addMinutesToDate(Date d1, long minutes) {
		return new Date(d1.getTime() - (1000 * 60 * minutes));
	}
	
	public static Date addHoursToDate(Date d1, long hours) {
		return new Date(d1.getTime() - (1000 * 60 * 60 * hours));
	}
	
	public static Date addDaysToDate(Date d1, long days) {
		return new Date(d1.getTime() - (1000 * 60 * 60 * 24 * days));
	}


	//
	// Number Tools

	public static double round(double number, int precision) {
		number = Math.round(number * (Math.pow(10, precision)));
		return number / (Math.pow(10, precision));
	}

	//
	// Internet Tools
	public static boolean pingServer(String url) {
		url = url.replaceFirst("^https", "http"); // Otherwise an exception may be thrown on invalid SSL certificates.

		try {
			HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
			connection.setConnectTimeout(5000);
			connection.setReadTimeout(5000);
			connection.setRequestMethod("HEAD");
			int responseCode = connection.getResponseCode();
			if (200 <= responseCode && responseCode <= 399) {
				return true;
			} else {
				return false;
			}
		} catch (IOException exception) {
			return false;
		}
	}

	public static boolean pingServer(URL url) {
		try {
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setConnectTimeout(5000);
			connection.setReadTimeout(5000);
			connection.setRequestMethod("HEAD");
			int responseCode = connection.getResponseCode();
			if (200 <= responseCode && responseCode <= 399) {
				return true;
			} else {
				return false;
			}
		} catch (IOException exception) {
			return false;
		}
	}

}
