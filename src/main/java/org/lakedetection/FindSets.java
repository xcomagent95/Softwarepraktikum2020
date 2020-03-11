
package org.lakedetection;

import java.io.File;
import java.io.IOException;

import com.github.kevinsawicki.http.HttpRequest;
import com.github.kevinsawicki.http.HttpRequest.HttpRequestException;


public class FindSets {
	
	public File search(String date, int[] coords) {
		String url = new String("https://scihub.copernicus.eu/dhus/odata/v1/Products?");
		try {
			//	https://jsonplaceholder.typicode.com/posts
			// .get("https://scihub.copernicus.eu/dhus/odata/v1/Products('9d08fd3f-06d9-406d-a021-b1a6291589eb')/$value").basic("username", "p4ssw0rd")
			HttpRequest request =  HttpRequest.get(url).basic("username", "password");
		    File file = null;
		    System.out.println("Status: " + request.code());
		      if (request.ok()) {
		    	  //System.out.println(request.body());
		    	try {
		    		file = File.createTempFile("sets", ".xml", new File(".//"));
		    		System.out.println("File path: "+ file.getAbsolutePath());
		    		System.out.println("Downloaded");
		    	} catch(IOException exception) {
		    		System.out.println(exception);
		    	}
		        request.receive(file);
		        
		      }
		     return file;
		      
			
		} catch (HttpRequestException exception) {
			System.out.println(exception);
			return null;
	    }
	}
}
