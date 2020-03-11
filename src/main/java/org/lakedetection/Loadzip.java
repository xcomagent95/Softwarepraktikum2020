package org.lakedetection;

import java.io.File;

public class Loadzip {
	
	//File Attribut
	private File file = null; //Attribut vom Typ File
	
	//Loadzip Konstruktor
	public Loadzip(String pathname) {
		file = new File(pathname); //Uebergabe des Pfades als String
		System.out.println("file exists: " + file.exists()); //Pruefen ob File existiert
		System.out.println(".zip to File done!"); //File gefunden und in File gespeichtert
	}
	
	//Getter fuer File
	public File getFile() {
		return file;
	}
}
