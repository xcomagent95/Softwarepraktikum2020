package org.lakedetection;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.IOException;

import org.esa.snap.core.dataio.ProductIO;
import org.esa.snap.core.datamodel.Product;

import com.bc.ceres.core.PrintWriterProgressMonitor;

public class ToArray {
	private int arrayHeight; //Hoehe des Rasters
	private int arrayWidth; //Breite des Rasters
	private float[][] datasetArray; //Array von Float Werten f�r die Speicherung von Farbwerten
	
	private int requestedCornerX;
	private int requestedCornerY;
	private int requestedHeight;
	private int requestedWidth;
	
	
	
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
		arrayHeight = product.getBand(band).getRasterHeight(); //Abfragen der Hoehe
		arrayWidth = product.getBand(band).getRasterWidth(); //Abfragen der Breite
		System.out.println("dataset " +  band  + " is converted into array!"); //i = height / j = width
		System.out.println("array build! with height: " + arrayHeight + " and width: " + arrayWidth);
	}
	
	//Getter fuer Array
	public float[][] getArray() {
		return datasetArray;
	}
	
	public void bufferedImageToArray(BufferedImage img, float[][] targetarray, int requestedCornerX, int requestedCornerY, int requestedHeight, int requestedWidth) {
        Raster raster = img.getData(new Rectangle(requestedCornerX, requestedCornerY, requestedHeight, requestedWidth)); 
		for(int x = 0; x < requestedHeight; x++) {
	        for(int y = 0; y < requestedWidth; y++) {
	        	targetarray[x][y] = raster.getSampleFloat(x, y, 0);
	        }
	    }
	}
	
	public void fillArray(Loadzip dataset, String band, float[][] datasetarray, int requestedCornerX, int requestedCornerY, int requestedHeight, int requestedWidth) {
		Product product = null; //Product initialisieren
		
		 //Product lesen
		try {
			product = ProductIO.readProduct(dataset.getFile());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		BufferedImage image = product.getBand(band).getSourceImage().getAsBufferedImage();
		System.out.println("image buffered!");
		
		//System.out.println(image.getColorModel());
		
		bufferedImageToArray(image, datasetarray, requestedCornerX, requestedCornerY, requestedHeight, requestedWidth);
		System.out.println("buffered image converted into array!");
	}
}
