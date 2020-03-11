package org.lakedetection;

import com.github.kevinsawicki.http.HttpRequest;

public class Request{
	 HttpRequest request =  HttpRequest.get("https://pokeapi.co/api/v2/pokemon/ditto/").receive(System.out);
}