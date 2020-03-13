package org.lakedetection;

import java.awt.Rectangle;
import java.io.IOException;

import org.esa.snap.core.dataio.ProductIO;
import org.esa.snap.core.datamodel.GeoPos;
import org.esa.snap.core.datamodel.PixelPos;
import org.esa.snap.core.datamodel.Product;

public class Georeference {
	
	/* Das Ziel dieser Methode ist, dass die beiden zu vergleichenden Bilder georeferenziert und lokalisiert werden,
	 * um sicherzustellen, dass es sich um dasselbe Gewässer handelt.
	 * Dazu wird im zuerst betrachteteten Bildausschnitt der Punkt (0,0) oben links als 
	 * Ausgangspunkt betrachtet. 
	 * Dann werden für diesen Punkt die Koordinaten abgefragt. 
	 * 
	 * Im Folgenden würde ein zweites Bild betrachtet werden und für die Koordinaten, die übergeben werden müssten,
	 * würde nach den Koordinaten geschaut werden und, falls vorhanden, die Pixel-Position abgefragt werden.
	 * 
	 * Nun ist von dem Ausgangspunkt (0,0) über die Koordinaten die Position dieses Punktes
	 * im zweiten Bild ermittelt worden. Nun können diese Bilder verglichen werden. 
	 * 
	 * Ggf. ist es sinnvoll, zwei Punkte zu nehmen, einen (0,0) und den zweiten bspw. (0,10), damit die Bilder auch genordet bzw. ausgerichtet 
	 * überlegt werden können.s
	 * */
	
	private String[] geo; // Erzeugt Array aus Strings names "geo" 
	
	private Product product;
	
	public Georeference(Product p) {


		/// Erweitern um Klassen die im uml benanntn sind. getter sind  das
		
		/*product = null; //Product initialisieren

		try {
			product = ProductIO.readProduct(dataset.getFile()); //Product lesen
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		product.getTiePointGridNames();
		*/
		
		geo = product.getTiePointGridNames(); // Füllt das oben erzeugte Array mit dem Namen der GCP-Liste
	}
	//// Die Methode kann möglicherweise entfernt werden, da sie nichts anderes tut als der Getter in Z. 68 für den festen Punkt GCP_20(26472, 0)
	public GeoPos georef() { // MACHT ES SINN HIER "Product p" ZU UEBERGEBEN???
		
		GeoPos geoPos = new GeoPos();//null//Bsp. GCP_20: (10.708067893981934d, 54.363765716552734d); // lat./long. // akt. auf "null" gesetzt, evtl.noch ergänzen
		PixelPos pixelPos = new PixelPos(26472, 0); // Bsp. GCP_20

		product.getTiePointGridGroup().get(product.getTiePointGridGroup().getName()); // gibt die Knoten der Gruppe zurück

		// Bildpunkt oben links (0,0) ist Vergleichspunkt. Von dort werden die Koordinaten erfragt, damit im Vergleichsbild nach den Koordinaten gesucht werden kann. Z.B. mit product.getSceneGeoCoding().getPixelPos(geoPos, pixelPos);
		PixelPos pixelPos2 = new PixelPos(0,0); 
		
		return product.getSceneGeoCoding().getGeoPos(pixelPos2, geoPos); // gibt Längen- und Breitengrad des GCP zurück
		//product.getTiePointGridGroup().get(product.getTiePointGridGroup().getName()); // gibt die Knoten der Gruppe zurück
		//product.getSceneGeoCoding().getPixelPos(geoPos, pixelPos); // Rückabfrage möglich?
	}
	
	//// Im Main festsetzen auf 11178 x 7116 für Müritzsee und später im Gui den Nutzer auffordern selber eine Punkt einzugeben
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
