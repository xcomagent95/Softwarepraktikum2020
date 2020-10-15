package org.lakedetection;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * Die Klasse enthaelt die Methoden die mit den Arrays arbeiten. 
 * Das impliziert nicht die Rasteroperationen (ROPs), die auf dem Raster arbeiten. 
 * @author Alexander Pilz & Josefina Balzer
 * @version 1.0 
 */
public class ArrayUtils {

	/* || Bilder uebereinanderlegen ||
	 * 
	 * Es liegen 2 2D-Arrays vor. 
	 * Nun sollen beide Arrays durchlaufen werden 
	 * und die Werte an den jeweils gleichen Pos. miteinander addiert 
	 * und durch 2 dividiert werden. 
	 * */

	/// Doku einfuegen
	/**
	 * Die Farbwert der Bilder, die im Array stehen werden auf den RGB-Wertebereich normalisiert. 
	 * @param 2D-Float-Array (datasetArray), welches das Bild enthaelt
	 * @param Ein Float-Wert, der den kleinsten Farbwert des Bildes enthaelt. 
	 * 			Dieser wird zur Normalisierung benoetigt.
	 * @param Ein Float-Wert, der den groeßten Farbwert des Bildes enthaelt. 
	 * @return Ein 2D-Float-Array, welches die normalisierten Farbwerte des Bildes enthaelt.
	 */
	public static int[][] normaliseValues(float[][] datasetArray, float lowestPixel, float highestPixel) {
		int[][] datasetArrayNormalised = new int[datasetArray.length][datasetArray[0].length];
		for(int i = 0; i < datasetArray.length; i++) {
			for(int j = 0; j < datasetArray[0].length; j++) {
				datasetArrayNormalised[i][j] = (int)(((datasetArray[i][j]-lowestPixel)*(255 - 0)/(highestPixel-lowestPixel)+0));
			}
		}
		return datasetArrayNormalised;
	}
	
	/**
	 * Die Farbwerte des Bildes werden auf eine Grauskala konvertiert.
	 * @param 2D-Float-Array (datasetArrayNormalised), welches das Bild enthaelt.
	 * @return Ein 2D-Float-Array, welches, die geaendertwen Farbwerte enthaelt.
	 */
	public static int[][] convertToGreyscale(int[][] datasetArrayNormalised) {
		// Ein 2D-int-Array wird initialisert
		int[][] datasetArrayRGB = new int[datasetArrayNormalised.length][datasetArrayNormalised[0].length];
		// Das 2D-int-Array wird befuellt
		for(int i = 0; i < datasetArrayNormalised.length; i++) {
			for(int j = 0; j < datasetArrayNormalised[0].length; j++) {
				// Formel zum konvertieren
				datasetArrayRGB[i][j] = (datasetArrayNormalised[i][j]) + (datasetArrayNormalised[i][j] <<8) + (datasetArrayNormalised[i][j] <<16);
			}
		} return datasetArrayRGB;
	}
	
	/**
	 * Die Methode verbindet zwei Bild-Baender mit Integer-Werten zu einem.
	 * @param 2D-int-Array (datasetArrayNormalised1), welches einem Bild-Band entspricht
	 * @param 2D-int-Array (datasetArrayNormalised2), welches dem anderen Bild-Band entspricht 
	 * @return 2D-int-Array, welches dem verbundenen Bild-Band entspricht
	 */
	public static int[][] connectNormalised(int[][] datasetArrayNormalised1, int[][] datasetArrayNormalised2){
		// Ein 2D-int-Array wird initialisert
		int[][] datasetArrayConnected = new int[datasetArrayNormalised1.length][datasetArrayNormalised1[0].length];
		// Das 2D-int-Array wird befuellt
		for(int i=0; i<datasetArrayNormalised1.length; i++) {
			for(int j=0; j<datasetArrayNormalised1[i].length; j++) { 
				// Formel zum verknüpfen der Baender
				datasetArrayConnected[i][j] = (datasetArrayNormalised1[i][j] + datasetArrayNormalised1[i][j]) / 2;
			}
		} return datasetArrayConnected;
	}

	/**
	 * Schreibt ein normalisiertes Array in ein .png 
	 * @param 2D-int-Array (datasetArrayRGB)
	 * @param 2D-int-Array (outputpath) 
	 * @param Den Dateinamen als String (filename)
	 */
	public static void arrayToImage(int [][] datasetArrayRGB, String outputpath, String filename) {
		// Ein BufferedImage wird initialisiert
		BufferedImage outputImage = new BufferedImage(datasetArrayRGB[0].length, datasetArrayRGB.length, BufferedImage.TYPE_INT_RGB);		
		// Das BufferedImage wird befuellt
		for(int i = 0; i < datasetArrayRGB.length; i++) {
			for(int j = 0; j < datasetArrayRGB[0].length; j++) {
		    	outputImage.setRGB(j, i, (datasetArrayRGB[i][j]));
		    }
		}
		// Eine Date wird initialisiert 
		File file = new File(outputpath + filename);
		try {
			ImageIO.write(outputImage, "png", file);	
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Der groeßte Pixel-Wert im Bild wird gefunden und ausgegeben
	 * @param Eine eindimensionals Float-Array (inputArray), enthaelt Bildwerte
	 * @return Ein Float-Wert, der den groeßten Pixelwert enthaelt
	 */
	public static float getMax(float[] inputArray) { 
		float maxValue = inputArray[0]; // Initialisierung des Ausgabewertes
		for(int i=1;i < inputArray.length;i++){ 
			if(inputArray[i] > maxValue){ 
		    	maxValue = inputArray[i]; 
		    } 
		} return maxValue; 
	 }
	 
	/**
	 * Der groeßte Pixel-Wert im Bild wird gefunden und ausgegeben
	 * @param Ein 2D-Float-Array (inputArray), enthaelt Bildwerte
	 * @return Ein Float-Wert, der den groeßten Pixelwert enthaelt
	 */
	public static float getMax(float[][] inputArray) { 
		float maxValue = inputArray[0][0]; // Initialisierung des Ausgabewertes
		for(int i=1;i < inputArray.length;i++){ 
			for(int j=0;j<inputArray[i].length;j++) {
				if(inputArray[i][j] > maxValue){ 
		        maxValue = inputArray[i][j]; 
				} 
			}
		 } return maxValue; 
	 }
	  
	/**
	 * Der kleinste Pixel-Wert im Bild wird gefunden und ausgegeben
	 * @param Eine eindimensionals Float-Array (inputArray), enthaelt Bildwerte
	 * @return Ein Float-Wert, der den kleinsten Pixelwert enthaelt
	 */
	 public static float getMin(float[] inputArray){ 
		 float minValue = inputArray[0]; // Initialisierung des Ausgabewertes
		 for(int i=1;i<inputArray.length;i++){ 
			 if(inputArray[i] < minValue){ 
				 minValue = inputArray[i]; 
		     } 
		 } return minValue; 
	  } 
	  
	 /**
	  * Der kleinste Pixel-Wert im Bild wird gefunden und ausgegeben
	  * @param Eine 2D-Float-Array (inputArray), enthaelt Bildwerte
	  * @return Ein Float-Wert, der den kleinsten Pixelwert enthaelt
	  */
	  public static float getMin(float[][] inputArray){ 
		  float minValue = inputArray[0][0]; // Initialisierung des Ausgabewertes
		  for(int i=0;i<inputArray.length;i++){ 
			  for(int j=0;j<inputArray[i].length;j++) {
				  if(inputArray[i][j] < minValue){ 
					  minValue = inputArray[i][j]; 
				  }
		      } 
		  } return minValue; 
	  } 
}
