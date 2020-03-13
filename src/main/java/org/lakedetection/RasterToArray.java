package org.lakedetection;

import java.io.IOException;
import org.esa.snap.core.datamodel.Band;
import org.esa.snap.core.datamodel.Product;

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
	public RasterToArray(Product product, String band, int requestedX, int requestedY, int height, int width) {
		
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
		this.fillArray(product);
		this.calculateStatistics();
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
	
	public float getHighestPixel() {
		return highestPixel;
	}
	
	public float getLowestPixel() {
		return lowestPixel;
	}

	//Methode zum fuellen des Arrays mit den Pixelwerten des Datensatzes im angefragten Bildausschnitt
	//Uebergeben werden muss der Datensatz als Loadzip, das geuenschte Band als String
	private void fillArray(Product product) {
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
 
	//Berechnen einiger Eckdaten zum Array 
	private void calculateStatistics() {
		float min = ArrayUtils.getMin(this.loadeddata);
		float max = ArrayUtils.getMax(this.loadeddata);
		System.out.println("Pixel-Count: " + this.arrayHeight * this.arrayWidth);
		System.out.println("lowest Pixel-Value: " + min);
		System.out.println("highest Pixel-Value: " + max);
		System.out.println("average Pixel-Value: " + max + min/2);
		System.out.println("statistics calculated! " + max + min/2);
		
		lowestPixel = min;
		highestPixel = max;
		averagePixel = max + min/2;
	}
}