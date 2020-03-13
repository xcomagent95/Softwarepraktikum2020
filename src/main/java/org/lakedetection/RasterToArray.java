package org.lakedetection;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.media.jai.PlanarImage;

import org.esa.snap.core.dataio.ProductIO;
import org.esa.snap.core.datamodel.Band;
import org.esa.snap.core.datamodel.Product;
import org.esa.snap.core.image.ImageManager;

import com.bc.ceres.core.PrintWriterProgressMonitor;
import com.bc.ceres.glevel.MultiLevelImage;

public class RasterToArray {
	private int arrayHeight; //Hoehe des Rasters
	private int arrayWidth; //Breite des Rasters
	private float[][] datasetArray; //Array von Float Werten fuer die Speicherung von Farbwerten
	private int[][] datasetArrayNormalised;
	private int[][] datasetArrayRGB;
	private float[] loadeddata;
 
	private int requestedCornerX; //X-Koordinate der oberen linken Ecke
	private int requestedCornerY; //Y-Koordinate der oberen linken Ecke
	private int requestedHeight; //Hoehe des angefragten Bildausschnitts
	private int requestedWidth; //Breite des angefragten Bildausschnitts

	private String requested_Band; //abgefragtes Band
	
	private float lowestPixel;
	private float highestPixel;
	private float averagePixel;
	
	//ToArray Konstruktor
	//Uebergeben werden muss der Datensatz als Loadzip, das geuenschte Band als String, sowie die Eckdaten zum angefragten Bildausschnitt
	public RasterToArray(Loadzip dataset, String band, int requestedX, int requestedY, int height, int width) {
		Product product = null; //Product initialisieren
		try {
			product = ProductIO.readProduct(dataset.getFile()); //Product lesen
		} catch (IOException e) {
			e.printStackTrace();
		}

		//Erzeugen des Arrays

		datasetArray = new float[height][width]; //Array erzeugen
		requestedCornerX = requestedX;
		requestedCornerY = requestedY;
		requestedHeight = height;
		requestedWidth = width;
		arrayHeight = height;
		arrayWidth = width;
		requested_Band = band;
		System.out.println("dataset " +  band  + " is converted into array!"); //i = height / j = width
		System.out.println("array build! with height: " + arrayHeight + " and width: " + arrayWidth);
		System.out.println("requested corner: " + requestedCornerX + "/" + requestedCornerY + " and bbox: " + requestedHeight + "*" + requestedWidth);
	}

	//Getter
	public float[][] getArray() {
		return datasetArray;
	}
	
	public int[][] getArrayNormalised() {
		return datasetArrayNormalised;
	}
	
	public int[][] datasetArrayRGB() {
		return datasetArrayRGB;
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

	//Methode zum fuellen des Arrays mit den Pixelwerten des Datensatzes im angefragten Bildausschnitt
	//Uebergeben werden muss der Datensatz als Loadzip, das geuenschte Band als String
	public void fillArray(Loadzip dataset) {
		Rectangle rect = new Rectangle(requestedWidth, requestedHeight);
		
		//Product initialisieren
		Product product = null;

		//Product lesen
		try {
			product = ProductIO.readProduct(dataset.getFile());
		} catch (IOException e) {
			e.printStackTrace();
		}

		//buffered Image aus Produkt hohlen
		Band loadedBand = product.getBand(this.requested_Band);
		try {
			float[] data = loadedBand.readPixels(this.requestedCornerX, this.requestedCornerY, this.arrayWidth, this.arrayHeight, (float[]) null);
			
			loadeddata = data;

			System.out.println("image buffered!");
			//Raster aus buffered Image hohlen und Farbwerte in Array speichern
			//Hier wird ueber eine Schleife das 2D-Array gefuellt
	        System.out.println("raster requested!");
	        for(int row = 0; row < this.requestedHeight; row++) {
	        	for(int column = 0; column < this.requestedWidth; column++) {
	        		this.datasetArray[row][column] = data[column + row*requestedWidth];
	        	}
	        }
	        
	        System.out.println("raster read from image!");
			} catch (IOException e) {
				e.printStackTrace();
			}
	}

	//Tester 
	/*
	public void probeArray() {
		for (int i=0; i<this.datasetArray.length; i++)
		{
			System.out.print("[");
			for (int j=0; j<this.datasetArray[i].length; j++)
			{
				System.out.print(this.datasetArray[i][j] + ",");
			}
			System.out.print("]\n");
		}
	}
	
	public void probeArrayNormalised() {
		for (int i=0; i<this.datasetArrayNormalised.length; i++)
		{
			System.out.print("[");
			for (int j=0; j<this.datasetArrayNormalised[i].length; j++)
			{
				System.out.print(this.datasetArrayNormalised[i][j] + ",");
			}
			System.out.print("]\n");
		}
	}
	*/
	
	  //groessten Pixelwert ausgeben
	  public float getMax(float[] inputArray){ 
		  float maxValue = inputArray[0]; 
	    for(int i=1;i < inputArray.length;i++){ 
	      if(inputArray[i] > maxValue){ 
	         maxValue = inputArray[i]; 
	      } 
	    } 
	    return maxValue; 
	  }
	 
	  //kleinsten Pixelwert ausgeben
	  public float getMin(float[] inputArray){ 
	    float minValue = inputArray[0]; 
	    for(int i=1;i<inputArray.length;i++){ 
	      if(inputArray[i] < minValue){ 
	        minValue = inputArray[i]; 
	      } 
	    } 
	    return minValue; 
	  } 
	 
	//Berechnen einiger Eckdaten zum Array
	public void calculateStatistics() {
		System.out.println("Pixel-Count: " + this.arrayHeight * this.arrayWidth);
		System.out.println("lowest Pixel-Value: " + getMin(this.loadeddata));
		System.out.println("highest Pixel-Value: " + getMax(this.loadeddata));
		System.out.println("average Pixel-Value: " + (getMax(this.loadeddata) + getMin(this.loadeddata)/2));
		
		lowestPixel = getMin(this.loadeddata);
		highestPixel = getMax(this.loadeddata);
		averagePixel = (getMax(this.loadeddata) + getMin(this.loadeddata)/2);
	}
}