package org.lakedetection;

import com.github.kevinsawicki.http.HttpRequest;
import com.github.kevinsawicki.http.HttpRequest.HttpRequestException;


import java.io.File;
import java.io.IOException;

import java.io.BufferedReader; 
import java.io.IOException; 
import java.io.InputStreamReader; 

public class Request{
	public File requesting() {
		LoginScihub userdata = new LoginScihub();
		userdata.login();
		System.out.println("Status: " + userdata.getPassword());
		System.out.println("Status: " + userdata.getUsername());
    	      
	
		try {
			//	https://jsonplaceholder.typicode.com/posts
			// .get("https://scihub.copernicus.eu/dhus/odata/v1/Products('9d08fd3f-06d9-406d-a021-b1a6291589eb')/$value").basic("username", "p4ssw0rd")
			boolean one = true;
			while(one) {
				HttpRequest request =  HttpRequest.get("https://scihub.copernicus.eu/dhus/odata/v1/Products('9d08fd3f-06d9-406d-a021-b1a6291589eb')/$value").basic(userdata.getUsername(), userdata.getPassword());
				if(request.code() == 401) {
					userdata.login();
				}else {
					one = false;
				}
			}
			HttpRequest request =  HttpRequest.get("https://scihub.copernicus.eu/dhus/odata/v1/Products('9d08fd3f-06d9-406d-a021-b1a6291589eb')/$value").basic(userdata.getUsername(), userdata.getPassword());
		    File file = null;
		    System.out.println("Status: " + request.code());
		      if (request.ok()) {
		    	  //System.out.println(request.body());
		    	try {
		    		// absolute Path: C:\\Users\\Dorian\\Documents\\GitHub\\softwarepraktikum2020\\src\\main\\java\\org\\lakedetection
		    		file = File.createTempFile("test", ".zip", new File(".//"));
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