package org.lakedetection;

import java.io.File;
import java.io.IOException;
import org.esa.snap.core.dataio.ProductIO;
import org.esa.snap.core.datamodel.Product;

public class Loadzip {
	
	//File Attribut
	private Product product;
	
	//Loadzip Konstruktor
	public Loadzip(String pathname) throws IOException {
		File file = new File(pathname); //Uebergabe des Pfades als String
		product = ProductIO.readProduct(file);
		System.out.println("file exists: " + file.exists()); //Pruefen ob File existiert
		System.out.println(".zip to File done!"); //File gefunden und in File gespeichtert
	}
	
	public Loadzip(File file) throws IOException {
		System.out.println(".zip to File done!"); //File gefunden und in File gespeichtert
		product = ProductIO.readProduct(file);
	}
	
	//Getter
	public Product getProduct() throws IOException {
		return product;
	}
}
