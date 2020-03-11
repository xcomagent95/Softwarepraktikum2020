package org.lakedetection;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.IOException;

import javax.media.jai.PlanarImage;

import org.esa.snap.core.dataio.ProductIO;
import org.esa.snap.core.datamodel.Product;
import org.esa.snap.core.image.ImageManager;

import com.bc.ceres.core.PrintWriterProgressMonitor;

public class ToArray {
	private int arrayHeight; //Hoehe des Rasters
	private int arrayWidth; //Breite des Rasters
	private float[][] datasetArray; //Array von Float Werten fuer die Speicherung von Farbwerten
	
	private int requestedCornerX;
	private int requestedCornerY;
	private int requestedHeight;
	private int requestedWidth;
	
	
	
	//ToArray Konstruktor
	public ToArray(Loadzip dataset, String band, int requestedX, int requestedY, int height, int width) { //Uebergeben wird der Datensatz vom Typ Loadzip und das gewuenschte Band als String
		Product product = null; //Product initialisieren
		try {
			product = ProductIO.readProduct(dataset.getFile()); //Product lesen
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//Erzeugen des Arrays
		//datasetArray = new float[product.getBand(band).getRasterHeight()][product.getBand(band).getRasterWidth()]; //Array erzeugen 
		//arrayHeight = product.getBand(band).getRasterHeight(); //Abfragen der Hoehe
		//arrayWidth = product.getBand(band).getRasterWidth(); //Abfragen der Breite
		datasetArray = new float[height][width]; //Array erzeugen 
		requestedCornerX = requestedX;
		requestedCornerY = requestedY;
		requestedHeight = height;
		requestedWidth = width;
		arrayHeight = height;
		arrayWidth = width;
		System.out.println("dataset " +  band  + " is converted into array!"); //i = height / j = width
		System.out.println("array build! with height: " + arrayHeight + " and width: " + arrayWidth);
		System.out.println("requested corner: " + requestedCornerX + "/" + requestedCornerY + " and bbox: " + requestedHeight + "*" + requestedWidth);
	}
	
	//Getter
	public float[][] getArray() {
		return datasetArray;
	}
	
	public int getRequestedCornerX() {
		return requestedCornerX;
	}
	public int getRequestedCornerY() {
		return requestedCornerY;
	}
	
	public int getRequestedHeight() {
		return requestedHeight;
	}
	
	public int getRequestedWidth() {
		return requestedWidth;
	}
	
	public void fillArray(Loadzip dataset, String band, float[][] datasetarray, int requestedCornerX, int requestedCornerY, int requestedHeight, int requestedWidth) {
		//Rectangle initialisieren
		Rectangle rect = new Rectangle(requestedCornerX, requestedCornerY, requestedHeight, requestedWidth);
		
		//Product initialisieren
		Product product = null; 
		
		 //Product lesen
		try {
			product = ProductIO.readProduct(dataset.getFile());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//buffered Image aus Produkt hohlen !!!lauft noch nicht!!!
		BufferedImage image = product.getBand(band).getGeophysicalImage().getAsBufferedImage().getSubimage(requestedCornerX, requestedCornerY, requestedWidth, requestedHeight);
		System.out.println("image buffered!");
		
		//System.out.println(image.getHeight());
		//System.out.println(image.getWidth());
		

		//Raster aus buffered Image hohlen und Farbwerte in Array speichern
		Raster raster = image.getData();
        System.out.println("raster requested!");
        //System.out.println(raster.getNumBands());
		for(int i = 0; i < requestedHeight; i++) {
			for(int j = 0; j < requestedWidth; j++) {
				datasetArray[i][j] = raster.getSampleFloat(i, j, 0);
			}
		}
        System.out.println("raster read from image!");
	}
	
	public void probeArray() {
		for(int i = 0; i < datasetArray.length; i++) {
			for(int j = 0; j < datasetArray[0].length; j++) {
				System.out.println(datasetArray[i][j]);
			}
		}
	}
}

