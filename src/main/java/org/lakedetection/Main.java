package org.lakedetection;

import java.io.IOException;
import java.util.Scanner;

import java.io.File;

public class Main {

    public static void main(String[] args) throws IOException { //main Methode
    	
    	Request req = new Request();
    	File file = req.requesting();
    	
    	//Laden des Datensatzes als Objekt vom Typ Loadzip und dem Namen dataset
    	Loadzip dataset = new Loadzip("/Users/josefinabalzer/Desktop/softwarepraktikum2020"+file.getAbsolutePath());
    	/*GUI try1 = new GUI();
    	Loadzip dataset = new Loadzip(try1.loadPic());*/
    	/// Funktionert 
    
    	/*DragAndDrop try2 = new DragAndDrop();
    	String path = try2.getName();
    	Loadzip dataset = new Loadzip(path);*/
    	/// Funktioniert nicht!!
    	
    	//new DragAndDrop();
    	
    	
  		RasterToArray amplitude_vv = new RasterToArray(dataset.getProduct(), "Amplitude_VV", 9961, 9994, 300, 300); //VV-Band
  		RasterToArray amplitude_vh = new RasterToArray(dataset.getProduct(), "Amplitude_VH", 9961, 9994, 300, 300); //VV-Band
  		ArrayUtils connected = new ArrayUtils();
  		
  		ROPs rops = new ROPs();

  		float[][] connectedBands = connected.connect(amplitude_vv.getArray(), amplitude_vh.getArray());
  		float[][] medianArray = rops.medianFilter(connectedBands);
  		float[][] medianAndGaussArray = rops.medianFilter(rops.gaussFilter(amplitude_vv.getArray()));
  		float[][] blackArray = rops.makeBlack(connectedBands);
  		
  		int[][] medianArrayNormalised = ArrayUtils.normaliseValues(medianArray, ArrayUtils.getMin(medianArray), ArrayUtils.getMax(medianArray));
  		
  		int[][] VHnormalisedArray = ArrayUtils.normaliseValues(amplitude_vh.getArray(), ArrayUtils.getMin(amplitude_vh.getArray()), ArrayUtils.getMax(amplitude_vh.getArray()));
  		int[][] VVnormalisedArray = ArrayUtils.normaliseValues(amplitude_vv.getArray(), ArrayUtils.getMin(amplitude_vv.getArray()), ArrayUtils.getMax(amplitude_vv.getArray()));
  		
  		int[][] connectedBandsNormalised = ArrayUtils.connectNormalised(VHnormalisedArray, VVnormalisedArray);
  		
  		//float[][] gaussArray = rops.gaussFilter(amplitude_vv.getArray());
  		//int[][] normalisedArray = ArrayUtils.normaliseValues(gaussArray, ArrayUtils.getMin(gaussArray), ArrayUtils.getMax(gaussArray));
  		// int[][] normalisedArray = ArrayUtils.normaliseValues(amplitude_vv.getArray(), amplitude_vv.getLowestPixel(), amplitude_vv.getHighestPixel());
  		int[][] VHrgbArray = ArrayUtils.convertToRGB(VHnormalisedArray);
  		int[][] VVrgbArray = ArrayUtils.convertToRGB(VVnormalisedArray);
  		int[][] connectedRGBArray = ArrayUtils.convertToRGB(connectedBandsNormalised);
  		
  		ArrayUtils.arrayToImage(medianArrayNormalised, "/Users/josefinabalzer/Desktop/TestBilder/", "medianArrayNormalised");

    }
}
