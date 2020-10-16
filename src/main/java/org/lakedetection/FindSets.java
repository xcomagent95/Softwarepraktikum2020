package org.lakedetection;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.github.kevinsawicki.http.HttpRequest;
import com.github.kevinsawicki.http.HttpRequest.HttpRequestException;

/**
 * @author Josefina Balzer & Dorian Henningfeld
 * @version 1.0 (current version number of program)
 */
public class FindSets {
	
	LoginScihub userdata = new LoginScihub();
	/**
	 * Sendet eine Anfrage an Scihub um eine JSON-File mit den Informationen über alle 
	 * verfügbaren Datensätze zu erhalten.
	 * @return Einen String, der in die Anfrage für den expliziten Datensatz eingebettet wird.
	 */
	public String search() {
		
		// String url = new String("https://scihub.copernicus.eu/dhus/api/stub/products?filter=(%20footprint:%22Intersects(POLYGON((12.847298091933668%2053.18180695455385,12.949362582961403%2053.18180695455385,12.949362582961403%2053.22056068128961,12.847298091933668%2053.22056068128961,12.847298091933668%2053.18180695455385)))%22%20)%20AND%20(%20beginPosition:[");
		// 12.847298091933668 53.18180695455385, 12.949362582961403 53.18180695455385,12.949362582961403 53.22056068128961,12.847298091933668 53.22056068128961,
		// 12.847298091933668 53.18180695455385 
	
		
		// Initialisierung eines Scanners zum Lesen der Benutzereingaben:
    	Scanner consoleScanner = new Scanner(System.in);
		
		Double centerPointLat; // Initialisierung eines centerPoint Latitude vom Typ Double
		
		System.out.println("Insert the Latitude of the center point of the polygon you want to request e.g. 12,898330338");
		
		centerPointLat = consoleScanner.nextDouble(); // Lesen der Benutzereingabe
		
		Double centerPointLon; // Initialisierung eines centerPoint Longitude vom Typ Double
		System.out.println("Insert the Longitude of the center point of the polygon you want to request e.g. 53,201183818");
		centerPointLon = consoleScanner.nextDouble(); // Lesen der Benutzereingabe
		
		String url = new String("https://scihub.copernicus.eu/dhus/api/stub/products?filter=(%20footprint:%22Intersects(POLYGON(("
				+ (centerPointLat-(0.05103224551)) +"%20"+(centerPointLon-(0.01937686336))+","+(centerPointLat+(0.05103224551))+"%20"+(centerPointLon-(0.01937686336))+","
				+ (centerPointLat+(0.05103224551))+"%20"+(centerPointLon+(0.01937686336))+","+(centerPointLat-(0.05103224551))+"%20"+(centerPointLon+(0.01937686336))+","
				+ (centerPointLat-(0.05103224551))+"%20"+(centerPointLon-(0.01937686336))+")))%22%20)%20AND%20(%20beginPosition:[");
		
		// Initialisierung eines reader Objekts, um die Nutzereingaben einzulesen
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		
   		String startdate = null; // Initialiserung des Startdatum des Zeitfensters 
		System.out.println("Insert the startdate e.g. 2020-08-01");
		try {
			startdate = reader.readLine(); // Lesen der Benutzereingabe
			url += startdate; // Erweitern des URL um das Startdatum
		} catch (IOException e) {
			e.printStackTrace();
		}
		url+= "T00:00:00.000Z%20TO%20NOW]%20AND%20endPosition:[";
		String enddate = null; // Initialiserung des Startdatum des Zeitfensters 
		System.out.println("Insert the enddate e.g. 2020-10-01");
		try {
			enddate = reader.readLine(); // Lesen der Benutzereingabe
			url += enddate; // Erweitern des URL um das Enddatum
		} catch (IOException e) {
			e.printStackTrace();
		}
		url += "T00:00:00.000Z%20TO%20NOW]%20)%20AND%20(%20%20(platformname:Sentinel-1%20AND%20producttype:GRD%20AND%20polarisationmode:VV+VH))&offset=0&limit=25&sortedby=beginposition&order=desc";
		
	    System.out.println("The Url is:  " + url);	    
    	userdata.login();
	    
	    try {
			HttpRequest request =  HttpRequest.get(url).basic(userdata.getUsername(), userdata.getPassword());
		    File file = null; // Initialisierung der Datei 
		    System.out.println("Status: " + request.code());
		    // Initialisierung eines Strings, welches die uuid enthalten wird
		    String imageNumber = "Variable 'imageNumber' has not been initialized"; 
		    if (request.ok()) { // Ueberpruefung, ob die Anfrage erfolgreich war 
		    	/*
		    	try {
		    		// Eine leere Datei wird auf dem Computer lokal erstellt
		    		file = File.createTempFile("sets", ".json", new File(".//"));
		    		// Ausgabe des Dateipfades auf der Konsole
		    		System.out.println("File path: "+ file.getAbsolutePath());
		    		System.out.println("Downloading"); 
		    	} catch(IOException exception) {
		    		System.out.println(exception);
		    	}
		    	*/
		    	
		    	// --------------- ZUM TESTEN OHNE DOWNLOAD --------------------------------------
		    	file = new File("/Users/josefinabalzer/Desktop/softwarepraktikum2020/sets8749057408278342050.json");
	    		System.out.println("File path: /Users/josefinabalzer/Desktop/softwarepraktikum2020/sets8749057408278342050.json");
		    	// -------------------------------------------------------------------------------
	    		// Fuellen der leeren Datei
		        //request.receive(file);
		        
		        int number = 0;
		        // Initialisierung eines String-Arrays, in dem spaeter die uuids gespeichter werden,
		        // die zur vervollstaendigung eines weiteren URL fuer eine Anfrage der zip-Datei 
		        // benoetigt wird.
		        String[] images = parse(file.getAbsolutePath()); 
		        System.out.println("Insert the the index of the date of the image you want e.g. if it is the first one (1.), enter 1");
				try {
					// Abfragen des Datums, von dem die Datei sein soll
					number = Integer.valueOf(reader.readLine()); 
				} catch (IOException e) {
					e.printStackTrace();
				}
		        imageNumber = images[number-1]; // Zuweisung der passenden uuid
		    } else System.out.println("Request does not work: "+request.body());
		    return imageNumber; // Rueckgabe der korrekten uuid
		} catch (HttpRequestException exception) {
			System.out.println(exception);
			return null;
	    }
	}
	
	/**
	 * @param Die Methode erhaelt einen String als Eingabe, der dann in die URL zur Anfrage an die API
	 * 			eingebettet wird. Dieser ist der Rückgabewert der find()-Methode.
	 * @return Zip-Datei, die die Baender für das Bild und alle weiteren notwendigen Informationen
	 * 			und Daten enthaelt.
	 */
	public File downloadZip(String firstImage) {
		try {
			// Initialisierung der Anfrage 
			HttpRequest request =  HttpRequest.get("https://scihub.copernicus.eu/dhus/odata/v1/Products('"+ firstImage +"')/$value").basic(userdata.getUsername(), userdata.getPassword());
			File file = null; // Initialisierung einer File
		    System.out.println("Status: " + request.code());
		    if (request.ok()) { // Ueberpruefung, ob die Anfrage erfolgreich war 
		    	try {
		    		// Speichert die Datei unter dem "Identiefier" als Namen 
		    		String fileName = request.header("Content-Disposition").substring(17, request.header("Content-Disposition").length()-1);
		    		file = new File(".//"+fileName); // Der Datei wird ein Name zugewiesen, sowie ein Speicherort 
		    		file.createNewFile(); // Anlegen einer neuen Datei
		    		System.out.println("File path: "+ file.getAbsolutePath()); // Ausgabe des Dateipfades auf der Konsole
		    		System.out.println("Downloading"); // Ausgabe des Prozesszustands auf der Konsole
		    	} catch(IOException exception) {
		    		System.out.println(exception);
		    	}
		        // Der Speicherort ist erstellt und wird befuellt
		    	request.receive(file);
		    } else System.out.println("Request does not work");
		    return file; // Rueckgabe der Datei
		} catch (HttpRequestException exception) {
			System.out.println(exception);
			return null;			
	    }
	}
	
	/**
	 * Die Methode gibt ein Array zurück, in dem die uuids für die Bilder drin sind. 
	 * Die Uuid wird benoetigt, um die Anfrage zu vervollstaendigen und die entsprechende 
	 * Datei herunterzuladen.
	 * @param Die Methode erhaelt ein String mit dem Dateipfad zur Datei die geparsed werden soll.
	 * @return Ein String-Array, welches alle Uuids enthaelt, die in der Datei (JSON) drin sind.
	 */
	
	public static String[] parse(String stringpath) {
		String[] images; // Enthaelt alle uuids, bzw. alle Bez. für Bilder, die hier gefunden werden
		try {
			JSONParser parser = new JSONParser(); // Intialisierung eines Objekt vom Typ JSONParse
	        Object obj = parser.parse(new FileReader(stringpath)); // Intialisierung eines Objekt vom Typ Object
	        JSONObject jsonObject = (JSONObject) obj; 
	        // Intialisierung eines Objekt vom Typ JSONArray und Befuellung des Arrays mit allen Produkten des JSONs  
	       	JSONArray namearr = (JSONArray) jsonObject.get("products"); 
	       	images = new String[namearr.size()]; // Intialisierung eines Objekt vom Typ String
	       	for(int i=0; i<namearr.size(); i++) {
	       		// Intialisierung eines JSONObjects welches das i-te Produkt enthaelt
	       		JSONObject jsonProduct = (JSONObject) namearr.get(i);
	       		// Intialisierung eines JSONArrays welches die Summary der i-ten Produkts enthaelt
	       		JSONArray dateArr = (JSONArray)jsonProduct.get("summary");
	       		// Ausgabe des Datums und der Groeße des i-ten Objekts
	          	System.out.println((i+1)+". "+dateArr.get(0)+", Size: "+dateArr.get(6));
	          	images[i] = (String)jsonProduct.get("uuid"); // Zuweisung der uuid des i-ten Objekts in ein Array
	       	}
		 } catch (Exception ex) {
	     	System.out.println(ex.getMessage());
	     	images = null;
	  	}
		return images; // Rueckgabe des String-Arrays mit den uuids
	}
}
