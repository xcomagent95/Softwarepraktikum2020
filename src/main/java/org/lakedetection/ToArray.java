package org.lakedetection;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.media.jai.PlanarImage;

import org.esa.snap.core.dataio.ProductIO;
import org.esa.snap.core.datamodel.Product;
import org.esa.snap.core.image.ImageManager;

import com.bc.ceres.core.PrintWriterProgressMonitor;
import com.bc.ceres.glevel.MultiLevelImage;

public class ToArray {
	private int arrayHeight; //Hoehe des Rasters
	private int arrayWidth; //Breite des Rasters
	private float[][] datasetArray; //Array von Float Werten fuer die Speicherung von Farbwerten

	private int requestedCornerX; //X-Koordinate der oberen linken Ecke
	private int requestedCornerY; //Y-Koordinate der oberen linken Ecke
	private int requestedHeight; //Hoehe des angefragten Bildausschnitts
	private int requestedWidth; //Breite des angefragten Bildausschnitts

	private String requested_Band; //abgefragtes Band
	
	//ToArray Konstruktor
	//Uebergeben werden muss der Datensatz als Loadzip, das geuenschte band als String, sowie die Eckdaten zum angefragten Bildausschnitt
	public ToArray(Loadzip dataset, String band, int requestedX, int requestedY, int height, int width) {
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
		//Hier wird aus dem Datensatz das entsprechende Band als buffredImage angefragt und von diesem ein Subimage gelesen
		MultiLevelImage geoImage = product.getBand(this.requested_Band).getGeophysicalImage();
	;// .getColorModel();
		BufferedImage image = geoImage.getAsBufferedImage(rect, geoImage.getColorModel());
		System.out.println("image buffered!");
		//Raster aus buffered Image hohlen und Farbwerte in Array speichern
		//Hier wird ueber eine Schleife das 2D-Array mit den korespondierenden Pixelwerten aus dem Subimage gefuellt
		Raster raster = image.getData();
        System.out.println("raster requested!");
		for(int i = 0; i < requestedHeight; i++) {
			for(int j = 0; j < requestedWidth; j++) {
				 datasetArray[i][j] = raster.getSampleFloat(i, j, 0); //Bandnummer 0
			}
		}
        System.out.println("raster read from image!");
	}

	//Tester 10x10 Area
	public void probeArray() {
		for(int i = 0; i < 10; i++) {
			for(int j = 0; j < 10; j++) {
				System.out.println(datasetArray[i][j]);
			}
		}
	}
}
