package org.lakedetection;

import java.util.ArrayList;
import java.util.Arrays;

public class ROPs {

	/* Die Klasse enthält die Rasteroperationen, die verwendet werden, um die Bilder zu bearbeiten. 
	 * 
	 * Zunächst werden die beiden Bänder verknüpft miteinander (connect(a,b)).
	 * 
	 * Daraufhin wird das Bild geglättet, um Störpixel zu eliminieren (smoothing(c)).
	 * 
	 * Zuletzt werden Wasserflächen Schwarz eingefärbt und alles andere Weiß.
	 * HIERBEI MUSS DER SCWELLWERT NOCH ANGEPASST WERDEN!!!
	 * */

	

	
	// Hilfsfkt. zum Anzeigen einer Matrix 15x15
	
	public void show(float[][] input) {

		for (int i=0; i<input.length; i++)
		{
			System.out.print("[");
			for (int j=0; j<input[i].length; j++)
			{
				System.out.print(input[i][j] + ",");
			}
			System.out.print("]\n");
		}
	}
	

	
	// Fkt. die die Fl�che gl�ttet -- Test winputr erfolgreich
	 
	public float[][] smoothing(float[][] input){
		float[][] output = new float[input.length][input[0].length];
		
		for(int i=1; i<(input.length)-1; i++) {
			for(int j=1; j<(input[i].length)-1; j++) {
				float x = ( input[i-1][j-1] + input[i-1][j] + input[i-1][j+1] + input[i][j-1] + input[i][j] + input[i][j+1] + input[i+1][j-1] + input[i+1][j] + input[i+1][j+1]) / 9;
				output[i][j] = x; 
			}
		}
		return output;
	}
	

	
	///////////////////////
	/* || Wasserfl�chen erkennen ||
	 * 
	 * Mit der Schwellwertfunktion arbeiten 
	 * und alle Wasserfl�chen schwarz einf�rben.
	 * */
	
	public int[][] makeBlack(int[][] input, int schwellwert) {
		int[][] output = new int[input.length][input[0].length];
		for(int i=0; i<input.length; i++) {
			for(int j=0; j<input[i].length; j++) {
				if(input[i][j] <= schwellwert) { // Schwellwert muss noch gesetzt werden
					output[i][j] = 0;
				}
				else output[i][j] = 255; // max. ist mittelwert der maxima von vh und vv (4142,5) __ jetzt doch ge�ndert auf 255
			}
		}
		return output;
	}
	
	public void verticalScanList(int[][] input, int px, int py) {
		ArrayList<Integer> horizontalList=new ArrayList<Integer>();  
		int basepy = py;
		int counter = 0;
		if(input[px][py] == 0) {
			while(input[px][py] == 0) {
				horizontalList.add(input[px][py]);
				input[px][py] = 150;
				py++;
				counter++;
			}
			py = basepy;
			input[px][py] = 0;
			while(input[px][py] == 0) {
				horizontalList.add(input[px][py]);
				input[px][py] = 150;
				py--;
				counter++;
			}
			System.out.println("Scanline: " + counter);
			input[px][py] = 150;
		}
		else {
		}
	}
	
	public void horizontalScanLine(int[][] input, int px, int py) {
		int basepy = py;
		int counter = 0;
		if(input[px][py] == 0) {
			while(input[px][py] == 0) {
				input[px][py] = 150;
				py++;
				counter++;
			}
			py = basepy;
			input[px][py] = 0;
			while(input[px][py] == 0) {
				input[px][py] = 150;
				py--;
				counter++;
			}
			System.out.println("Scanline: " + counter);
			input[px][py] = 150;
		}
		else {
		}
	}
	
	// Test mithilfe einer beliebigen 5x5-Matrix war erfolgreich
	
	
	 /**
	  * Function takes an image and filtering it with an array
	  * @param img float[][]
	  * @return float[][] Input image filterd
	  */
	public float[][] medianFilter(float[][] img){
		float[][] newimg = new float[img.length][img[0].length];
		System.out.println(Arrays.deepToString(img));
		for(int i = 1; i < img.length - 1; i++) {
			for(int j = 1; j < img[i].length - 1; j++) {
				float[] medianarray = {
						img[i - 1][j - 1], img[i - 1][j], img[i - 1][j + 1], 
						img[i][j - 1], img[i][j], img[i][j + 1],
						img[i + 1][j - 1], img[i + 1][j], img[i + 1][j + 1]};
				
				
				Arrays.sort(medianarray);
				float median = medianarray[4];
				
				/*
				 System.out.println(
						" Feld i:  " + i + ", Feld j: " + j + 
						" , Median Array: " + Arrays.toString(medianarray) +
						" , Median: " + median);
				*/
				newimg[i][j] = median;
				//System.out.println(Arrays.deepToString(newimg));
			}
		}
		
		return newimg;
	}
	
	/**
	 * Adding Original rim Pixel data to a new 2d array.
	 * The Image will keep it size and the inner pixel might be used
	 * for a filter, rim Pixels wont be edited in all of our filter
	 * @param img original image
	 * @param rim size of the filter 
	 * @return 2darray with edges filled with original image Pixelvalues
	 */
	public float[][] rimFiller(float[][] img, int rim){
		float[][] newimg = new float[img.length][img[1].length];
		for(int i = 0; i < img.length; i++) {
			if(i < 0 + rim || i > img.length - 1 - rim) {
				for(int j = 0; j < img[i].length; j++) {
					newimg[i][j] = img[i][j];
				}
			} else {
				for(int j = 0; j < rim; j++) {
					newimg[i][j] = img[i][j];	
					newimg[i][newimg[i].length - 1 - j] = img[i][img[i].length - 1 - j ];
				}
				newimg[i][0] = img[i][0];
				newimg[i][newimg[i].length - 1] = img[i][img[i].length - 1];
			}
		}
		return newimg;
	}
	/* Gauss-Filter mit 7x7-Matrix 
	 * */
	public float[][] gaussFilter(float[][] input){
		float[][] output = new float[input.length][input[0].length];
		for(int i=3; i<(input.length)-3; i++) {
			for(int j=3; j<(input[i].length)-3; j++) {
				float x = (( (input[i-3][j-3] + input[i-3][j+3] + input[i+3][j-3] + input[i+3][j+3]) 
									* 1)  // Gewicht = 1
						
						+ ( (  	   input[i-3][j-2] + input[i-3][j-1] + input[i-3][j  ] + input[i-3][j+1] + input[i-3][j+2]  +  input[i-2][j-2]  
								+  input[i+3][j-2] + input[i+3][j-1] + input[i+3][j  ] + input[i+3][j+1] + input[i+3][j+2]  +  input[i+2][j+2]  
								+  input[i-2][j-3] + input[i-1][j-3] + input[i  ][j-3] + input[i+1][j-3] + input[i+2][j-3]  +  input[i+2][j-2]  
								+  input[i-2][j+3] + input[i-1][j+3] + input[i  ][j+3] + input[i+1][j+3] + input[i+2][j+3]  +  input[i-2][j+2] ) 
									* 2) // Gewicht = 2
						+ ( (input[i-2][j-1] + input[i-2][j  ] + input[i-2][j+1]  
								+  input[i+2][j-1] + input[i+2][j  ] + input[i+2][j+1]  
								+  input[i-1][j-2] + input[i  ][j-2] + input[i+1][j-2]  
								+  input[i-1][j+2] + input[i  ][j+2] + input[i+1][j+2]) 
									* 3) // Gewicht = 3
						+ ( (input[i-1][j-1] + input[i-1][j  ] + input[i-1][j+1] 
								+ input[i  ][j-1] + input[i  ][j] + input[i  ][j+1] 
								+ input[i+1][j-1] + input[i+1][j] + input[i+1][j+1]) 
									* 5)     // Gewicht = 5
						) / 128;	  
						
				output[i][j] = x; 
			}
		}
		return output;
		
		// Test ergibt, dass der Filter korrekt arbeitet
	
	}
	
	int counter = 0;
	public int countBlacks(int[][] input) {
		for(int i=0; i<input.length; i++) {
			for(int j=0; j<input[i].length; j++) {
				if(input[i][j] == 0) counter++;
			}
		}
		return counter;
	}
	
	// RIM überall einbauen
	
}


