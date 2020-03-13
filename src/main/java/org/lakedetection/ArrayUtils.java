package org.lakedetection;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.media.jai.PlanarImage;

import org.esa.snap.core.dataio.ProductIO;
import org.esa.snap.core.datamodel.Band;
import org.esa.snap.core.datamodel.Product;
import org.esa.snap.core.image.ImageManager;

import com.bc.ceres.core.PrintWriterProgressMonitor;
import com.bc.ceres.glevel.MultiLevelImage;

public class ArrayUtils {

	public int[][] normaliseValues(float[][] datasetArray, int lowestPixel, int highestPixel) {
		int[][] datasetArrayNormalised = new int[datasetArray.length][datasetArray[0].length];
		int pixelCounter = 0;
		for(int i = 0; i < datasetArray.length; i++) {
			for(int j = 0; j < datasetArray[0].length; j++) {
				datasetArrayNormalised[i][j] = ((int) ((datasetArray[i][j]-lowestPixel)*(255 - 0)/(highestPixel-lowestPixel)+0));
				pixelCounter += 1;
				//System.out.println(pixelCounter + " pixels normalised...");
			}
		}
		System.out.println("array normalised!");
		return datasetArrayNormalised;
	}
	
	public int[][] convertToRGB(int[][] datasetArrayNormalised) {
		int[][] datasetArrayRGB = new int[datasetArrayNormalised.length][datasetArrayNormalised[0].length];
		int pixelCounter = 0;
		for(int i = 0; i < datasetArrayNormalised.length; i++) {
			for(int j = 0; j < datasetArrayNormalised[0].length; j++) {
				datasetArrayRGB[i][j] = (datasetArrayNormalised[i][j]) + (datasetArrayNormalised[i][j] << 8) + (datasetArrayNormalised[i][j]<< 16);
				pixelCounter += 1;
				//System.out.println(pixelCounter + " pixels normalised...");
			}
		}
		System.out.println("array converted to RGB!");
		return(datasetArrayRGB);
	}

	//Schreibt ein normalisiertes Array in ein .png
	public void arrayToImage(int [][] datasetArrayRGB) {
		BufferedImage outputImage = new BufferedImage(datasetArrayRGB[0].length, datasetArrayRGB.length, BufferedImage.TYPE_INT_RGB);
		
		 int pixelCounter = 0;
		 for(int i = 0; i < datasetArrayRGB.length; i++) {
		        for(int j = 0; j < datasetArrayRGB[0].length; j++) {
		        	outputImage.setRGB(j, i, datasetArrayRGB[i][j]);
		        	pixelCounter += 1;
		        	//System.out.println(pixelCounter + " pixels written...");
		        }
		 }
		 
		 File file = new File("E:\\Raster\\test.png");
		 try {
			ImageIO.write(outputImage, "png", file);
			System.out.println("image written!");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
