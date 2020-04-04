package MET_304_L50_SHARPpy_Sounding;


public class HypsometricEquation {

	// Hypsometric Equation: h=RdTv/g * ln(p0/p)

	private static final double g = 9.81; // Gravity (m s-1)
	private static final double Rd = 287; // Dry Gas Constant (J kg-1 K-1)

	// Returns the thickness of a layer in meters
	public static double getLayerThickness(double Tbar, double p0, double p) {
		return (Rd * Tbar) / g * Math.log(p0 / p);
	}

	// Returns the base of a layer in millibars (hectopascals)
	public static double getLayerBase(double Tbar, double p, double h) {
		return p * Math.exp((g * h) / (Rd * Tbar));
	}

	// Returns the top of a layer in millibars (hectopascals)
	public static double getLayerTop(double Tbar, double p0, double h) {
		return p0 * Math.exp(-(g * h) / (Rd * Tbar));
	}

}