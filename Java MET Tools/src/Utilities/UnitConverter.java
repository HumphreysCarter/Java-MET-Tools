package Utilities;


// version 1.0

public class UnitConverter {

	//
	// Pressure
	//
	
	public static double inHg_to_hPa(double inHg) {
		return (1013.25 / 29.92) * inHg;
	}

	public static double hPa_to_inHg(double hPa) {
		return (29.92 / 1013.25) * hPa;
	}
	
	//
	// Distance
	//
	
	public static double km_to_nm(double km) {
		return km / 1.852;
	}
	
	public static double nm_to_km(double nm) {
		return nm * 1.852;
	}
	
	public static double km_to_mi(double km) {
		return ft_to_mi(m_to_ft(km * 1000));
	}

	public static double mi_to_km(double mi) {
		 return ft_to_m(mi_to_ft(mi) / 1000);
	}

	public static double ft_to_mi(double ft) {
		 return (1.0 / 5280) * ft;
	}

	public static double mi_to_ft(double mi) {
	    return 5280 * mi;
	}

	public static double m_to_ft(double m) {
		 return (1 / 0.3048) * m;
	}

	public static double ft_to_m(double ft) {
		   return 0.3048 * ft;
	}
	
	//
	// Temperature
	//

	public static double f_to_c(double f) {
		return (f - 32) * (5.0 / 9.0);
	}

	public static double c_to_f(double c) {
		return c * (9.0 / 5.0) + 32; 
	}
	
	public static double c_to_k(double c) {
		return c + 273.15; 
	}
	
	public static double f_to_k(double f) {
		return f_to_c(f) + 273.15; 
	}
	
}
