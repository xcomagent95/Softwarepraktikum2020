package org.lakedetection;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException { //main Methode
    	//Laden des Datensatzes als Objekt vom Typ Loadzip und dem Namen dataset
    	Loadzip dataset = new Loadzip("/Users/josefinabalzer/Desktop/S1A_IW_GRDH_1SDV_20200307T052505_20200307T052530_031565_03A2FE_508A.zip");

		RasterToArray amplitude_vv = new RasterToArray(dataset.getProduct(), "Amplitude_VV", 9961, 9994, 300, 300); //VV-Band
		ROPs rops = new ROPs();
		
		float[][] medianArray = rops.medianFilter(amplitude_vv.getArray());
		float[][] medianAndGaussArray = rops.medianFilter(rops.gaussFilter(amplitude_vv.getArray()));
		float[][] blackArray = rops.makeBlack(medianAndGaussArray);
		int[][] normalisedArray = ArrayUtils.normaliseValues(blackArray, ArrayUtils.getMin(blackArray), ArrayUtils.getMax(blackArray));
				
		//float[][] gaussArray = rops.gaussFilter(amplitude_vv.getArray());
		//int[][] normalisedArray = ArrayUtils.normaliseValues(gaussArray, ArrayUtils.getMin(gaussArray), ArrayUtils.getMax(gaussArray));
		// int[][] normalisedArray = ArrayUtils.normaliseValues(amplitude_vv.getArray(), amplitude_vv.getLowestPixel(), amplitude_vv.getHighestPixel());
		int[][] rgbArray = ArrayUtils.convertToRGB(normalisedArray);
		ArrayUtils.arrayToImage(rgbArray, "/Users/josefinabalzer/Desktop/TestBilder/", "blackArrayAfterMedianAndGaussArray");
		
    }
}
