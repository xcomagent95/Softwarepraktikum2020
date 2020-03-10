package org.lakedetection;

import java.awt.Rectangle;
import java.io.IOException;

import org.esa.snap.core.dataio.ProductIO;
import org.esa.snap.core.datamodel.Product;
//import org.esa.snap.core.dataop.resamp.Raster;

public class ToArray {
	private int arrayHeight; //Hoehe des Rasters
	private int arrayWidth; //Breite des Rasters
	private float[][] datasetArray; //Array von Float Werten für die Speicherung von Farbwerten
	
	//ToArray Konstruktor
	public ToArray(Loadzip dataset, String band) { //Uebergeben wird der Datensatz vom Typ Loadzip und das gewuenschte Band als String
		Product product = null; //Product initialisieren
		try {
			product = ProductIO.readProduct(dataset.getFile()); //Product lesen
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//Erzeugen des Arrays
		datasetArray = new float[product.getBand(band).getRasterHeight()][product.getBand(band).getRasterWidth()]; //Array erzeugen getPixelFloat(x, y)
		arrayHeight = product.getBand(band).getRasterHeight(); //Abfragen der Hoehe
		arrayWidth = product.getBand(band).getRasterWidth(); //Abfragen der Breite
		System.out.println("dataset " +  band  + " is converted into array!"); //i = height / j = width
		System.out.println("array build! with height: " + arrayHeight + " and width: " + arrayWidth);
	}
	
	//Getter fuer Array
	public float[][] getArray() {
		return datasetArray;
	}
	
	/*
	//Fuellen des Arrays mit Korrespondierenden Farbwerten
	public void writeArrayValues(Loadzip dataset, float[][] array, String band) { //Uebergeben werden: dataset vom Typ Loadzip, das zu fuellende Array sowie das gewünschte Band
		Product product = null; //Product initialisieren
		try {
			product = ProductIO.readProduct(dataset.getFile()); //Product lesen
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			Rectangle testviereck = new Rectangle(25,25,50,50);
			Raster raster = product.getBand(band).getGeophysicalImage().getData(testviereck);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(int i = 0; i < arrayHeight; i++) {
			for(int j = 0; j < arrayWidth; j++) {
				product.getBand(band).getPixelFloat(i, j);
			}
		}
		System.out.println("datasetarray loaded! Value 0/0 " + datasetArray[0][0]);
	}
	*/
}
