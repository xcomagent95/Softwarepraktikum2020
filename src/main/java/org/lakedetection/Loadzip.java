package org.lakedetection;

import java.io.File;

public class Loadzip {
	
	//File Attribut
	private File file = null;
	
	//loadzip Object
	public Loadzip(String pathname) {
		file = new File(pathname);
		System.out.println(file.exists());
		System.out.println(".zip to File done!");
	}
	
	//Getter fuer File
	public File getFile() {
		return file;
	}
}
