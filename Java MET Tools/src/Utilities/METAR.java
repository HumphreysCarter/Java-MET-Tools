package Utilities;
public class METAR {

	private String metar;

	private String site;
	private String time;
	private int windDir;
	private int windSpd;
	private int windGst;
	private double vis_sm;
	private double vis_km;
	private String wx;
	private String clouds;
	private int tempC;
	private int tempF;
	private int dewpointC;
	private int dewpointF;
	private double altimeter;
	private double qnh;

	public METAR(String metar) {
		this.metar = metar;
		parseMETAR();
	}

	private void parseMETAR() {
		String[] metarData = metar.split(" ");

		site = metarData[0];
		time = metarData[1];

		String wind = metarData[2].replace("KT", "");
		if (wind.contains("G")) {
			windDir = Integer.parseInt(wind.substring(0, 3));
			windSpd = Integer.parseInt(wind.substring(3, wind.indexOf("G")));
			windGst = Integer.parseInt(wind.substring(wind.indexOf("G") + 1, wind.length()));
		} else {
			windDir = Integer.parseInt(wind.substring(0, 3));
			windSpd = Integer.parseInt(wind.substring(3, wind.length()));
		}
		
		if (metarData[3].contains("/")) {
			if (metarData[3].contains("SM")) {
				String[] visString = metarData[3].replace("SM", "").split("/");
				double n = Integer.parseInt(visString[0]);
				double d = Integer.parseInt(visString[1]);
				vis_sm = n / d;
				vis_km = UnitConverter.mi_to_km(vis_sm);
			} else if (metarData[3].contains("KM")) {
				String[] visString = metarData[3].replace("KM", "").split("/");
				double n = Integer.parseInt(visString[0]);
				double d = Integer.parseInt(visString[1]);
				vis_km = n / d;
				vis_sm = UnitConverter.km_to_mi(vis_km);
			}

		} else {
			if (metarData[3].contains("SM")) {
				vis_sm = Integer.parseInt(metarData[3].substring(0, metarData[3].length() - 2));
				vis_km = UnitConverter.mi_to_km(vis_sm);
			} else if (metarData[3].contains("KM")) {
				vis_km = Integer.parseInt(metarData[3].substring(0, metarData[3].length() - 2));
				vis_sm = UnitConverter.km_to_mi(vis_km);
			}
		}

		int tempStringi = 0;
		for (int i = 3; i < metarData.length; i++) {

			if (metarData[i].length() == 5 && metarData[i].charAt(0) == 'A') {
				tempStringi = i - 1;
				altimeter = Double.parseDouble(metarData[i].replace("A", "")) / 100;
				qnh = UnitConverter.inHg_to_hPa(altimeter);
			} else if (metarData[i].length() == 5 && metarData[i].charAt(0) == 'Q') {
				tempStringi = i - 1;
				qnh = Double.parseDouble(metarData[i].replace("Q", ""));
				altimeter = UnitConverter.hPa_to_inHg(qnh);
			}
		}

		if (metarData[tempStringi].contains("/")) {
			String[] tempData = metarData[tempStringi].split("/");

			if (tempData[0].contains("M")) {
				tempC = Integer.parseInt(tempData[0].replace("M", "")) * -1;
				tempF = (int) UnitConverter.c_to_f(tempC);
			} else {
				tempC = Integer.parseInt(tempData[0]);
				tempF = (int) UnitConverter.c_to_f(tempC);
			}

			if (tempData[1].contains("M")) {
				dewpointC = Integer.parseInt(tempData[1].replace("M", "")) * -1;
				dewpointF = (int) UnitConverter.c_to_f(dewpointC);
			} else {
				dewpointC = Integer.parseInt(tempData[1]);
				dewpointF = (int) UnitConverter.c_to_f(dewpointC);
			}
		}
	}

	public String getSite() {
		return site;
	}

	public String getTime() {
		return time;
	}

	public int getWindDir() {
		return windDir;
	}

	public int getWindSpd() {
		return windSpd;
	}

	public int getWindGst() {
		return windGst;
	}

	public double getVis_sm() {
		return vis_sm;
	}

	public double getVis_km() {
		return vis_km;
	}

	public String getWx() {
		return wx;
	}

	public String getClouds() {
		return clouds;
	}

	public int getTempC() {
		return tempC;
	}

	public int getTempF() {
		return tempF;
	}

	public int getDewpointC() {
		return dewpointC;
	}

	public int getDewpointF() {
		return dewpointF;
	}

	public double getAltimeter() {
		return altimeter;
	}

	public double getQNH() {
		return qnh;
	}

	@Override
	public String toString() {
		return metar;
	}

}
