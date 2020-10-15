package org.lakedetection;

import java.io.IOException;

import org.esa.snap.core.datamodel.Band;
import org.esa.snap.core.datamodel.Product;

/**
 * @author Alexander Pilz
 * @version 1.0
 */
public class RasterToArray {
	private int arrayHeight; //Hoehe des Rasters
	private int arrayWidth; //Breite des Rasters
	private float[][] datasetArray; //Array von Float Werten fuer die Speicherung von Farbwerten
	private int[][] datasetArrayNormalised; // Array mit normalisierten Farbwerten
	private int[][] datasetArrayRGB; // Array mit RGB-Farbwerten 
	private float[] loadeddata; // Array mit geladenen Daten 

	private int requestedCornerX; // X-Koordinate der oberen linken Ecke
	private int requestedCornerY; // Y-Koordinate der oberen linken Ecke
	private int requestedHeight; // Hoehe des angefragten Bildausschnitts
	private int requestedWidth; // Breite des angefragten Bildausschnitts

	private String requested_Band; //abgefragtes Band

	private float lowestPixel;
	private float highestPixel;
	private float averagePixel;

	//ToArray Konstruktor
	//Uebergeben werden muss der Datensatz als Loadzip, das geuenschte Band als String, sowie die Eckdaten zum angefragten Bildausschnitt
	/**
	 * Konstruktor der RasterToArray-Klasse
	 * @param (product) Erhaelt das Produkt aus der Datei welches die Baender anthaelt, vom Typ Product
	 * @param (band) Erhaelt das zu bearbeitende Band als String  
	 * @param (requestedX) Erhaelt einen Integerwert, der die X-Koordinate der oberen linken Ecke enthaelt
	 * @param (requestedY) Erhaelt einen Integerwert, der die Y-Koordinate der oberen linken Ecke enthaelt
	 * @param (height) Erhaelt einen Integerwert, der die Hoehe des angefragten Bildausschnitts enthaelt
	 * @param (width) Erhaelt einen Integerwert, der die Breite des angefragten Bildausschnitts enthaelt
	 */
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
		this.fillArray(product);
		this.calculateStatistics();
	}

	//Getter:
	
	public float[][] getArray() { return datasetArray;}

	public int[][] getArrayNormalised() { return datasetArrayNormalised;}

	public int[][] datasetArrayRGB() { return datasetArrayRGB;}

	public int getRequestedCornerX() { return requestedCornerX;}
	
	public int getRequestedCornerY() { return requestedCornerY;}

	public int getRequestedHeight() { return requestedHeight;}

	public int getRequestedWidth() { return requestedWidth;}

	public float getHighestPixel() { return highestPixel;}

	public float getLowestPixel() { return lowestPixel;}

	/**
	 * Methode zum fuellen des Arrays mit den 
	 * Pixelwerten des Datensatzes im angefragten Bildausschnitt
	 * @param Uebergeben werden muss das Produkt des Datensatz
	 */
	private void fillArray(Product product) {
		// Buffered Image aus Produkt hohlen
		Band loadedBand = product.getBand(this.requested_Band);
		try {
			// Befuelen mit Formel
			float[] data = loadedBand.readPixels(this.requestedCornerX-(this.arrayHeight/2), 
									this.requestedCornerY-(this.arrayWidth/2), this.arrayWidth, 
									this.arrayHeight, (float[]) null);
			loadeddata = data;
	        for(int row = 0; row < this.requestedHeight; row++) {
	        	for(int column = 0; column < this.requestedWidth; column++) {
	        		// Befuellen des datasetArrays
	        		this.datasetArray[row][column] = data[column + row*requestedWidth];
	        	}
	        }
	        System.out.println("raster read from image!");
			} catch (IOException e) {
				e.printStackTrace();
			}
	}

	/**
	 * Methode zum berechnen der benoetigte Parameter bzw. der Eckdaten zum Array
	 */
	private void calculateStatistics() {
		float min = ArrayUtils.getMin(this.loadeddata);
		float max = ArrayUtils.getMax(this.loadeddata);
		lowestPixel = min;
		highestPixel = max;
		averagePixel = max + min/2;
	}
	
	/**
	 * Methode, welche alle Werte aus dem Array ausgibt, um zu prüfen, ob dieses 
	 * tatsächlich normalisierte Werte enthaelt.
	 * Test-Methode
	 */
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

	/**
	 * Getter fuer den groeßten Pixelwert.
	 * @param inputArray aus Float-Werten 
	 * @return Den max. Wert als Integer
	 */
	public float getMax(float[] inputArray){
		float maxValue = inputArray[0];
	    for(int i=1;i < inputArray.length;i++){
	    	if(inputArray[i] > maxValue){
	    		maxValue = inputArray[i];
	    	}
	    } return maxValue;
	}

	/**
	 * Getter fuer den kleinsten Pixelwert.
	 * @param inputArray aus Float-Werten 
	 * @return Den min. Wert als Integer
	 */
	public float getMin(float[] inputArray){
		float minValue = inputArray[0];
	    for(int i=1;i<inputArray.length;i++){
	    	if(inputArray[i] < minValue){
	    		minValue = inputArray[i];
	    	}
	    } return minValue;
	}

	/**
	 * Die Methode setzt alle Pixelwerte, welche groeßer sind, als der Minimalwert, auf 0.
	 * Arbeitet mit float-Werten.
	 */
	public void filterArrayLowestPixel() {
		for(int i = 0; i < this.arrayHeight; i++) {
			for(int j = 0; j < this.arrayWidth; j++) {
				if(this.datasetArray[i][j] != lowestPixel) {
					datasetArray[i][j] = 0.0f;
				}
			}
		}
	}
	
	/**
	 * Die Methode konvertiert die Farbwerte alle auf eine Grauskala
	 */
	public void convertToGreyscale() {
		datasetArrayNormalised = new int[this.arrayHeight][this.arrayWidth];
		for(int i = 0; i < this.arrayHeight; i++) {
			for(int j = 0; j < this.arrayWidth; j++) {
				datasetArrayNormalised[i][j] = ((int) ((datasetArray[i][j]-this.lowestPixel)*(255 - 0)/(this.highestPixel-this.lowestPixel)+0)) +
						(((int) ((datasetArray[i][j]-this.lowestPixel)*(255 - 0)/(this.highestPixel-this.lowestPixel)+0)) << 8) +
						(((int) ((datasetArray[i][j]-this.lowestPixel)*(255 - 0)/(this.highestPixel-this.lowestPixel)+0)) << 16);
			}
		}
	}

	// Getter:
	public int[][] getConvertedArray(){ return datasetArrayNormalised;}
}

