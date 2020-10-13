package org.lakedetection;

import java.io.IOException;
import java.util.Scanner;

import java.io.File;

public class Main {

    public static void main(String[] args) throws IOException { //main Methode

    	//Request req = new Request();
    	//File file = req.requesting();

    	//Laden des Datensatzes als Objekt vom Typ Loadzip und dem Namen dataset
    	//Loadzip dataset = new Loadzip("/Users/josefinabalzer/Desktop/softwarepraktikum2020"+file.getAbsolutePath());

    	Loadzip dataset = new Loadzip("/Users/josefinabalzer/Desktop/S1A_IW_GRDH_1SDV_20200307T052505_20200307T052530_031565_03A2FE_508A.zip");
    	/*GUI try1 = new GUI();
    	Loadzip dataset = new Loadzip(try1.loadPic());*/
    	/// Funktionert

    	/*DragAndDrop try2 = new DragAndDrop();
    	String path = try2.getName();
    	Loadzip dataset = new Loadzip(path);*/
    	/// Funktioniert nicht!!

    	//new DragAndDrop();

  		//Init
  		ArrayUtils connected = new ArrayUtils();
  		ROPs rops = new ROPs();

    	//GetBands
  		RasterToArray amplitude_vv = new RasterToArray(dataset.getProduct(), "Amplitude_VV", 9961, 9994, 300, 300); //VV-Band
  		RasterToArray amplitude_vh = new RasterToArray(dataset.getProduct(), "Amplitude_VH", 9961, 9994, 300, 300); //VH-Band

  		//ROP`s auf�hren
  		//Gauss
  		float[][] amplitude_vvGauss = rops.gaussFilter(amplitude_vv.getArray());
  		float[][] amplitude_vhGauss = rops.gaussFilter(amplitude_vh.getArray());

  		//Median
  		float[][] amplitude_vvMedian = rops.medianFilter(amplitude_vv.getArray());
  		float[][] amplitude_vhMedian = rops.medianFilter(amplitude_vh.getArray());

  		//Normalize Arrays
  		int[][] VHnormalisedArray = ArrayUtils.normaliseValues(amplitude_vh.getArray(), ArrayUtils.getMin(amplitude_vh.getArray()), ArrayUtils.getMax(amplitude_vh.getArray()));
  		int[][] VVnormalisedArray = ArrayUtils.normaliseValues(amplitude_vv.getArray(), ArrayUtils.getMin(amplitude_vv.getArray()), ArrayUtils.getMax(amplitude_vv.getArray()));

  		int[][] normalisedVVGauss = ArrayUtils.normaliseValues(amplitude_vvGauss, ArrayUtils.getMin(amplitude_vvGauss), ArrayUtils.getMax(amplitude_vvGauss));
  		int[][] normalisedVHGauss = ArrayUtils.normaliseValues(amplitude_vhGauss, ArrayUtils.getMin(amplitude_vhGauss), ArrayUtils.getMax(amplitude_vhGauss));

  		int[][] normalisedvvMedian = ArrayUtils.normaliseValues(amplitude_vhGauss, ArrayUtils.getMin(amplitude_vvMedian), ArrayUtils.getMax(amplitude_vvMedian));
  		int[][] normalisedvhMedian = ArrayUtils.normaliseValues(amplitude_vhMedian, ArrayUtils.getMin(amplitude_vhMedian), ArrayUtils.getMax(amplitude_vhMedian));

  		//Connect normalized Bands
  		int[][] connectedBandsNormalised = ArrayUtils.connectNormalised(VHnormalisedArray, VVnormalisedArray);
  		int[][] connectedBandsNormalisedGauss = ArrayUtils.connectNormalised(normalisedVVGauss, normalisedVHGauss);
  		int[][] connectedBandsNormalisedMedian = ArrayUtils.connectNormalised(normalisedvvMedian, normalisedvhMedian);

  		
  	
	  	//MakeBlack
	  	//int[][] connectedBandsNormalisedBlack = rops.makeBlack(connectedBandsNormalised, i);
	  	//int[][] connectedBandsNormalisedGaussBlack = rops.makeBlack(connectedBandsNormalisedGauss, i);
	  	int[][] connectedBandsNormalisedMedianBlack = rops.makeBlack(connectedBandsNormalisedMedian, 20);
	
		//Lake detect
	  	//rops.horizontalScanLine(connectedBandsNormalisedGaussBlack, 200, 100);
	  	//rops.horizontalScanLine(connectedBandsNormalisedMedianBlack, 200, 100);
	
		//Convert to RGB
		//int[][] connectedRGBArray = ArrayUtils.convertToRGB(connectedBandsNormalisedBlack);
	  	//int[][] connectedRGBArrayGauss = ArrayUtils.convertToRGB(connectedBandsNormalisedGaussBlack);
	  	int[][] connectedRGBArrayMedian = ArrayUtils.convertToRGB(connectedBandsNormalisedMedianBlack);
	
		//Write Image
		//ArrayUtils.arrayToImage(connectedRGBArray, "E:\\Raster\\TestBilder\\", "normal" + i + ".png");
		//ALEX:
		//ArrayUtils.arrayToImage(connectedRGBArrayGauss, "E:\\Raster\\TestBilder\\", "gauss.png");
		//ArrayUtils.arrayToImage(connectedRGBArrayMedian, "E:\\Raster\\TestBilder\\", "median.png");
		//JOSI:
		//ArrayUtils.arrayToImage(connectedRGBArrayGauss, "/Users/josefinabalzer/Desktop/TestBilder/", "gaussAlexChangedBorders.png");
		ArrayUtils.arrayToImage(connectedRGBArrayMedian, "/Users/josefinabalzer/Desktop/TestBilder/", "medianAlexChangedBorders.png");
	  	
    }
}
