package org.lakedetection;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ArrayUtils {

	/* || Bilder �bereinanderlegen ||
	 * 
	 * Es liegen 2 2D-Arrays vor. 
	 * Nun sollen beide Arrays durchlaufen werden 
	 * und die Werte an den jeweils gleichen Pos. miteinander addiert 
	 * und durch 2 dividiert werden. 
	 * */

	public float[][] connect(float[][] b1, float[][] b2){
		
		float[][] a = new float[b1.length][b1[0].length];
		
		for(int i=0; i<b1.length; i++) {
			for(int j=0; j<b1[i].length; j++) { 
				a[i][j] = (b1[i][j] + b2[i][j]) / 2;
			}
		} 
		return a;
	}

	/// Doku einfuegen
	public int[][] normaliseValues(float[][] datasetArray, int lowestPixel, int highestPixel) {
		int[][] datasetArrayNormalised = new int[datasetArray.length][datasetArray[0].length];
		int pixelCounter = 0;
		for(int i = 0; i < datasetArray.length; i++) {
			for(int j = 0; j < datasetArray[0].length; j++) {
				datasetArrayNormalised[i][j] = ((int) ((datasetArray[i][j]-lowestPixel)*(255 - 0)/(highestPixel-lowestPixel)+0));
				pixelCounter += 1;
				//System.out.println(pixelCounter + " pixels normalised...");
			}
		}
		System.out.println("array normalised!");
		return datasetArrayNormalised;
	}
	
	public static int[][] convertToRGB(int[][] datasetArrayNormalised) {
		int[][] datasetArrayRGB = new int[datasetArrayNormalised.length][datasetArrayNormalised[0].length];
		int pixelCounter = 0;
		for(int i = 0; i < datasetArrayNormalised.length; i++) {
			for(int j = 0; j < datasetArrayNormalised[0].length; j++) {
				datasetArrayRGB[i][j] = (datasetArrayNormalised[i][j]) + (datasetArrayNormalised[i][j] << 8) + (datasetArrayNormalised[i][j]<< 16);
				pixelCounter += 1;
				//System.out.println(pixelCounter + " pixels normalised...");
			}
		}
		System.out.println("array converted to RGB!");
		return(datasetArrayRGB);
	}

	//Schreibt ein normalisiertes Array in ein .png //!!!!!AusgabePfad anpassen
	public static void arrayToImage(int [][] datasetArrayRGB, String outputpath, String filename) {
		BufferedImage outputImage = new BufferedImage(datasetArrayRGB[0].length, datasetArrayRGB.length, BufferedImage.TYPE_INT_RGB);
		
		 int pixelCounter = 0;
		 for(int i = 0; i < datasetArrayRGB.length; i++) {
		        for(int j = 0; j < datasetArrayRGB[0].length; j++) {
		        	outputImage.setRGB(j, i, datasetArrayRGB[i][j]);
		        	pixelCounter += 1;
		        	//System.out.println(pixelCounter + " pixels written...");
		        }
		 }
		 

		 File file = new File(outputpath + filename);
		 try {
			ImageIO.write(outputImage, "png", file);
			System.out.println("image written!");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	 //groessten Pixelwert ausgeben
	  public static float getMax(float[] inputArray) { 
		  float maxValue = inputArray[0]; 
		    for(int i=1;i < inputArray.length;i++){ 
		      if(inputArray[i] > maxValue){ 
		         maxValue = inputArray[i]; 
		      } 
		    } 
		    return maxValue; 
	  }
		 
	  //kleinsten Pixelwert ausgeben
	  public static float getMin(float[] inputArray){ 
		    float minValue = inputArray[0]; 
		    for(int i=1;i<inputArray.length;i++){ 
		      if(inputArray[i] < minValue){ 
		        minValue = inputArray[i]; 
		      } 
		    } 
		    return minValue; 
	  } 
}
