package org.lakedetection;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ArrayUtils {

	/* || Bilder uebereinanderlegen ||
	 * 
	 * Es liegen 2 2D-Arrays vor. 
	 * Nun sollen beide Arrays durchlaufen werden 
	 * und die Werte an den jeweils gleichen Pos. miteinander addiert 
	 * und durch 2 dividiert werden. 
	 * */

	/// Doku einfuegen
	public static int[][] normaliseValues(float[][] datasetArray, float lowestPixel, float highestPixel) {
		int[][] datasetArrayNormalised = new int[datasetArray.length][datasetArray[0].length];
		//int pixelCounter = 0;
		for(int i = 0; i < datasetArray.length; i++) {
			for(int j = 0; j < datasetArray[0].length; j++) {
				datasetArrayNormalised[i][j] = (int)(((datasetArray[i][j]-lowestPixel)*(255 - 0)/(highestPixel-lowestPixel)+0));
				//pixelCounter++;
				//System.out.println(pixelCounter + " pixels normalised...");
			}
		}
		//System.out.println("array normalised!");
		return datasetArrayNormalised;
	}
	
	public static int[][] convertToGreyscale(int[][] datasetArrayNormalised) {
		int[][] datasetArrayRGB = new int[datasetArrayNormalised.length][datasetArrayNormalised[0].length];
		//int pixelCounter = 0;
		for(int i = 0; i < datasetArrayNormalised.length; i++) {
			for(int j = 0; j < datasetArrayNormalised[0].length; j++) {
				datasetArrayRGB[i][j] = (datasetArrayNormalised[i][j]) + (datasetArrayNormalised[i][j] <<8) + (datasetArrayNormalised[i][j] <<16);
				//pixelCounter += 1;
				//System.out.println(pixelCounter + " pixels normalised...");
			}
		}
		//System.out.println("array converted to RGB!");
		return(datasetArrayRGB);
	}
	
	// Dieselbe Methode "connect", doch diese arbietet nicht mit zwei float[][] sondern mit int[][]
	public static int[][] connectNormalised(int[][] datasetArrayNormalised1, int[][] datasetArrayNormalised2){
		int[][] datasetArrayConnected = new int[datasetArrayNormalised1.length][datasetArrayNormalised1[0].length];
		for(int i=0; i<datasetArrayNormalised1.length; i++) {
			for(int j=0; j<datasetArrayNormalised1[i].length; j++) { 
				datasetArrayConnected[i][j] = (datasetArrayNormalised1[i][j] + datasetArrayNormalised1[i][j]) / 2;
			}
		} 
		return datasetArrayConnected;
	}

	//Schreibt ein normalisiertes Array in ein .png 
	public static void arrayToImage(int [][] datasetArrayRGB, String outputpath, String filename) {
		BufferedImage outputImage = new BufferedImage(datasetArrayRGB[0].length, datasetArrayRGB.length, BufferedImage.TYPE_INT_RGB);		
		 //int pixelCounter = 0;
		 for(int i = 0; i < datasetArrayRGB.length; i++) {
		        for(int j = 0; j < datasetArrayRGB[0].length; j++) {
		        	outputImage.setRGB(j, i, (datasetArrayRGB[i][j]));
		        	//pixelCounter += 1;
		        	//System.out.println(pixelCounter + " pixels written...");
		        }
		 }
		 

		 File file = new File(outputpath + filename);
		 try {
			ImageIO.write(outputImage, "png", file);	
			//System.out.println("image written!");
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
	  
	  public static float getMax(float[][] inputArray) { 
		  float maxValue = inputArray[0][0]; 
		    for(int i=1;i < inputArray.length;i++){ 
		      for(int j=0;j<inputArray[i].length;j++) {
		    	if(inputArray[i][j] > maxValue){ 
		         maxValue = inputArray[i][j]; 
		    	} 
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
	  
	  public static float getMin(float[][] inputArray){ 
		    float minValue = inputArray[0][0]; 
		    for(int i=0;i<inputArray.length;i++){ 
		    	for(int j=0;j<inputArray[i].length;j++) {
		    		if(inputArray[i][j] < minValue){ 
				        minValue = inputArray[i][j]; 
		    	}
		      } 
		    } 
		    return minValue; 
	  } 
}
