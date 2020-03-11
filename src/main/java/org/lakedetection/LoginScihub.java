package org.lakedetection;


import java.io.BufferedReader; 
import java.io.IOException; 
import java.io.InputStreamReader;

public class LoginScihub {
	// Reading data using readLine 
	String username = null;
	String password = null;
	public void login() {
		BufferedReader reader =  
                new BufferedReader(new InputStreamReader(System.in)); 
      
     
		
		
		System.out.println("Insert your username for SciHub");
		try {
			username = reader.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}

         // Printing the read line 
         
       
         System.out.println("insert your password");
		try {
			password = reader.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(password);
	}
	
	public String username() {
		return username;
	}
	public String password() {
		return password;
	}
}
