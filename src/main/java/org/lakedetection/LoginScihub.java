package org.lakedetection;

import java.io.BufferedReader; 
import java.io.IOException; 
import java.io.InputStreamReader;
/**
 * @author Dorian Henningfeld
 * @version     1.0       
 *  */
public class LoginScihub {
	String username = null; // initialize username
	String password = null; // initialize password
	/**
	 * Abfragen der Login-Daten des Benutzers, um sich bei Scihub einzuloggen
	 */
	public void login() {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in)); 
      	
		System.out.println("Insert your username for SciHub"); //user support
		try {
			username = reader.readLine(); // Lesen des Benutzernamens von der Konsole
		} catch (IOException e) {
			e.printStackTrace();
		}

        System.out.println("insert your password");
		try {
			password = reader.readLine(); // Lesen des Passworts von der Konsole
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Getter für den Benutzernamen
	 * @return Benutzername als String
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Getter für das Passwort
	 * @return Passwort als String
	 */
	public String getPassword() {
		return password;
	}
}
