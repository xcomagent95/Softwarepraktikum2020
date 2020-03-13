package org.lakedetection;

import org.esa.snap.core.dataio.ProductIO;
import org.esa.snap.core.dataio.ProductReader;
import org.esa.snap.core.dataio.ProductSubsetBuilder;
import org.esa.snap.core.dataio.ProductSubsetDef;
import org.esa.snap.core.datamodel.Band;
import org.esa.snap.core.datamodel.Product;
import org.esa.snap.core.datamodel.TiePointGrid;

import javax.imageio.stream.FileImageOutputStream;
import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException { //main Methode
    	//Laden des Datensatzes als Objekt vom Typ Loadzip und dem Namen dataset
    	Loadzip dataset = new Loadzip("E:\\Raster\\S1A_IW_GRDH_1SDV_20200307T052505_20200307T052530_031565_03A2FE_508A.zip");

		RasterToArray amplitude_vv = new RasterToArray(dataset.getProduct(), "Amplitude_VV", 9961, 9994, 21, 21); //VV-Band
		int[][] normalisedArray = ArrayUtils.normaliseValues(amplitude_vv.getArray(), amplitude_vv.getLowestPixel(), amplitude_vv.getHighestPixel());
		int[][] rgbArray = ArrayUtils.convertToRGB(normalisedArray);
		ArrayUtils.arrayToImage(rgbArray, "E:\\Raster", "test");
    }
}
