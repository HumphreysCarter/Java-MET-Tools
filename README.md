# Java Meteorlogical Scripts
An assortment of old meteorological scripts written in Java, some of which may still work...

## Aviation Weather Data
Script for parsing AIREPs and METARs from the Aviation Weather Center [ADDS TDS](https://www.aviationweather.gov/dataserver).
* /src/AWC_ADDS_TDS/ADDS_AIREP.java
* /src/AWC_ADDS_TDS/ADDS_METAR.java

## NWS CAP Alerts
Parser for NWS watch, warning, and advisory information via [NWS XML/CAP](https://alerts.weather.gov/index.php) feed.
* /src/CAP_Alerts/nws_alerts.java

## Vaisala Radiosode Data Formatter
Java build of 2018-19 SUNY Oswego Synoptic Lab project to convert raw Vaisala radiosonde data into a [SHARPpy](https://github.com/sharppy/SHARPpy) readable format.
* /src/MET_304_L50_SHARPpy_Sounding/HypsometricEquation.java
* /src/MET_304_L50_SHARPpy_Sounding/Main.java
* /src/MET_304_L50_SHARPpy_Sounding/RadiosondeData.java
* /src/MET_304_L50_SHARPpy_Sounding/Tester.java

## Utilities
### Geographic Coordinate Object
Object class fot working with latitude and longitude coordinates
* /src/Utilities/GeographicCoordinate.java
### METAR Object
METAR string praser and object class
* /src/Utilities/METAR.java
### Unit Converter
Class with a few helper methods for unit conversion. (pressure, distance, and temperature)
* /src/Utilities/UnitConverter.java
### Utility
Class with helper methods for datetime formating/parsing, double rounding, and internet connection testing.
* /src/Utilities/Utility.java

## LES Polygon Placefile Writter
Script for parsing WSW products for [lake-effect snow ploygons](https://www.weather.gov/media/buf/2016ExperimentalBUFLESPolygonPDD.pdf) to generate placefiles for use in [GRLevelX products](http://grlevelx.com/)
* /src/WSW_LES_Polygons/PlacefileWriter.java
