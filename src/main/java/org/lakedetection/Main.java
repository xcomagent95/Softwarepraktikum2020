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

    public static void main(String[] args) { //main Methode
    	//Laden des Datensatzes als Objekt vom Typ Loadzip und dem Namen dataset
    	Loadzip dataset = new Loadzip("E:\\Raster\\S1A_IW_GRDH_1SDV_20200307T052505_20200307T052530_031565_03A2FE_508A.zip");

    	//Lesen des Produktes als Objekt vom typ Product und dem Namen product
		try {
			//Product lesen
			Product product = ProductIO.readProduct(dataset.getFile());
			System.out.println("product loaded!");

			//Baender ausgeben (hier werden ueber eine vorhanden Methode alle Baender ausgegeben welche product enthaelt
			System.out.println("available bands:");
			for(int i = 0; i < product.getBandNames().length; i++) {
				System.out.println(product.getBandNames()[i]);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		//Hier wird aus dataset unter Angabe des gewï¿½nschten Bandes ein Array erzeugt.
		//Das Ergebnis ist ein Objekt vom Typ ToArray mit dem Namen datasetarray
		//Es können folgende Bänder angefragt werden: Amplitude_VH, Intensity_VH, Amplitude_VV, Intensity_VV
		
		//ToArray amplitude_vh = new ToArray(dataset, "Intensity_VH", 1000, 1000, 4, 4); //VH-Band
		ToArray amplitude_vv = new ToArray(dataset, "Intensity_VV", 5250, 4500, 300, 500); //VV-Band
		
		//Fuellen das Arrays
		//amplitude_vh.fillArray(dataset);
		amplitude_vv.fillArray(dataset);
		
		//amplitude_vh.calculateStatistics();
		amplitude_vv.calculateStatistics();
		
		//amplitude_vh.convertToGreyscale();
		amplitude_vv.convertToGreyscale();
		
		//amplitude_vh.probeArray();
		//amplitude_vv.probeArray();
		
		//amplitude_vh.probeArrayNormalised();
		//amplitude_vv.probeArrayNormalised();
		
		//amplitude_vh.arrayToImage();
		amplitude_vv.arrayToImage();
    }
}
