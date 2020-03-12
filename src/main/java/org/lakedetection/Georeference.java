package org.lakedetection;

import java.awt.Rectangle;
import java.io.IOException;

import org.esa.snap.core.dataio.ProductIO;
import org.esa.snap.core.datamodel.Product;
import org.esa.snap.core.datamodel.GeoPos;
import org.esa.snap.core.datamodel.PixelPos;


public class Georeference {

	private String[] geo; // Erzeugt Array aus Strings names "geo" 
	
	public Georeference(Loadzip dataset) {
		Product product = null; //Product initialisieren
		try {
			product = ProductIO.readProduct(dataset.getFile()); //Product lesen
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		geo = product.getTiePointGridNames(); // Füllt das oben erzeugte Array mit dem Namen der GCP-Liste
		GeoPos geoPos = new GeoPos(10.708067893981934d, 54.363765716552734d); // akt. auf "null" gesetzt, evtl.noch ergänzen
		PixelPos pixelPos = new PixelPos(26472, 0); // Bsp. GCP_20
		product.getSceneGeoCoding().getGeoPos(pixelPos, geoPos); // gibt Längen- und Breitengrad des GCP zurück
		product.getTiePointGridGroup().get(product.getTiePointGridGroup().getName()); // gibt die Knoten der Gruppe zurück
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
