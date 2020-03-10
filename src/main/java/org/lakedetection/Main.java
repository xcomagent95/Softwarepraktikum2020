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

/**
 * This is an example program which writes out an NDVI value in the range 0 to 255 computed from the MERIS L1b bands
 * "radiance_6" and "radiance_10".
 * <p>The program expects two input arguments: <ol> <li><i>input-file</i> - the file path to an input data product
 * containing the bands "radiance_6" and "radiance_10". The format can be either ENVISAT or BEAM-DIMAP</li>
 * <li><i>output-file</i> - the file path to the NDVI image file to be written</li> </ol>
 * <p>
 * <i><b>Note:</b> If you want to work with product subsets, you can use the {@link
 * ProductSubsetBuilder} class. It has a static method which lets you create a subset of a
 * given product and subset definition.</i>
 *
 * @see ProductIO
 * @see ProductSubsetBuilder
 * @see ProductSubsetDef
 * @see Product
 * @see Band
 * @see TiePointGrid
 */
public class Main {

    /**
     * The main method. Fetches the input arguments and delegates the call to the <code>run</code> method.
     *
     * @param args the program arguments
     */
    public static void main(String[] args) { //main Methode
    	//Laden des Datensatzes
    	Loadzip dataset = new Loadzip("E:\\Uni\\Softwarepraktikum\\Project\\softwarepraktikum2020\\src\\main\\resources\\S1A_IW_GRDH_1SDV_20200307T052505_20200307T052530_031565_03A2FE_508A.zip");
    //try {
		try {
			//Product lesen
			Product product = ProductIO.readProduct(dataset.getFile());
			System.out.println("product loaded!");
			//Bänder ausgeben
			for(int i = 0; i < product.getBandNames().length; i++) {
				System.out.println(product.getBandNames()[i]);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}