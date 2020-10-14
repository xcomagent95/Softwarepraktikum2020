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

	

	
	// Hilfsfunktion zum Anzeigen einer Matrix 15x15
	
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
	

	
	// Funktion die die Flaeche glaettet -- Test winputr erfolgreich
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
	 * und alle Wasserflaechen schwarz einfaerben.
	 * */
	//In bin�res Farbschema �berf�hren
	public int[][] convertTobinaryColorScheme(int[][] input, int schwellwert) {

		int[][] output = new int[input.length][input[0].length];
		for(int i=0; i<input.length; i++) {
			for(int j=0; j<input[i].length; j++) {
				if(input[i][j] <= schwellwert) {
					output[i][j] = 0;
				}
				else output[i][j] = 255; 
			}
		}
		return output;
	}
	
	//Scannen
	public void scan(int[][] input, int px, int py) {
		int origin_color = input[px][py];
		ArrayList<Point> pointList = new ArrayList<Point>();  
		input[px][py] = origin_color;	
		int counter = 0;
		int boundX = input.length;
		int boundY = input[0].length;
		
		pointList.add(new Point(px, py));
		
		while(!pointList.isEmpty()) {
			int x = pointList.get(0).x;
			int y = pointList.get(0).y;
			if(pointList.get(0) != null) {
				if((x-1 >= 0 && input[x-1][y] != 150) || (x+1 < boundX && input[x+1][y] != 150)) {
					verticalScanLine(input,  x, y, pointList);
				}
				if((y-1 >= 0 && input[x][y-1] != 150) || (y+1 < boundY && input[x][y+1] != 150)) {
					horizontalScanLine(input, x,  y, pointList);
				}
				if(input[x][y] == 150) {
					counter += 1;
				}
				int[][] output = input;
				/*
				if(counter % 500000 == 0) {
					int[][] connectedBandsNormalisedGaussBlack = makeBlack(output, 70);
					waterrize(output, 150);
					ArrayUtils.arrayToImage(output, "E:\\Raster\\TestBilder\\", "median" + counter + ".png");
				}
				*/
			}
			pointList.remove(0);
		}
	}
	
	//Horizontal Scannen
	public void horizontalScanLine(int[][] input, int px, int py, ArrayList<Point> pointList) {
		int basepy = py;
		int counter = 0;
		int boundX = input.length;
		int boundY = input[0].length;
		 
		if(input[px][py] == 0) {
			while(py < boundY && input[px][py] == 0) {
				input[px][py] = 150;
				counter++;
				pointList.add(new Point(px, py));
				py++;
			}
			py = basepy;
			input[px][py] = 0;
			while(py >= 0 && input[px][py] == 0) {
				input[px][py] = 150;
				counter++;
				pointList.add(new Point(px, py));
				py--;
			}
		}
	}
	
	//Vertikal Scannen
	public void verticalScanLine(int[][] input, int px, int py, ArrayList<Point> pointList) {
		int basepx = px;
		int basepy = py;
		int counter = 0;
		int boundX = input.length;
		int boundY = input[0].length;
		
		if(input[px][py] == 0 || input[px][py] == 150) {
			while(px < boundX && (input[px][py] == 0 || input[px][py] == 150)) {
				counter++;
				if (input[px][py] == 0) {	
					pointList.add(new Point(px, py));
				} 
				px++;
			}
			px = basepx;
			while(px >= 0 && (input[px][py] == 0 || input[px][py] == 150)) {
				counter++;
				if (input[px][py] == 0) {
					pointList.add(new Point(px, py));
				}
				px--;
			}
		}
	}
	
	public static void waterrize(int[][] input, int color) {
		int pixelCounter = 0;
		for(int i = 0; i < input.length; i++) {
			for(int j = 0; j < input[0].length; j++) {
				if(input[i][j] == 255) {
					int shiftedColor = (255 << 16) + (255 << 8) + 255;
					input[i][j] = shiftedColor;
					pixelCounter++;
				}
			}
		}
		//System.out.println(pixelCounter);
	}
	
	//Wasserflaeche berechnen
	public static int calculateSurface(int[][] input) {
		int counter = 0;
		for(int i = 0; i < input.length; i++) {
			for(int j = 0; j < input[0].length; j++) {
				if(input[i][j] == 150) {
					counter += 100;
				}
			}
		}
		return counter;
	}

	 /**
	  * Function takes an image and filtering it with an array
	  * @param img float[][]
	  * @return float[][] Input image filterd
	  */
	public float[][] medianFilter(float[][] img){
		float[][] newimg = new float[img.length-2][img[0].length-2];
		for(int i = 1; i < img.length - 1; i++) {
			for(int j = 1; j < img[i].length - 1; j++) {
				float[] medianarray = {
						img[i - 1][j - 1], img[i - 1][j], img[i - 1][j + 1], 
						img[i    ][j - 1], img[i    ][j], img[i    ][j + 1],
						img[i + 1][j - 1], img[i + 1][j], img[i + 1][j + 1]};
				
				Arrays.sort(medianarray);
				float median = medianarray[4];
				
				/*
				 System.out.println(
						" Feld i:  " + i + ", Feld j: " + j + 
						" , Median Array: " + Arrays.toString(medianarray) +
						" , Median: " + median);
				*/
				newimg[i-1][j-1] = median;
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
		float[][] output = new float[input.length-6][input[0].length-6];
		for(int i=3; i<(input.length)-3; i++) {
			for(int j=3; j<(input[i].length)-3; j++) {
				float x = (( (input[i-3][j-3] + input[i-3][j+3] + input[i+3][j-3] + input[i+3][j+3]) 
									* 1)  // Gewicht = 1
						
						+ ( (  	   input[i-3][j-2] + input[i-3][j-1] + input[i-3][j  ] + input[i-3][j+1] + input[i-3][j+2]  +  input[i-2][j-2]  
								+  input[i+3][j-2] + input[i+3][j-1] + input[i+3][j  ] + input[i+3][j+1] + input[i+3][j+2]  +  input[i+2][j+2]  
								+  input[i-2][j-3] + input[i-1][j-3] + input[i  ][j-3] + input[i+1][j-3] + input[i+2][j-3]  +  input[i+2][j-2]  
								+  input[i-2][j+3] + input[i-1][j+3] + input[i  ][j+3] + input[i+1][j+3] + input[i+2][j+3]  +  input[i-2][j+2] ) 
									* 2) // Gewicht = 2
						+ ( (	   input[i-2][j-1] + input[i-2][j  ] + input[i-2][j+1]  
								+  input[i+2][j-1] + input[i+2][j  ] + input[i+2][j+1]  
								+  input[i-1][j-2] + input[i  ][j-2] + input[i+1][j-2]  
								+  input[i-1][j+2] + input[i  ][j+2] + input[i+1][j+2]) 
									* 3) // Gewicht = 3
						+ ( (      input[i-1][j-1] + input[i-1][j  ] + input[i-1][j+1] 
								+  input[i  ][j-1] + input[i  ][j] + input[i  ][j+1] 
								+  input[i+1][j-1] + input[i+1][j] + input[i+1][j+1]) 
									* 5)     // Gewicht = 5
						) / 128;	  
						
				output[i-3][j-3] = x; 
			}
		}
		return output;
		
		// Test ergibt, dass der Filter korrekt arbeitet
	
	}
	
	//Punkt-Klasse fuer interne Zwecke
	public static class Point {
		int x;
		int y;
		
		public Point(int x, int y) {
			this.x = x;
			this.y = y;
		}
		
	}

}


