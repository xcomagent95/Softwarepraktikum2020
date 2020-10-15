package org.lakedetection;

import java.io.File;
import java.io.IOException;
import org.esa.snap.core.dataio.ProductIO;
import org.esa.snap.core.datamodel.Product;

public class Loadzip {

	//File Attribut
	private Product product;
	/**
	 * Loadzip Konstruktor, der das Produkt aus einer Zip-Datei liest und als Variable speichert.
	 * @param Erhaelt einen Dateipfad als String, an dem eine Zip-Datei liegt.
	 * @throws IOException
	 */
	public Loadzip(String pathname) throws IOException {
		File file = new File(pathname); //Uebergabe des Pfades als String
		product = ProductIO.readProduct(file); // Deklaration des Produkts
		System.out.println("file exists: " + file.exists()); //Pruefen ob File existiert
		System.out.println(".zip to File done!"); //File gefunden und in File gespeichtert
	}

	/**
	 * Ein weiterer Konstruktor, der das Produkt aus einer Zip-Datei liest und als Variable speichert.
	 * @param Erhaelt eine Datei als Obejkt vom Typ File.
	 * @throws IOException
	 */
	public Loadzip(File file) throws IOException {
		product = ProductIO.readProduct(file);
		System.out.println(".zip to File done!"); //File gefunden und in File gespeichtert
	}
	
	/**
	 * Es handelt sich um eine Getter-Methode.
	 * @return Das Produkt vom Typ Product.
	 * @throws IOException
	 */
	public Product getProduct() throws IOException {
		return product;
	}
}
