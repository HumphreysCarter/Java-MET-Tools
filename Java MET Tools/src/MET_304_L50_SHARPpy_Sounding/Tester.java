package MET_304_L50_SHARPpy_Sounding;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Tester {

	public static void main(String[] args) {
		
		System.out.println("%TITLE%");
		System.out.println("OSW" +"\t" + "180529/1732");
		System.out.println();
		System.out.println("LEVEL\tHGHT\tTEMP\tDWPT\tWDIR\tWSPD");
		System.out.println("-------------------------------------------------------------------");
		System.out.println("%RAW%");
		
		loadVaisalaData();
		
		RadiosondeData testPoint = new RadiosondeData(965.00, 350.00, 27.80, 23.80, 150.00, 23.00); 
		System.out.println(testPoint);
		
		System.out.println("%END%");
	}
	
	
	/*
	 *	Vaisala Data Format
	 *	INDEX	HGHT (m?)	???		TEMP (C)	???		WDIR	WSPD (m/s)		HGHT (FT?)	LAT			LON  
	 *   17		884			///		20.5		77		103		4.34			80			37.769		-100.037
	 *   2785	13761		///		-57.1	  	2		229		28.71			53836		38.124	 	-99.621
	 *
	 */
	
	public static void loadVaisalaData() {
		
		try {
			Scanner vaisalaFile = new Scanner(new File("OSW_20180529_1732.txt"));
			
			while(vaisalaFile.hasNextLine()) {
				System.out.println(vaisalaFile.nextLine());
			}
		
		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		
	}

}
