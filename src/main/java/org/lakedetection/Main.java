package org.lakedetection;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import org.esa.snap.core.datamodel.PixelPos;

public class Main {

    public static void main(String[] args) throws IOException { //main Methode
  		// Initialisieren der Utils und ROP`s, um spaeter auf diese Methoden zugreifen zu koennen 
  		ArrayUtils connected = new ArrayUtils();
  		ROPs rops = new ROPs();

  		// Initialisieren der Objekts "findsets", indem die Informationen zur gefundenen Datei 
  		// gespeichert werden, um die File zu initialisieren und herunterzuladen.
    	FindSets findsets = new FindSets();
    	//File file = findsets.downloadZip(findsets.search());
    	findsets.search();
    	// --------------- ZUM TESTEN OHNE DOWNLOAD --------------------------------------
    	File file = new File("/Users/josefinabalzer/Desktop/softwarepraktikum2020/S1B_IW_GRDH_1SDV_20201015T052435_20201015T052500_023819_02D439_34BD.zip");
    	// -------------------------------------------------------------------------------
    	System.out.println("search done"); // Kommentar auf der Konsole zur Überprüfung des Fortschritts 

    	// Laden des Datensatzes als Objekt vom Typ Loadzip und dem Namen dataset
    	Loadzip dataset = new Loadzip(file.getAbsolutePath());
    	
    	// Pfade zur Test-Datei auf beiden PCs zur Ueberpruefung von Methoden und Funktionen
    	// ohne diese Datei neu herunterladen zu muessen:
    	// Pfad Josi:
    	//Loadzip dataset = new Loadzip("/Users/josefinabalzer/Desktop/softwarepraktikum2020/S1B_IW_GRDH_1SDV_20201015T052435_20201015T052500_023819_02D439_34BD.zip");
    	// Pfad Alex:
    	//Loadzip dataset = new Loadzip("E:\\Raster\\S1A_IW_GRDH_1SDV_20200307T052505_20200307T052530_031565_03A2FE_508A.zip");
  		
    	// Initialisierung der Objektes georefernce
    	Georeference georeference = new Georeference(dataset.getProduct());
  		
    	// Parameter zur Hoehe und Breite des Bildausschnitts:
    	int tile_width;
    	int tile_height;
    	// Parameter zur angefragten Bildkoordinate oben links des Bildausschnitts:
    	double requestedCoordX;
    	double requestedCoordY;
    	// Parameter zur angefragten Bildkooridnate im Bild und im Wasser:
    	int x_in_lake = -1;
    	int y_in_lake = -1;

    	Boolean color_or_greyscale = true; // Initialisierung der bool'schen Variable bzgl. der Farbskala
    	int color_or_greyscale_int = 1;
    	
    	String outputpath = ""; // Initialisierung des Ausgabepfads

    	// Initialisierung eines Scanners zum Lesen der Benutzereingaben:
    	Scanner consoleScanner = new Scanner(System.in);
    	
    	// Abfragen von Benutzereingaben:
        System.out.println("Enter requested tile height:");
        tile_height = consoleScanner.nextInt(); 
        System.out.println("Enter requested tile with:");
        tile_width = consoleScanner.nextInt();
    	
        PixelPos requestedPos;
        do {
        	System.out.println("Enter requested Coord-X e.g. 53,205902: ");
        	requestedCoordX = consoleScanner.nextDouble();
        	System.out.println("Enter requested Coord-Y e.g. 12,890525: ");
        	requestedCoordY = consoleScanner.nextDouble();
        	requestedPos = georeference.getPixPos(requestedCoordX, requestedCoordY); 
        } while(Double.isNaN(requestedPos.x) || Double.isNaN(requestedCoordY));
        	
        	// e.g //53,213489 12,863988 //53,205902 12,890525
        
    	x_in_lake = tile_height/2;
    	y_in_lake = tile_width/2;

    	// Benutzerabfrage des gewuenschten Farbschemas:
        System.out.println("Color or Greyscale? (1 = Color / 2 = Greyscale)");
        color_or_greyscale_int = consoleScanner.nextInt();

        if(color_or_greyscale_int == 1) {
        	color_or_greyscale = true;
        }
        else {
        	color_or_greyscale = false;
        }
        
        System.out.println("Enter outputpath:");
        outputpath = consoleScanner.next(); // Einlesen der Benutzereingabe
        
        consoleScanner.close(); // Schließen des Konsolenscanners
        System.out.println("running......"); // Rueckmeldung des Status

  		// Initialisierung eines Objekts, welches die Pixelposition enhaelt
  		PixelPos pixelpos = georeference.getPixPos(requestedCoordX, requestedCoordY); //53,213489 12,863988 //53,205902 12,890525
  		int pixx = (int)pixelpos.getX();
  		int pixy = (int)pixelpos.getY();
  		
    	// GetBands - Baender lesen und in RasterToArray ueberfuehren, bzw. Initialisierung zweier 
  		// Objekte Band-Objekte 
  		RasterToArray amplitude_vv = new RasterToArray(dataset.getProduct(), "Amplitude_VV", pixx, pixy, tile_height, tile_width); //VV-Band
  		RasterToArray amplitude_vh = new RasterToArray(dataset.getProduct(), "Amplitude_VH", pixx, pixy, tile_height, tile_width); //VH-Band

  		// ROP`s werden aufgefuehr und angewendet 
  		// Gauss-Filter wird angewendet
  		float[][] amplitude_vvGauss = rops.gaussFilter(amplitude_vv.getArray());
  		float[][] amplitude_vhGauss = rops.gaussFilter(amplitude_vh.getArray());

  		// Median-Filter wird angewendet
  		float[][] amplitude_vvMedian = rops.medianFilter(amplitude_vv.getArray());
  		float[][] amplitude_vhMedian = rops.medianFilter(amplitude_vh.getArray());

  		// Normalisierung der Arrays
  		int[][] VHnormalisedArray = ArrayUtils.normaliseValues(amplitude_vh.getArray(), ArrayUtils.getMin(amplitude_vh.getArray()), ArrayUtils.getMax(amplitude_vh.getArray()));
  		int[][] VVnormalisedArray = ArrayUtils.normaliseValues(amplitude_vv.getArray(), ArrayUtils.getMin(amplitude_vv.getArray()), ArrayUtils.getMax(amplitude_vv.getArray()));
  		// Normalisierung der Arrays, auf die der Gauss-Filter angewandt wurde
  		int[][] normalisedVVGauss = ArrayUtils.normaliseValues(amplitude_vvGauss, ArrayUtils.getMin(amplitude_vvGauss), ArrayUtils.getMax(amplitude_vvGauss));
  		int[][] normalisedVHGauss = ArrayUtils.normaliseValues(amplitude_vhGauss, ArrayUtils.getMin(amplitude_vhGauss), ArrayUtils.getMax(amplitude_vhGauss));
  		// Normalisierung der Arrays, auf die der Median-Filter angewandt wurde
  		int[][] normalisedvvMedian = ArrayUtils.normaliseValues(amplitude_vvMedian, ArrayUtils.getMin(amplitude_vvMedian), ArrayUtils.getMax(amplitude_vvMedian));
  		int[][] normalisedvhMedian = ArrayUtils.normaliseValues(amplitude_vhMedian, ArrayUtils.getMin(amplitude_vhMedian), ArrayUtils.getMax(amplitude_vhMedian));

  		// Verschmelzen des VH- und VV-Bandes der drei Bilder
  		int[][] connectedBandsNormalised = ArrayUtils.connectNormalised(VHnormalisedArray, VVnormalisedArray);
  		int[][] connectedBandsNormalisedGauss = ArrayUtils.connectNormalised(normalisedVVGauss, normalisedVHGauss);
  		int[][] connectedBandsNormalisedMedian = ArrayUtils.connectNormalised(normalisedvvMedian, normalisedvhMedian);

	  	// Konvertierung der Bilder in ein binaeres Farbschema
	  	int[][] connectedBandsNormalisedBlack = rops.convertToBinaryColorScheme(connectedBandsNormalised, 30); //30
	  	int[][] connectedBandsNormalisedGaussBlack = rops.convertToBinaryColorScheme(connectedBandsNormalisedGauss, 50); //50
	  	int[][] connectedBandsNormalisedMedianBlack = rops.convertToBinaryColorScheme(connectedBandsNormalisedMedian, 30); //30
	
		// Detektion der Wasserflaeche, sowie Vermessung jener
	  	if(color_or_greyscale == true) {
	  		rops.scan(connectedBandsNormalisedBlack, x_in_lake, y_in_lake); // Detektion
	  	  	System.out.println("Wasserflaeche (filterlos) ca. " + rops.calculateSurface(connectedBandsNormalisedBlack) + "m2");
	  		rops.waterrize(connectedBandsNormalisedBlack); // Vermessung und Einfaerbung
	  		// Konvertierung des Arrays zu einem Bild
	  		ArrayUtils.arrayToImage(connectedBandsNormalisedBlack, outputpath, "normal_blue.png");
	  	}
	  	else {
	  		rops.scan(connectedBandsNormalisedBlack, x_in_lake, y_in_lake); // Detektion
	  	  	System.out.println("Wasserflaeche (filterlos) ca. " + rops.calculateSurface(connectedBandsNormalisedBlack) + "m2");
	  		int[][] outputNormal = ArrayUtils.convertToGreyscale(connectedBandsNormalisedBlack); // Vermessung und Einfaerbung
	  		// Konvertierung des Arrays zu einem Bild
	  		ArrayUtils.arrayToImage(outputNormal, outputpath, "normal_grey.png");
	  	}
	
	  	if(color_or_greyscale == true) {
	  		rops.scan(connectedBandsNormalisedGaussBlack, x_in_lake, y_in_lake); // Detektion
	  	  	System.out.println("Wasserflaeche (gauss) ca. " + rops.calculateSurface(connectedBandsNormalisedGaussBlack) + "m2");
	  		rops.waterrize(connectedBandsNormalisedGaussBlack); // Vermessung und Einfaerbung
	  		// Konvertierung des Arrays zu einem Bild
	  		ArrayUtils.arrayToImage(connectedBandsNormalisedGaussBlack, outputpath, "gauss_blue.png");
	  	}
	  	else {
	  		rops.scan(connectedBandsNormalisedGaussBlack, x_in_lake, y_in_lake); // Detektion
	  	  	System.out.println("Wasserflaeche (gauss) ca. " + rops.calculateSurface(connectedBandsNormalisedGaussBlack) + "m2");
	  		int[][] outputGauss = ArrayUtils.convertToGreyscale(connectedBandsNormalisedGaussBlack); // Vermessung und Einfaerbung
	  		// Konvertierung des Arrays zu einem Bild
	  		ArrayUtils.arrayToImage(outputGauss, outputpath, "gauss_grey.png");
	  	}
	
	  	if(color_or_greyscale == true) {
	  		rops.scan(connectedBandsNormalisedMedianBlack, x_in_lake, y_in_lake); // Detektion
	  	  	System.out.println("Wasserflaeche (median) ca. " + rops.calculateSurface(connectedBandsNormalisedMedianBlack) + "m2");
	  		rops.waterrize(connectedBandsNormalisedMedianBlack); // Vermessung und Einfaerbung
	  		// Konvertierung des Arrays zu einem Bild
	  		ArrayUtils.arrayToImage(connectedBandsNormalisedMedianBlack, outputpath, "median_blue.png");
	  	}
	  	else {
	  		rops.scan(connectedBandsNormalisedMedianBlack, x_in_lake, y_in_lake); // Detektion
	  	  	System.out.println("Wasserflaeche (median) ca. " + rops.calculateSurface(connectedBandsNormalisedMedianBlack) + "m2");
	  		int[][] outputMedian = ArrayUtils.convertToGreyscale(connectedBandsNormalisedMedianBlack); // Vermessung und Einfaerbung
	  		// Konvertierung des Arrays zu einem Bild
	  		ArrayUtils.arrayToImage(outputMedian, outputpath, "median_grey.png");
	  	}
    }
}
