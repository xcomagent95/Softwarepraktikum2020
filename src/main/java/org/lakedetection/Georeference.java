package org.lakedetection;

import org.esa.snap.core.datamodel.GeoPos;
import org.esa.snap.core.datamodel.PixelPos;
import org.esa.snap.core.datamodel.Product;

/**
 * @author Josefina Balzer
 * @version 1.0
 */
public class Georeference {
	/**
	 * Das Ziel der Methoden dieser Klasse ist, dass die beiden zu vergleichenden Bilder 
	 * georeferenziert und lokalisiert werden, um sicherzustellen, dass es sich um 
	 * dasselbe Gewaesser handelt. Dazu wird im zuerst betrachteteten Bildausschnitt 
	 * der Punkt (0,0) oben links als Ausgangspunkt betrachtet. 
	 * Dann werden fuer diesen Punkt die Koordinaten abgefragt. 
	 * 
	 * Im Folgenden wuerde ein zweites Bild betrachtet werden und fuer die Koordinaten, 
	 * die uebergeben werden muessten, wuerde nach den Koordinaten geschaut werden und, 
	 * falls vorhanden, die Pixel-Position abgefragt werden.
	 * 
	 * Nun ist von dem Ausgangspunkt (0,0) ueber die Koordinaten die Position dieses Punktes
	 * im zweiten Bild ermittelt worden. Nun wuerden diese Bilder verglichen werden koennen. 
	 * 
	 * Ggf. ist es sinnvoll, zwei Punkte zu nehmen, einen (0,0) und den zweiten bspw. (0,10), 
	 * damit die Bilder auch genordet bzw. ausgerichtet uebereinander gelegt werden koennen.
	 */
	
	private String[] geo; // Erzeugt Array aus Strings names "geo" 
	
	private Product product; // Erzeugt ein Prdosukt vom Typp Product
	
	/**
	 * Konstruktor der Klasse Georeference; Die Tabelle von Verbindungspunkten wird als String-Array 
	 * gespeichert.
	 * @param Ein Produkt, genannt p
	 */
	public Georeference(Product p) {
		product = p;
		geo = product.getTiePointGridNames(); // Fuellt das oben erzeugte Array mit dem Namen der GCP-Liste
	}
	
	/**
	 * Getter für die WGS84-Koordinaten
	 * @param Zwei Doubles sind ein Pixelpaar
	 * @return WGS84-Koordinaten des Pixelpaares
	 */
	public GeoPos getGeoPos(double x, double y) {
		PixelPos pixpos = new PixelPos(x, y);
		return product.getSceneGeoCoding().getGeoPos(pixpos, null);
	}
	
	/**
	 * Getter für die Pixel-Werte der Koordinaten aus dem Bild
	 * @param Zwei Doubles die Längen- und Breitengrad sind
	 * @return Pixelwerte des Koordinatenpunktes auf dem Bild
	 */
	public PixelPos getPixPos(double lat, double lon) {//GeoPos p) {
		GeoPos geopos = new GeoPos(lat, lon);
		return product.getSceneGeoCoding().getPixelPos(geopos, null);
	}
	
	/**
	 * Getter für den Namen der GCP-Punktliste
	 * @return Namen der GCP-Punktliste
	 */
	public String[] getName() {
		return geo;
	}
	
	/**
	 * Vergleicht zwei Koorinaten miteinander
	 * @param Koorinatenpaar a bestehend aus zwei Doubles
	 * @param Koorinatenpaar b bestehend aus zwei Doubles
	 * @return Booleschen Wert und ob die beiden Koordinaten gleich oder ungleich sind
	 */
	public boolean compare(GeoPos a, GeoPos b) {
		if(a == b) return true;
		else return false;
	}
	
}
