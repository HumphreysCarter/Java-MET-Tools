package MET_304_L50_SHARPpy_Sounding;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

import Utilities.Utility;

public class Main {

	public static void main(String[] args) throws FileNotFoundException {

		PrintWriter out = new PrintWriter("output.txt");
		
		out.println("%TITLE%");
		out.println("OSW" + "\t" + "180529/1732");
		out.println("");
		out.println("LEVEL\tHGHT\tTEMP\tDWPT\tWDIR\tWSPD");
		out.println("-------------------------------------------------------------------");
		out.println("%RAW%");

		ArrayList<String> dataStrings = loadVaisalaData();
		ArrayList<RadiosondeData> data = new ArrayList<RadiosondeData>();

		double p0 = 920;
		for (int i = 1; i < dataStrings.size(); i++) {
			RadiosondeData ob = convertVaisalaData(dataStrings.get(i - 1).split("\t"), dataStrings.get(i).split("\t"),
					p0);
			data.add(ob);
			p0 = ob.getLevel();
		}

		for (int i = 0; i < data.size(); i++) {
			out.println("  " + data.get(i));
		}

		out.println("%END%");
		
	}

	public static ArrayList<String> loadVaisalaData() {

		try {
			Scanner fileReader = new Scanner(new File("OSW_20180529_1732.txt"));

			ArrayList<String> dataStrings = new ArrayList<String>();

			while (fileReader.hasNextLine()) {
				dataStrings.add(fileReader.nextLine());
			}
			return dataStrings;
		} catch (FileNotFoundException e) {
			return null;
		}
	}
	
	public static void writeToFile(String output) {
		try {
			Files.write(Paths.get("output.txt"), output.getBytes());
		
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static RadiosondeData convertVaisalaData(String[] layerBase, String[] layerTop, double p0) {

		double z0 = Double.parseDouble(layerBase[1]); // height at the base of the layer
		double z = Double.parseDouble(layerTop[1]); // height at the top of the layer
		double T0 = Double.parseDouble(layerBase[3]); // Temp at the base of the layer
		double T = Double.parseDouble(layerTop[3]); // Temp at the top of the layer
		double RH = Double.parseDouble(layerTop[4]); // Relative Humidity at the top of the layer
		double Td = getDewpointFromRH_T(T, RH); // Dewpoint at the top of the layer
		double wDir = Double.parseDouble(layerTop[5]); // Wind Direction at the top of the layer
		double wSpd = 1.94384 * Double.parseDouble(layerTop[6]); // Wind Speed at the top of the layer converted to kts
		double p = getPFromLayerData(p0, z0, z, T0, T);

		return new RadiosondeData(Utility.round(p, 2), Utility.round(z, 2), Utility.round(T, 2), Utility.round(Td, 2),
				Utility.round(wDir, 2), Utility.round(wSpd, 2));
	}

	// Calculates dewpoint (deg C) from air temperature (deg C) and relative
	// humidity (%)
	// Uses August-Roche-Magnus approximation:
	// http://andrew.rsmas.miami.edu/bmcnoldy/Humidity.html
	public static double getDewpointFromRH_T(double T, double RH) {
		return 243.04 * (Math.log(RH / 100) + ((17.625 * T) / (243.04 + T)))
				/ (17.625 - Math.log(RH / 100) - ((17.625 * T) / (243.04 + T)));
	}

	// Calculates pressure level from layer data using the Hypsometric Equation
	public static double getPFromLayerData(double p0, double z0, double z, double T0_c, double T_c) {

		// Conversion to MKS units
		double T0 = T0_c + 273.15; // Convert T at base from deg C to K
		double T = T_c + 273.15; // Convert T at top of layer from deg C to K

		// Calculations
		double Tbar = (T0 + T) / 2.0; // Calculate mean temperature of layer from T0 (base) and T (top)
		double dz = z - z0; // Calculate layer thickness from z0 (height at the base of the layer) and z
							// (height
							// at the top of the layer)

		// Pressure from Hypsometric Equation
		return HypsometricEquation.getLayerTop(Tbar, p0, dz);
	}

}
