package org.lakedetection;

import com.github.kevinsawicki.http.HttpRequest;
import com.github.kevinsawicki.http.HttpRequest.HttpRequestException;


import java.io.File;
import java.io.IOException;

import java.io.BufferedReader; 
import java.io.IOException; 
import java.io.InputStreamReader; 
/**
 * @version     1.0                 (current version number of program)
 *  */
public class Request{
	/**
	 * Sends a request to Scihub to get a satellite image
	 * @return Zip Folder  with data of API Hub.
	 */
	public File requesting() {
		/*
		 *  Asks the User in the console for his userdata (password and username) 
		 */
		LoginScihub userdata = new LoginScihub();
		userdata.login(); // do the scihub userdata login stuff
		
		BufferedReader reader =  
                new BufferedReader(new InputStreamReader(System.in)); 
      
		
		System.out.println("Insert a ID-type of file e.g. 9d08fd3f-06d9-406d-a021-b1a6291589eb");
		
		/*
		 * Asking for the date set id to be downloaded  
		 */
		String url = "https://scihub.copernicus.eu/dhus/odata/v1/Products('";
		try {
			String id = reader.readLine();
			url += id; // adding the id to the url
		} catch (IOException e) {
			e.printStackTrace();
		}
		url += "')/$value"; // finish url
		System.out.println("requesting following url= " + url); // show url		
		
		
		/*
		 * If authorisation fails the console will ask again for
		 * the userdata
		 */
		
		try {
			//	https://jsonplaceholder.typicode.com/posts
			// .get("https://scihub.copernicus.eu/dhus/odata/v1/Products('9d08fd3f-06d9-406d-a021-b1a6291589eb')/$value").basic("username", "p4ssw0rd")
			boolean one = true;
			while(one) {
				HttpRequest request =  HttpRequest.get(url).basic(userdata.getUsername(), userdata.getPassword());
				if(request.code() == 401) { // Authorisation denied
					userdata.login(); // do the user data login
				}else {
					one = false; // stop loop if authorisation succeeded
				}
			}
			
			HttpRequest request =  HttpRequest.get(url).basic(userdata.getUsername(), userdata.getPassword());
		    File file = null; // initialize
		    /*
		     * Downloading the file
		     */
		    System.out.println("Status: " + request.code());
		      if (request.ok()) {
		    	try {
		    		file = File.createTempFile("img", ".zip", new File(".//")); // creat Zip file
		    		System.out.println("File path: "+ file.getAbsolutePath()); // print the path
		    		System.out.println("Downloading"); // let User see the status
		    	} catch(IOException exception) {
		    		System.out.println(exception);
		    	}
		        request.receive(file); // receiving file
		      }
		     return file;
		
		} catch (HttpRequestException exception) {
			System.out.println(exception);
			return null;
	    }
	}
}