package org.lakedetection;

import java.io.IOException;
import java.util.Scanner;

import org.esa.snap.core.datamodel.PixelPos;

import java.io.File;

public class Main {

    public static void main(String[] args) throws IOException { //main Methode
  		//Initialize Utils und ROP`s
  		ArrayUtils connected = new ArrayUtils();
  		ROPs rops = new ROPs();

    	//FindSets findsets = new FindSets();
    	//Request req = new Request();
    	//File file = findsets.downloadZip(findsets.search());
    	

    	//System.out.println("search done");

    	//Laden des Datensatzes als Objekt vom Typ Loadzip und dem Namen dataset
    	//Loadzip dataset = new Loadzip(file.getAbsolutePath());
    	
    	//Pfad Josi
    	//Loadzip dataset = new Loadzip("/Users/josefinabalzer/Desktop/softwarepraktikum2020/S1B_IW_GRDH_1SDV_20201013T165207_20201013T165232_023797_02D38A_0D7E.zip");
    	//Pfad Alex
    	Loadzip dataset = new Loadzip("E:\\Raster\\S1A_IW_GRDH_1SDV_20200307T052505_20200307T052530_031565_03A2FE_508A.zip");
  		Georeference georeference = new Georeference(dataset.getProduct());
  		
    	//Parameter
    	int tile_width;
    	int tile_height;
    	
    	double requestedCoordX;
    	double requestedCoordY;

    	int x_in_lake = -1;
    	int y_in_lake = -1;

    	Boolean color_or_greyscale = true;
    	int color_or_greyscale_int = 1;
    	
    	String outputpath = "";

    	//Create a Scanner object fuer Nutzer Eingabe
    	Scanner consoleScanner = new Scanner(System.in);

        System.out.println("Enter requested tile height:");
        tile_height = consoleScanner.nextInt();
        System.out.println("Enter requested tile with:");
        tile_width = consoleScanner.nextInt();
    	PixelPos requestedPos;
    	
        do {
        	System.out.println("Enter requested Coord-X:");
        	requestedCoordX = consoleScanner.nextDouble();
        	System.out.println("Enter requested Coord-Y:");
        	requestedCoordY = consoleScanner.nextDouble();
        	requestedPos = georeference.getPixPos(requestedCoordX, requestedCoordY); 
        } while(Double.isNaN(requestedPos.x) || Double.isNaN(requestedCoordY));
        	
        	//53,213489 12,863988 //53,205902 12,890525

        /*
        while((x_in_lake > tile_height || y_in_lake > tile_width) || (x_in_lake < 0 || y_in_lake < 0)) {
	        System.out.println("Enter x of position in Lake:");
	        x_in_lake = consoleScanner.nextInt();
	        System.out.println("Enter y of position in Lake:");
	        y_in_lake = consoleScanner.nextInt();
        }
        */
        
    	x_in_lake = tile_height/2;
    	y_in_lake = tile_width/2;

        System.out.println("Color or Greyscale? (1 = Color / 2 = Greyscale)");
        color_or_greyscale_int = consoleScanner.nextInt();

        if(color_or_greyscale_int == 1) {
        	color_or_greyscale = true;
        }
        else {
        	color_or_greyscale = false;
        }
        
        System.out.println("Enter outputpath:");
        outputpath = consoleScanner.next();
        
        System.out.println("running......");

  		// PixPos
  		PixelPos pixelpos = georeference.getPixPos(requestedCoordX, requestedCoordY); //53,213489 12,863988 //53,205902 12,890525
  		int pixx = (int)pixelpos.getX();
  		int pixy = (int)pixelpos.getY();
  		
    	//GetBands - Baender lesen und in RasterToArray ueberfuehren
  		RasterToArray amplitude_vv = new RasterToArray(dataset.getProduct(), "Amplitude_VV", pixx, pixy, tile_height, tile_width); //VV-Band
  		RasterToArray amplitude_vh = new RasterToArray(dataset.getProduct(), "Amplitude_VH", pixx, pixy, tile_height, tile_width); //VH-Band

  		//ROP`s aufuehren
  		//Gauss-Filter anwenden
  		float[][] amplitude_vvGauss = rops.gaussFilter(amplitude_vv.getArray());
  		float[][] amplitude_vhGauss = rops.gaussFilter(amplitude_vh.getArray());

  		//Median-Filter anwenden
  		float[][] amplitude_vvMedian = rops.medianFilter(amplitude_vv.getArray());
  		float[][] amplitude_vhMedian = rops.medianFilter(amplitude_vh.getArray());

  		//Normalize Arrays
  		//Normal
  		int[][] VHnormalisedArray = ArrayUtils.normaliseValues(amplitude_vh.getArray(), ArrayUtils.getMin(amplitude_vh.getArray()), ArrayUtils.getMax(amplitude_vh.getArray()));
  		int[][] VVnormalisedArray = ArrayUtils.normaliseValues(amplitude_vv.getArray(), ArrayUtils.getMin(amplitude_vv.getArray()), ArrayUtils.getMax(amplitude_vv.getArray()));
  		//Gauss
  		int[][] normalisedVVGauss = ArrayUtils.normaliseValues(amplitude_vvGauss, ArrayUtils.getMin(amplitude_vvGauss), ArrayUtils.getMax(amplitude_vvGauss));
  		int[][] normalisedVHGauss = ArrayUtils.normaliseValues(amplitude_vhGauss, ArrayUtils.getMin(amplitude_vhGauss), ArrayUtils.getMax(amplitude_vhGauss));
  		//Median
  		int[][] normalisedvvMedian = ArrayUtils.normaliseValues(amplitude_vvMedian, ArrayUtils.getMin(amplitude_vvMedian), ArrayUtils.getMax(amplitude_vvMedian));
  		int[][] normalisedvhMedian = ArrayUtils.normaliseValues(amplitude_vhMedian, ArrayUtils.getMin(amplitude_vhMedian), ArrayUtils.getMax(amplitude_vhMedian));

  		//Connect normalized Bands
  		int[][] connectedBandsNormalised = ArrayUtils.connectNormalised(VHnormalisedArray, VVnormalisedArray);
  		int[][] connectedBandsNormalisedGauss = ArrayUtils.connectNormalised(normalisedVVGauss, normalisedVHGauss);
  		int[][] connectedBandsNormalisedMedian = ArrayUtils.connectNormalised(normalisedvvMedian, normalisedvhMedian);

	  	//convert to binary Color-Scheme
	  	int[][] connectedBandsNormalisedBlack = rops.convertToBinaryColorScheme(connectedBandsNormalised, 30); //30
	  	int[][] connectedBandsNormalisedGaussBlack = rops.convertToBinaryColorScheme(connectedBandsNormalisedGauss, 50); //50
	  	int[][] connectedBandsNormalisedMedianBlack = rops.convertToBinaryColorScheme(connectedBandsNormalisedMedian, 30); //30
	
		  //Lake detect
	  	if(color_or_greyscale == true) {
	  		rops.scan(connectedBandsNormalisedBlack, x_in_lake, y_in_lake);
	  	  	System.out.println("Wasserflaeche (filterlos) ca. " + rops.calculateSurface(connectedBandsNormalisedBlack) + "m2");
	  		rops.waterrize(connectedBandsNormalisedBlack, 150);
	  		ArrayUtils.arrayToImage(connectedBandsNormalisedBlack, outputpath, "normal_blue.png");
	  	}
	  	else {
	  		rops.scan(connectedBandsNormalisedBlack, x_in_lake, y_in_lake);
	  	  	System.out.println("Wasserflaeche (filterlos) ca. " + rops.calculateSurface(connectedBandsNormalisedBlack) + "m2");
	  		int[][] outputNormal = ArrayUtils.convertToGreyscale(connectedBandsNormalisedBlack);
	  		ArrayUtils.arrayToImage(outputNormal, outputpath, "normal_grey.png");
	  	}
	
	  	if(color_or_greyscale == true) {
	  		rops.scan(connectedBandsNormalisedGaussBlack, x_in_lake, y_in_lake);
	  	  	System.out.println("Wasserflaeche (gauss) ca. " + rops.calculateSurface(connectedBandsNormalisedGaussBlack) + "m2");
	  		rops.waterrize(connectedBandsNormalisedGaussBlack, 150);
	  		ArrayUtils.arrayToImage(connectedBandsNormalisedGaussBlack, outputpath, "gauss_blue.png");
	  	}
	  	else {
	  		rops.scan(connectedBandsNormalisedGaussBlack, x_in_lake, y_in_lake);
	  	  	System.out.println("Wasserflaeche (gauss) ca. " + rops.calculateSurface(connectedBandsNormalisedGaussBlack) + "m2");
	  		int[][] outputGauss = ArrayUtils.convertToGreyscale(connectedBandsNormalisedGaussBlack);
	  		ArrayUtils.arrayToImage(outputGauss, outputpath, "gauss_grey.png");
	  	}
	
	  	if(color_or_greyscale == true) {
	  		rops.scan(connectedBandsNormalisedMedianBlack, x_in_lake, y_in_lake);
	  	  	System.out.println("Wasserflaeche (median) ca. " + rops.calculateSurface(connectedBandsNormalisedMedianBlack) + "m2");
	  		rops.waterrize(connectedBandsNormalisedMedianBlack, 150);
	  		ArrayUtils.arrayToImage(connectedBandsNormalisedMedianBlack, outputpath, "median_blue.png");
	  	}
	  	else {
	  		rops.scan(connectedBandsNormalisedMedianBlack, x_in_lake, y_in_lake);
	  	  	System.out.println("Wasserflaeche (median) ca. " + rops.calculateSurface(connectedBandsNormalisedMedianBlack) + "m2");
	  		int[][] outputMedian = ArrayUtils.convertToGreyscale(connectedBandsNormalisedMedianBlack);
	  		ArrayUtils.arrayToImage(outputMedian, outputpath, "median_grey.png");
	  	}
    }
}
