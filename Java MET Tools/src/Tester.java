import AWC_ADDS_TDS.ADDS_AIREP;
import AWC_ADDS_TDS.ADDS_METAR;
import CAP_Alerts.nws_alerts;

public class Tester {
	
	public static void main(String[] args) {
		

		nws_alerts oswego = new nws_alerts("NYC027");
		oswego.ingestNewData();
		
	}

}
