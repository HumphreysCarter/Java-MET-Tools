package MET_304_L50_SHARPpy_Sounding;

public class RadiosondeData {
	
	private double level = -9999.00;
	private double height = -9999.00;
	private double temp = -9999.00;
	private double dwpt = -9999.00;
	private double windDir = -9999.00;
	private double windSpd = -9999.00;
	
	public RadiosondeData(double level, double height, double temp, double dwpt, double windDir, double windSpd) {
		this.level = level;
		this.height = height;
		this.temp = temp;
		this.dwpt = dwpt;
		this.windDir = windDir;
		this.windSpd = windSpd;
	}
	
	public void checkDataIntegrity () {
		if(temp < -273.15) {
			temp = -9999.00;
		}
		
		if(dwpt < -273.15) {
			dwpt = -9999.00;
		}
		
		if (!(windDir >= 0) && (windDir < 360)) {
			windDir = -9999.00;
		}
		
		if (windSpd < 0) {
			windSpd = -9999.00;
		}
	}

	public double getLevel() {
		return level;
	}

	public double getHeight() {
		return height;
	}

	public double getTemp() {
		return temp;
	}

	public double getDwpt() {
		return dwpt;
	}

	public double getWindDir() {
		return windDir;
	}

	public double getWindSpd() {
		return windSpd;
	}

	@Override
	public String toString() {
		return level + ",\t" + height + ",\t" + temp + ",\t" + dwpt + ",\t" + windDir + ",\t" + windSpd;
	}
	
	

}
