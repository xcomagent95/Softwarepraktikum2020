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
			
			//Bänder ausgeben (hier werden über eine vorhanden Methode alle Baender ausgegeben welche product enthält
			//for(int i = 0; i < product.getBandNames().length; i++) {
				//System.out.println(product.getBandNames()[i]);
			//}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//Hier wird aus dataset unter Angabe des gewünschten Bandes ein Array erzeugt. 
		//Das Ergebnis ist ein Objekt vom Typ ToArray mit dem Namen datasetarray
		//Es können folgende Bänder angefragt werden: Amplitude_VH, Intensity_VH, Amplitude_VV, Intensity_VV
		ToArray datasetarray = new ToArray(dataset, "Intensity_VH", 50, 50, 100, 100); 
		
		//Fuellen das Arrays
		datasetarray.fillArray(dataset, "Intensity_VH", datasetarray.getArray(), datasetarray.getRequestedCornerX(), datasetarray.getRequestedCornerY(), datasetarray.getRequestedHeight(), datasetarray.getRequestedWidth());
		
		//Ein paar Infos zum erzeugten Array
		datasetarray.probeArray();
    }
}