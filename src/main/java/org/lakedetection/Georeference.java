package org.lakedetection;

import java.awt.Rectangle;
import java.io.IOException;

import org.esa.snap.core.dataio.ProductIO;
import org.esa.snap.core.datamodel.GeoPos;
import org.esa.snap.core.datamodel.PixelPos;
import org.esa.snap.core.datamodel.Product;

public class Georeference {
	
	/* Das Ziel dieser Methode ist, dass die beiden zu vergleichenden Bilder georeferenziert und lokalisiert werden,
	 * um sicherzustellen, dass es sich um dasselbe Gewaesser handelt.
	 * Dazu wird im zuerst betrachteteten Bildausschnitt der Punkt (0,0) oben links als 
	 * Ausgangspunkt betrachtet. 
	 * Dann werden fuer diesen Punkt die Koordinaten abgefragt. 
	 * 
	 * Im Folgenden wuerde ein zweites Bild betrachtet werden und fuer die Koordinaten, die uebergeben werden muessten,
	 * wuerde nach den Koordinaten geschaut werden und, falls vorhanden, die Pixel-Position abgefragt werden.
	 * 
	 * Nun ist von dem Ausgangspunkt (0,0) ueber die Koordinaten die Position dieses Punktes
	 * im zweiten Bild ermittelt worden. Nun wuerden diese Bilder verglichen werden koennen. 
	 * 
	 * Ggf. ist es sinnvoll, zwei Punkte zu nehmen, einen (0,0) und den zweiten bspw. (0,10), damit die Bilder auch genordet bzw. ausgerichtet 
	 * uebereinander gelegt werden koennen.s
	 * */
	
	private String[] geo; // Erzeugt Array aus Strings names "geo" 
	
	private Product product;
	
	public Georeference(Product p) {
		
		geo = product.getTiePointGridNames(); // Fuellt das oben erzeugte Array mit dem Namen der GCP-Liste
	}

	//// Im Main festsetzen auf 11178 x 7116 für Mueritzsee und später im Gui den Nutzer auffordern selber eine Punkt einzugeben
	public GeoPos getGeoPos(PixelPos p) {
		return product.getSceneGeoCoding().getGeoPos(p, null);
	}
	
	public PixelPos getPixPos(GeoPos p) {
		return product.getSceneGeoCoding().getPixelPos(p, null);
	}
	
	// Gibt den Namen der GCP-Punktliste zurück // Fkt. eig. nicht notw.
	public String[] getName() {
		return geo;
	}
	
	// Vergleicht zwei Koordinaten miteinander
	public boolean compare(GeoPos a, GeoPos b) {
		if(a == b) return true;
		else return false;
	}
	
}
