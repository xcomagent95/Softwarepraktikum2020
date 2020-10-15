package org.lakedetection;


import java.io.BufferedReader; 
import java.io.IOException; 
import java.io.InputStreamReader;
/**
 * @version     1.0       
 *  */
public class LoginScihub {
	// Reading data using readLine 
	String username = null; // initialize username
	String password = null; // initialize password
	/**
	 * setting the scihub login data  from the user in console
	 */
	public void login() {
		// initialize
		BufferedReader reader =  
                new BufferedReader(new InputStreamReader(System.in)); 
      	
		System.out.println("Insert your username for SciHub"); //user support
		try {
			username = reader.readLine(); // read user name from console
		} catch (IOException e) {
			e.printStackTrace();
		}

        System.out.println("insert your password");
		try {
			password = reader.readLine(); // read password from console
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * getting the scihub Username
	 * @return String username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * getting the scihub Password
	 * @return String password
	 */
	public String getPassword() {
		return password;
	}
}
