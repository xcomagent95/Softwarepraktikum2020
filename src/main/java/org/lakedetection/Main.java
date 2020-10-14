package org.lakedetection;

import java.io.IOException;
import java.util.Scanner;

import java.io.File;

public class Main {

    public static void main(String[] args) throws IOException { //main Methode

    	//Request req = new Request();
    	//File file = req.requesting();

    	//Pfade zu den .zip Archiven
    	//Loadzip dataset = new Loadzip("/Users/josefinabalzer/Desktop/S1A_IW_GRDH_1SDV_20200307T052505_20200307T052530_031565_03A2FE_508A.zip");
    	Loadzip dataset = new Loadzip("E:\\Raster\\S1A_IW_GRDH_1SDV_20200307T052505_20200307T052530_031565_03A2FE_508A.zip");
    	
    	/*GUI try1 = new GUI();
    	Loadzip dataset = new Loadzip(try1.loadPic());*/
    	/// Funktionert

    	/*DragAndDrop try2 = new DragAndDrop();
    	String path = try2.getName();
    	Loadzip dataset = new Loadzip(path);*/
    	/// Funktioniert nicht!!

    	//new DragAndDrop();
    	
    	//Parameter
    	int tile_width;
    	int tile_height;
    	
    	int x_in_lake = -1;
    	int y_in_lake = -1;
    	
    	Boolean color_or_greyscale = true; 
    	int color_or_greyscale_int = 1; 
    	
    	//Create a Scanner object fuer Nutzer Eingabe
    	Scanner consoleScanner = new Scanner(System.in);  
    	
        System.out.println("Enter requested tile height:");
        tile_height = consoleScanner.nextInt();
        System.out.println("Enter requested tile with:");
        tile_width = consoleScanner.nextInt();
        
        while((x_in_lake > tile_height || y_in_lake > tile_width) || (x_in_lake < 0 || y_in_lake < 0)) {
	        System.out.println("Enter x of position in Lake:");
	        x_in_lake = consoleScanner.nextInt();
	        System.out.println("Enter y of position in Lake:");
	        y_in_lake = consoleScanner.nextInt();
        }
        
        System.out.println("Color or Greyscale? (1 = Color / 2 = Greyscale)");
        color_or_greyscale_int = consoleScanner.nextInt();
        
        if(color_or_greyscale_int == 1) {
        	color_or_greyscale = true;
        }
        else {
        	color_or_greyscale = false;
        }
        System.out.println("running......");

  		//Initialize Utils und ROP`s
  		ArrayUtils connected = new ArrayUtils();
  		ROPs rops = new ROPs();
  		
    	//GetBands - Baender lesen und in RasterToArray ueberfuehren
  		RasterToArray amplitude_vv = new RasterToArray(dataset.getProduct(), "Amplitude_VV", 9961, 9994, tile_height, tile_width); //VV-Band
  		RasterToArray amplitude_vh = new RasterToArray(dataset.getProduct(), "Amplitude_VH", 9961, 9994, tile_height, tile_width); //VH-Band

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
  		int[][] connectedBandsNormalisedBlack = rops.convertTobinaryColorScheme(connectedBandsNormalised, 50);
  		int[][] connectedBandsNormalisedGaussBlack = rops.convertTobinaryColorScheme(connectedBandsNormalisedGauss, 70);
  		int[][] connectedBandsNormalisedMedianBlack = rops.convertTobinaryColorScheme(connectedBandsNormalisedMedian, 60);

	  	//Lake detect
  		if(color_or_greyscale == true) {
  			rops.scan(connectedBandsNormalisedBlack, x_in_lake, y_in_lake);
  	  		System.out.println("Wasserfläche (filterlos) ca. " + rops.calculateSurface(connectedBandsNormalisedBlack) + "m²");
  			rops.waterrize(connectedBandsNormalisedBlack, 150);
  			ArrayUtils.arrayToImage(connectedBandsNormalisedBlack, "E:\\Raster\\TestBilder\\", "normal_blue.png");
  		}
  		else {
  			rops.scan(connectedBandsNormalisedBlack, x_in_lake, y_in_lake);
  	  		System.out.println("Wasserfläche (filterlos) ca. " + rops.calculateSurface(connectedBandsNormalisedBlack) + "m²");
  			int[][] outputNormal = ArrayUtils.convertToGreyscale(connectedBandsNormalisedBlack);
  			ArrayUtils.arrayToImage(outputNormal, "E:\\Raster\\TestBilder\\", "normal_grey.png");
  		}
  		
  		if(color_or_greyscale == true) {
  			rops.scan(connectedBandsNormalisedGaussBlack, x_in_lake, y_in_lake);
  	  		System.out.println("Wasserfläche (gauss) ca. " + rops.calculateSurface(connectedBandsNormalisedGaussBlack) + "m²");
  			rops.waterrize(connectedBandsNormalisedGaussBlack, 150);
  			ArrayUtils.arrayToImage(connectedBandsNormalisedGaussBlack, "E:\\Raster\\TestBilder\\", "gauss_blue.png");
  		}
  		else {
  			rops.scan(connectedBandsNormalisedGaussBlack, x_in_lake, y_in_lake);
  	  		System.out.println("Wasserfläche (gauss) ca. " + rops.calculateSurface(connectedBandsNormalisedGaussBlack) + "m²");
  			int[][] outputGauss = ArrayUtils.convertToGreyscale(connectedBandsNormalisedGaussBlack);
  			ArrayUtils.arrayToImage(outputGauss, "E:\\Raster\\TestBilder\\", "gauss_grey.png");
  		}
  		
  		if(color_or_greyscale == true) {
  			rops.scan(connectedBandsNormalisedMedianBlack, x_in_lake, y_in_lake);
  	  		System.out.println("Wasserfläche (median) ca. " + rops.calculateSurface(connectedBandsNormalisedMedianBlack) + "m²");
  			rops.waterrize(connectedBandsNormalisedMedianBlack, 150);
  			ArrayUtils.arrayToImage(connectedBandsNormalisedMedianBlack, "E:\\Raster\\TestBilder\\", "median_blue.png");
  		}
  		else {
  			rops.scan(connectedBandsNormalisedMedianBlack, x_in_lake, y_in_lake);
  	  		System.out.println("Wasserfläche (median) ca. " + rops.calculateSurface(connectedBandsNormalisedMedianBlack) + "m²");
  			int[][] outputMedian = ArrayUtils.convertToGreyscale(connectedBandsNormalisedMedianBlack);
  			ArrayUtils.arrayToImage(outputMedian, "E:\\Raster\\TestBilder\\", "median_grey.png");
  		}
    }
}
