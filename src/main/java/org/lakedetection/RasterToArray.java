package org.lakedetection;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

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


	//Setzt alle Pixelwerte welche groesser sind als der Minimalwert auf 0
	public void filterArrayLowestPixel() {
		for(int i = 0; i < this.arrayHeight; i++) {
			for(int j = 0; j < this.arrayWidth; j++) {
				if(this.datasetArray[i][j] != lowestPixel) {
					datasetArray[i][j] = 0.0f;
				}
			}
		}
	}

	//////// Sollte besser ein int[][] entgegennehmenn und nciht auf dem internene band arbeiten
	//Normalisiert die Pixelwerte auf eine Skala von 0 bis 255
	public void convertToGreyscale() {
		datasetArrayNormalised = new int[this.arrayHeight][this.arrayWidth];
		int pixelCounter = 0;
		for(int i = 0; i < this.arrayHeight; i++) {
			for(int j = 0; j < this.arrayWidth; j++) {
				datasetArrayNormalised[i][j] = ((int) ((datasetArray[i][j]-this.lowestPixel)*(255 - 0)/(this.highestPixel-this.lowestPixel)+0)) +
						(((int) ((datasetArray[i][j]-this.lowestPixel)*(255 - 0)/(this.highestPixel-this.lowestPixel)+0)) << 8) +
						(((int) ((datasetArray[i][j]-this.lowestPixel)*(255 - 0)/(this.highestPixel-this.lowestPixel)+0)) << 16);
				pixelCounter += 1;
				//System.out.println(pixelCounter + " pixels normalised...");
			}
		}
		System.out.println("array normalised!");
	}

	// Getter
	public int[][] getConvertedArray(){
		return datasetArrayNormalised;
	}

	//Schreibt ein normalisiertes Array in ein .png // arbeitet auf int[][]
	public void arrayToImage() {
		BufferedImage outputImage = new BufferedImage(this.arrayWidth, this.arrayHeight, BufferedImage.TYPE_INT_RGB);

		 int pixelCounter = 0;
		 for(int i = 0; i < this.arrayHeight; i++) {
		        for(int j = 0; j < this.arrayWidth; j++) {
		        	outputImage.setRGB(j, i, this.datasetArrayNormalised[i][j]);
		        	pixelCounter += 1;
		        	//System.out.println(pixelCounter + " pixels written...");
		        }
		 }

		 File file = new File("/Users/josefinabalzer/Desktop/test.png");
		 try {
			ImageIO.write(outputImage, "png", file);
			System.out.println("image written!");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Analog zu rops.connect(a,b), arbeitet allerdings mit int[][] und nicht mit float[][]
	public float[][] connectP(float[][] a, float[][] b){
		float[][] c = new float[a.length][a[0].length];
		for(int i=0; i<a.length; i++) {
			for(int j=0; j<a[i].length; j++) {
				c[i][j] = (a[i][j] + b[i][j]) / 2;
			}
		}
		return c;
	}
}

