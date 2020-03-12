package org.lakedetection;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import com.github.kevinsawicki.http.HttpRequest;
import com.github.kevinsawicki.http.HttpRequest.HttpRequestException;


public class FindSets {
	// insert array [[lng, lat], [lng, lat], ...]
	// -->type double
	// Since we use a polygon the last array entry must be equal to the first
	public File search() {
		// double[][] coords
		String url = new String("https://scihub.copernicus.eu/dhus/search?start=0&rows=100&q=(platformname:Sentinel-1%20AND%20ingestiondate%5b");
		BufferedReader reader =  
                new BufferedReader(new InputStreamReader(System.in)); 
      
		//2014-01-01T00:00:00.000Z
		String startdate = null;
		System.out.println("Insert the startdate e.g. 2014-01-01T00:00:00.000Z OR NOW-XDays");
		try {
			startdate = reader.readLine();
			url += startdate;
		} catch (IOException e) {
			e.printStackTrace();
		}
		url+= "%20TO%20";
		String enddate = null;
		System.out.println("Insert the enddate e.g. 2019-01-01T00:00:00.000Z OR NOW");
		try {
			enddate = reader.readLine();
			url += enddate;
		} catch (IOException e) {
			e.printStackTrace();
		}
		url += "%5d%20AND%20footprint:%22Intersects(POLYGON((";
		
		
		String num = null;
		System.out.println("Insert your Polygon e.g. -4.53%2029.85,26.75%2029.85,26.75%2046.80,-4.53%2046.80,-4.53%2029.85  --> instead of space do %20");
		try {
			num = reader.readLine();
			url += num;
		} catch (IOException e) {
			e.printStackTrace();
		}
		url += ")))%22)";
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
	    
	    LoginScihub userdata = new LoginScihub();
		userdata.login();
    	
	    try {
			//	https://jsonplaceholder.typicode.com/posts
			// .get("https://scihub.copernicus.eu/dhus/odata/v1/Products('9d08fd3f-06d9-406d-a021-b1a6291589eb')/$value").basic("username", "p4ssw0rd")
			boolean one = true;
			while(one) {
				HttpRequest request =  HttpRequest.get("https://scihub.copernicus.eu/dhus/odata/v1/Products('9d08fd3f-06d9-406d-a021-b1a6291589eb')/$value").basic(userdata.getUsername(), userdata.getPassword());
				if(request.code() == 401) {
					System.out.println("Error 401: Invalid Authentification. Try Again :)");
					userdata.login();
				}else {
					one = false;
				}
			}
			HttpRequest request =  HttpRequest.get(url).basic(userdata.getUsername(), userdata.getPassword());
		    File file = null;
		    System.out.println("Status: " + request.code());
		      if (request.ok()) {
		    	  //System.out.println(request.body());
		    	try {
		    		// absolute Path: C:\\Users\\Dorian\\Documents\\GitHub\\softwarepraktikum2020\\src\\main\\java\\org\\lakedetection
		    		file = File.createTempFile("infoxml", ".xml", new File(".//"));
		    		System.out.println("File path: "+ file.getAbsolutePath());
		    		System.out.println("Downloaded");
		    	} catch(IOException exception) {
		    		System.out.println(exception);
		    	}
		        request.receive(file);
		        //publishProgress(file.length());
		      }
		     return file;
		      
			//int status =  HttpRequest.get("https://jsonplaceholder.typicode.com/todos/1").code();
			//System.out.println(status);
			//String body =  HttpRequest.get("https://jsonplaceholder.typicode.com/todos/1").body();
			//System.out.println(body);
		
		} catch (HttpRequestException exception) {
			System.out.println(exception);
			return null;
	    }	
	}
}
