package org.lakedetection;

import org.esa.snap.core.dataio.ProductIO;
import org.esa.snap.core.dataio.ProductReader;
import org.esa.snap.core.dataio.ProductSubsetBuilder;
import org.esa.snap.core.dataio.ProductSubsetDef;
import org.esa.snap.core.datamodel.Band;
import org.esa.snap.core.datamodel.Product;
import org.esa.snap.core.datamodel.TiePointGrid;

import javax.imageio.stream.FileImageOutputStream;
import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) { //main Methode
    	//Laden des Datensatzes als Objekt vom Typ Loadzip und dem Namen dataset
    	Loadzip dataset = new Loadzip("/Users/josefinabalzer/Desktop/S1A_IW_GRDH_1SDV_20200307T052505_20200307T052530_031565_03A2FE_508A.zip");


    	//Lesen des Produktes als Objekt vom typ Product und dem Namen product
		try {
			//Product lesen
			Product product = ProductIO.readProduct(dataset.getFile());
			System.out.println("product loaded!");

			//Baender ausgeben (hier werden �ber eine vorhanden Methode alle Baender ausgegeben welche product enth�lt
			for(int i = 0; i < product.getBandNames().length; i++) {
				System.out.println(product.getBandNames()[i]);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		//Hier wird aus dataset unter Angabe des gew�nschten Bandes ein Array erzeugt.
		//Das Ergebnis ist ein Objekt vom Typ ToArray mit dem Namen datasetarray
		//Es k�nnen folgende B�nder angefragt werden: Amplitude_VH, Intensity_VH, Amplitude_VV, Intensity_VV
		
		ToArray amplitude_vh = new ToArray(dataset, "Amplitude_VH", 9961, 9994, 21, 21); //VH-Band
		
		// VON JOSI VERAENDERT:
		ToArray amplitude_vv = new ToArray(dataset, "Amplitude_VV", 9961, 9994, 21, 21); //VV-Band
		 ///////  Aenderung zum Testen des Gauss-Algorithmus
		ROPs rops = new ROPs();
		//Fuellen das Arrays
<<<<<<< Updated upstream
		///////amplitude_vh.fillArray(dataset);
=======

		amplitude_vh.fillArray(dataset);
>>>>>>> Stashed changes
		amplitude_vv.fillArray(dataset);
		
		
		////// Erst addieren und dann in RGB umwandel!!! dennn rgb lässt sich nicht gescheit addieren
		
		ToArray connectedArrays = new ToArray(dataset, "ConnectedArrays", 0, 0, 21, 21);
		
		connectedArrays = connectedArrays.connectP(amplitude_vh.getArray(), amplitude_vv.getArray());
		connectedArrays.convertToGreyscale();
		connectedArrays.arrayToImage();
		//connectedArrays.probeArrayNormalised();
		
		////
		 
		/////////amplitude_vh.probeArray();

		/////ROPs rops = new ROPs();
		/////rops.connect(amplitude_vh.getArray(), amplitude_vv.getArray());
		
		/* Objekt "test" der Klasse "Georeference" wird erzeugt. Übergeben wird dafür der Test-Datensatz.
		 * 
		 * */
		Georeference test = new Georeference(dataset);
		System.out.println(test.getName().toString());
		System.out.println(test.georef().toString());
    }
}
