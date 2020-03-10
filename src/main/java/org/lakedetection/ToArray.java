package org.lakedetection;

import java.io.IOException;

import org.esa.snap.core.dataio.ProductIO;
import org.esa.snap.core.datamodel.Product;

public class ToArray {
	private int arrayHeight;
	private int arrayWidth;
	private float[][] datasetArray;
	
	public ToArray(Loadzip dataset, String band) { //Amplitude_VH, Intensity_VH, Amplitude_VV, Intensity_VV
		Product product = null; //Product initialisieren
		try {
			product = ProductIO.readProduct(dataset.getFile()); //Product lesen
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		datasetArray = new float[product.getBand(band).getRasterHeight()][product.getBand(band).getRasterWidth()]; //Array erzeugen getPixelFloat(x, y)
		arrayHeight = product.getBand(band).getRasterHeight();
		arrayWidth = product.getBand(band).getRasterWidth();
		System.out.println("dataset " +  band  + " is converted into array!");
	}
	
	public float[][] getArray() {
		System.out.println("array build! with height: " + arrayHeight + " and width: " + arrayWidth);
		return datasetArray;
	}
	
	public void writeArrayValues() {
		
	}
}
