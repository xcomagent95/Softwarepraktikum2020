package org.lakedetection;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.github.kevinsawicki.http.HttpRequest;
import com.github.kevinsawicki.http.HttpRequest.HttpRequestException;


/**
 * @version     1.0                 (current version number of program)
 *  */
public class FindSets {
	
	LoginScihub userdata = new LoginScihub();
	
	// insert array [[lng, lat], [lng, lat], ...]
	// -->type double
	// Since we use a polygon the last array entry must be equal to the first
	
	/**
	 * Sends a request to Scihub to get a xml file with potential data sets
	 * @return Zip Folder  with data of API Hub.
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
		//url+= "2018-01-01T00:00:00.000Z";
		//url+= "%20TO%20"; // Add <from> TO <to> in url
		url+= "T00:00:00.000Z%20TO%20NOW]%20AND%20endPosition:[";
		String enddate = null;
		//System.out.println("Insert the enddate e.g. 2019-01-01T00:00:00.000Z OR NOW");
		System.out.println("Insert the enddate e.g. 2020-10-01");
		try {
			enddate = reader.readLine(); // add enddate in url
			url += enddate;
		} catch (IOException e) {
			e.printStackTrace();
		}
		//url+= "2020-02-02T00:00:00.000Z";
		//url += "%5d%20AND%20footprint:%22Intersects(POLYGON((";
		url += "T00:00:00.000Z%20TO%20NOW]%20)%20AND%20(%20%20(platformname:Sentinel-1%20AND%20producttype:GRD%20AND%20polarisationmode:VV+VH))&offset=0&limit=25&sortedby=beginposition&order=desc";
		
		String num = null;
		//System.out.println("Insert your Polygon e.g. -4.53%2029.85,26.75%2029.85,26.75%2046.80,-4.53%2046.80,-4.53%2029.85  --> instead of space do %20");
		/*try {
			num = reader.readLine();
			url += num; // insert polygon
		} catch (IOException e) {
			e.printStackTrace();
		}*/
		//url+= "12.86%2053.21,12.92%2053.21,12.92%2053.19,12.86%2053.19,12.86%2053.21";
		
		//url += ")))%22)"; // finalize url
		/*
		for(int i = 0; i < coords.length; i++) {
			if(i == coords.length -1) {
				url += String.valueOf(coords[i][0]) + "%20" + String.valueOf(coords[i][1]);
			} else {
				url += String.valueOf(coords[i][0]) + "%20" + String.valueOf(coords[i][1]) + ",";
			}
			
		}
		*/
		
		//url = "https://scihub.copernicus.eu/dhus/search?q=(ingestiondate:[2019-01-01T00:00:00.000Z TO 2020-02-01T00:00:00.000Z] AND platformname:Sentinel-1 AND footprint:%22Intersects(POLYGON((-4.53 29.85,26.75 29.85,26.75 46.80,-4.53 46.80,-4.53 29.85)))%22)";
		//   ingestiondate%5b2017-01-01T00:00:00.000Z%20TO%202014-02-01T00:00:00.000Z%5d
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
	
	// Methode gibt ein Array zurück, in dem die uuids für die Bilder drin sind. 
	// Die uuid wird benötigt, um die Request zu vervollständigen und die entsprechende Datei herunterzuladen 
	public static String[] parse(String stringpath) {
		String[] images; // Enthält alle uuids, bzw. alle Bez. für Bilder, die hier gefunden werden
		try {
			JSONParser parser = new JSONParser();
	        Object obj = parser.parse(new FileReader(stringpath));
	        JSONObject jsonObject = (JSONObject) obj; 
	       	JSONArray namearr = (JSONArray) jsonObject.get("products");
	       	images = new String[namearr.size()]; 
	       	for(int i=0; i<namearr.size(); i++) {
	       		JSONObject jsonProduct = (JSONObject) namearr.get(i);
	       		//JSONObject jsonSummary = (JSONObject) jsonProduct.get(key);
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
