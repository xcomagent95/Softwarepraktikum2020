package org.lakedetection;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

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
		// String url = new String("https://scihub.copernicus.eu/dhus/api/stub/products?filter=(%20footprint:%22Intersects(POLYGON((12.847298091933668%2053.18180695455385,12.949362582961403%2053.18180695455385,12.949362582961403%2053.22056068128961,12.847298091933668%2053.22056068128961,12.847298091933668%2053.18180695455385)))%22%20)%20AND%20(%20%20(platformname:Sentinel-1%20AND%20producttype:GRD%20AND%20polarisationmode:VV+VH))&offset=0&limit=25&sortedby=beginposition&order=desc");// set base url
		String url = new String("https://scihub.copernicus.eu/dhus/api/stub/products?filter=(%20footprint:%22Intersects(POLYGON((12.847298091933668%2053.18180695455385,12.949362582961403%2053.18180695455385,12.949362582961403%2053.22056068128961,12.847298091933668%2053.22056068128961,12.847298091933668%2053.18180695455385)))%22%20)%20AND%20(%20beginPosition:[");
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in)); 
   		String startdate = null; // initialize startdate
		//System.out.println("Insert the startdate e.g. 2015-01-01T00:00:00.000Z OR NOW-XDays"); // Console show Info
		System.out.println("Insert the startdate e.g. 2020-08-01");
		try {
			startdate = reader.readLine(); //get startdate
			url += startdate;
		} catch (IOException e) {
			e.printStackTrace();
		}
		url+= "T00:00:00.000Z%20TO%20NOW]%20AND%20endPosition:[";
		String enddate = null;
		System.out.println("Insert the enddate e.g. 2020-10-01");
		try {
			enddate = reader.readLine(); // add enddate in url
			url += enddate;
		} catch (IOException e) {
			e.printStackTrace();
		}
		url += "T00:00:00.000Z%20TO%20NOW]%20)%20AND%20(%20%20(platformname:Sentinel-1%20AND%20producttype:GRD%20AND%20polarisationmode:VV+VH))&offset=0&limit=25&sortedby=beginposition&order=desc";
		
	    System.out.println("The Url is:  " + url);	    
    	userdata.login();
	    
	    try {
			HttpRequest request =  HttpRequest.get(url).basic(userdata.getUsername(), userdata.getPassword());
		    File file = null;
		    System.out.println("Status: " + request.code());
		    String imageNumber = "Variable 'imageNumber' has not been initialized";
		    if (request.ok()) { // check request if Code 200  
		    	try {
		    		file = File.createTempFile("sets", ".json", new File(".//")); //set file
		    		System.out.println("File path: "+ file.getAbsolutePath()); // show filepath on user pc
		    		System.out.println("Downloading"); // show user progress
		    	} catch(IOException exception) {
		    		System.out.println(exception);
		    	}
		        request.receive(file);
		        
		        int number = 0;
		        String[] images = parse(file.getAbsolutePath());
		        System.out.println("Insert the the index of the date of the image you want e.g. if it is the first one (1.), enter 1");
				try {
					number = Integer.valueOf(reader.readLine()); //get number of the date 
					System.out.println(number);
				} catch (IOException e) {
					e.printStackTrace();
				}
				
		        imageNumber = images[number-1];
		    } else System.out.println("Request does not work");
		    return imageNumber;
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
			HttpRequest request =  HttpRequest.get("https://scihub.copernicus.eu/dhus/odata/v1/Products('"+ firstImage +"')/$value").basic(userdata.getUsername(), userdata.getPassword());
		    
			File file = null;
		    System.out.println("Status: " + request.code());
		    if (request.ok()) { // check request if Code 200  
		    	try {
		    		// Speichert die Datei unter dem "Identiefier" als Namen 
		    		String fileName = request.header("Content-Disposition").substring(17, request.header("Content-Disposition").length()-1);
		    		file = new File(".//"+fileName);
		    		file.createNewFile();//fileName, ".zip", new File(".//")); //set file
		    		System.out.println("File path: "+ file.getAbsolutePath()); // show filepath on user pc
		    		System.out.println("Downloading"); // show user progress
		    	} catch(IOException exception) {
		    		System.out.println(exception);
		    	}
		        // Der Speicherort ist erstellt und wird befuellt
		    	request.receive(file);
		    } else System.out.println("Request does not work");
		    return file;
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
			JSONParser parser = new JSONParser();
	        Object obj = parser.parse(new FileReader(stringpath));
	        JSONObject jsonObject = (JSONObject) obj; 
	       	JSONArray namearr = (JSONArray) jsonObject.get("products");
	       	images = new String[namearr.size()]; 
	       	for(int i=0; i<namearr.size(); i++) {
	       		JSONObject jsonProduct = (JSONObject) namearr.get(i);
	       		JSONArray dateArr = (JSONArray)jsonProduct.get("summary");
	          	System.out.println((i+1)+". "+dateArr.get(0));
	          	images[i] = (String)jsonProduct.get("uuid");
	       	}
		 } catch (Exception ex) {
	     	System.out.println(ex.getMessage());
	     	images = null;
	  	}
		return images;
	}
}
