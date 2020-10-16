package org.lakedetection;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Die Klasse enthaelt die Rasteroperationen, die verwendet werden, um die Bilder zu bearbeiten. 
 * @author Josefina Balzer & Alexander Pilz
 * @version 1.0
 */
public class ROPs {

	/**
	 * Hilfsfunktion zum Anzeigen einer Matrix 15x15. Dies ist sinnvoll für Tests.
	 * @param 2D-Float-Array, welches Werte enthält, anhand derer getestet wird
	 */
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
	
	/**
	 * Smoothing-Operator mit dem Stoerpixel eliminiert werden 
	 * @param 2D-Float-Array (Bild)
	 * @return 2D-Float-Array (bearbeitetes Bild)
	 */
	public float[][] smoothing(float[][] input){
		float[][] output = new float[input.length][input[0].length];
		for(int i=1; i<(input.length)-1; i++) {
			for(int j=1; j<(input[i].length)-1; j++) {
				// Aus den Pixelwerten werden jeden betrachteten Punkt wird der Durchschnitt der insgesamt
				// neun Werte errechet und für den mittleren Wert eingetragen. 
				float x = ( input[i-1][j-1] + input[i-1][j] + input[i-1][j+1] + input[i][j-1] + input[i][j] 
						+ input[i][j+1] + input[i+1][j-1] + input[i+1][j] + input[i+1][j+1]) / 9;
				output[i][j] = x; 
			}
		}
		return output;
	}
	
	/**
	 * Methode zur besseren Erkennung von Wasserflaechen indem sie anders eingefaerbt werden, 
	 * als die Landflaechen.
	 * @param Ein 2D-int-Array (Farbwerte der Baender) 
	 * @param Ein Schwellwert vom Typ int
	 * @return Ein 2D-int-Array mit den eingefaerbten Werten
	 */
	public int[][] convertToBinaryColorScheme(int[][] input, int schwellwert) {
		int[][] output = new int[input.length][input[0].length]; // Initialisierung eines Output-Arrays
		for(int i=0; i<input.length; i++) {
			for(int j=0; j<input[i].length; j++) {
				// Jeder Pixel wird komplett Schwarz oder Weiß gefärbt
				if(input[i][j] <= schwellwert) {
					output[i][j] = 0; // Fuellen des Arrays
				} else output[i][j] = 255; 
			}
		} return output;
	}
	
	/**
	 * Die Wasserflaeche wird detektiert und ggf. eingefaerbt
	 * @param 2D-Array (binaer)
	 * @param Es wird ein Startpixel muss angegeben werden. Dazu wird ein x-Wert (px) benoetigt und
	 * @param ein y-Wert (py)
	 */
	public void scan(int[][] input, int px, int py) {
		int origin_color = input[px][py];
		ArrayList<Point> pointList = new ArrayList<Point>();  
		input[px][py] = origin_color;	
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
			}
			pointList.remove(0);
		}
	}
	
	/**
	 * Die Methode scannt die Werte in einer horizontalen Linie. Die Methode wird zum Detektieren der
	 * Wasserflaeche benoetigt.
	 * @param 2D-int-Array mit binaeren Werten 
	 * @param Es wird ein Startpixel muss angegeben werden. Dazu wird ein x-Wert (px) benoetigt und
	 * @param ein y-Wert (py)
	 * @param Eine ArrayList mit Punkten (pointList)
	 */
	public void horizontalScanLine(int[][] input, int px, int py, ArrayList<Point> pointList) {
		int basepy = py;
		int boundY = input[0].length;
		 
		if(input[px][py] == 0) {
			while(py < boundY && input[px][py] == 0) {
				input[px][py] = 150;
				pointList.add(new Point(px, py));
				py++;
			}
			py = basepy;
			input[px][py] = 0;
			while(py >= 0 && input[px][py] == 0) {
				input[px][py] = 150;
				pointList.add(new Point(px, py));
				py--;
			}
		}
	}
	
	/**
	 * Die Methode scannt die Werte in einer horizontalen Linie. Die Methode wird zum Detektieren der
	 * Wasserflaeche benoetigt.
	 * @param 2D-int-Array mit binaeren Werten 
	 * @param Es wird ein Startpixel muss angegeben werden. Dazu wird ein x-Wert (px) benoetigt und
	 * @param ein y-Wert (py)
	 * @param Eine ArrayList mit Punkten (pointList)
	 */
	public void verticalScanLine(int[][] input, int px, int py, ArrayList<Point> pointList) {
		int basepx = px;
		int boundX = input.length;
		if(input[px][py] == 0 || input[px][py] == 150) {
			while(px < boundX && (input[px][py] == 0 || input[px][py] == 150)) {
				if (input[px][py] == 0) {	
					pointList.add(new Point(px, py));
				} 
				px++;
			}
			px = basepx;
			while(px >= 0 && (input[px][py] == 0 || input[px][py] == 150)) {
				if (input[px][py] == 0) {
					pointList.add(new Point(px, py));
				}
				px--;
			}
		}
	}
	
	/**
	 * Die Methode faerbt die Wasserflaeche Blau ein.
	 * @param Binaeres 2D-int-Array
	 */
	public static void waterrize(int[][] input) {
		for(int i = 0; i < input.length; i++) {
			for(int j = 0; j < input[0].length; j++) {
				if(input[i][j] == 255) {
					int shiftedColor = (255 << 16) + (255 << 8) + 255;
					input[i][j] = shiftedColor;
				}
			}
		}
	}
	
	/**
	 * Die Wasserflaeche wird berechnet anhand der gezaehlten Pixel.
	 * @param 2D-Array
	 * @return Integer-Wert, welcher die Groeße der Wasserflaeche angibt
	 */
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
	 * Die Methode geht mit einem Median-Filter über ein Bild-Array
	 * @param 2D-Float-Array (Bild)
	 * @return 2D-Float-Array (gefiltertes Bild)
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
				newimg[i-1][j-1] = median;
			}
		}
		return newimg;
	}
	
	/**
	 * Gauss-Filter mit 7x7-Matrix.
	 * Innerhalb einer 7x7-Matrix wird der Durchschnitt der Werte aller Zellen mit unterschiedlicher Gewichtung
	 * berechnet. 
	 * @param 2D-Float-Array (Bild)
	 * @return 2D-Float-Array (gefiltertes Bild)
	 */
	public float[][] gaussFilter(float[][] img){
		float[][] output = new float[img.length-6][img[0].length-6];
		for(int i=3; i<(img.length)-3; i++) {
			for(int j=3; j<(img[i].length)-3; j++) {
				float x = (( (img[i-3][j-3] + img[i-3][j+3] + img[i+3][j-3] + img[i+3][j+3]) 
									* 1)  // Gewicht = 1
						
						+ ( (  	   img[i-3][j-2] + img[i-3][j-1] + img[i-3][j  ] + img[i-3][j+1] + img[i-3][j+2]  +  img[i-2][j-2]  
								+  img[i+3][j-2] + img[i+3][j-1] + img[i+3][j  ] + img[i+3][j+1] + img[i+3][j+2]  +  img[i+2][j+2]  
								+  img[i-2][j-3] + img[i-1][j-3] + img[i  ][j-3] + img[i+1][j-3] + img[i+2][j-3]  +  img[i+2][j-2]  
								+  img[i-2][j+3] + img[i-1][j+3] + img[i  ][j+3] + img[i+1][j+3] + img[i+2][j+3]  +  img[i-2][j+2] ) 
									* 2) // Gewicht = 2
						+ ( (	   img[i-2][j-1] + img[i-2][j  ] + img[i-2][j+1]  
								+  img[i+2][j-1] + img[i+2][j  ] + img[i+2][j+1]  
								+  img[i-1][j-2] + img[i  ][j-2] + img[i+1][j-2]  
								+  img[i-1][j+2] + img[i  ][j+2] + img[i+1][j+2]) 
									* 3) // Gewicht = 3
						+ ( (      img[i-1][j-1] + img[i-1][j  ] + img[i-1][j+1] 
								+  img[i  ][j-1] + img[i  ][j] + img[i  ][j+1] 
								+  img[i+1][j-1] + img[i+1][j] + img[i+1][j+1]) 
									* 5)     // Gewicht = 5
						) / 128;	  
						
				output[i-3][j-3] = x; 
			}
		} return output;	
	}
	
	/**
	 * Punkt-Klasse fuer interne Zwecke
	 * @author Alexander Pilz
	 */
	public static class Point {
		int x;
		int y;
		
		public Point(int x, int y) {
			this.x = x;
			this.y = y;
		}
		
	}

}


