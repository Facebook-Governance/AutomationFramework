package com.Utils;

import java.util.ArrayList;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class ApiUtils {
	protected Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());
	private RequestSpecification requestSpecification;
	private Response response;

	public ApiUtils() {
		requestSpecification = RestAssured.given();
	}

	public void setTheBaseURI(String baseURI) {
		logger.info("Setting the base URI as :: " + baseURI);
		requestSpecification.baseUri(baseURI);
	}

	public void setHeaders(HashMap<String, String> header) {
		logger.info("Setting the Header :: " + header);

		requestSpecification.headers(header);
	}

	public Response get(String URI) {
		response = requestSpecification.get(URI);
		return response;
	}

	public Response get() {
		logger.info("Fetching the Response :: ");

		response = requestSpecification.get();
		return response;
	}

	public Response post(String URI, String body) {
		logger.info("Setting the URI and Body to be passed when calling a API :: " + URI + " Body ::" + body);

		requestSpecification.body(body);
		return response = requestSpecification.post(URI);
	}

	public Response put(String URI, String body) {
		requestSpecification.body(body);
		return response = requestSpecification.put(URI);
	}

	public Response delete(String URI) {
		return response = requestSpecification.delete(URI);
	}

	public int getStatusCode() {
		logger.info("Fetching the status code from API Call");

		return response.getStatusCode();
	}

	public String getStatusLine() {
		return response.getStatusLine();
	}

	public String getReponse() {
		return response.asString();
	}

	public String getValues(String jsonPath) {
		logger.info("Reading the response from API Call ::" + jsonPath);

		return response.jsonPath().get(jsonPath);
	}

	public int getIDValues(String jsonPath) {
		logger.info("Reading the  Integer response from API Call ::" + jsonPath);

		return response.jsonPath().get(jsonPath);
	}

	public ArrayList<String> getArrayOfValues(String jsonPath) {
		logger.info("Converting the object in arraylist::" + jsonPath);

		return response.jsonPath().get(jsonPath);
	}

	public ArrayList<Integer> getArrayOfIDValues(String jsonPath) {
		logger.info("Reading response and converting it to Arraylist of Integers" + jsonPath);

		return response.jsonPath().get(jsonPath);
	}
}
