package bb.RESTfw.endpoints;

import static io.restassured.RestAssured.given;

import java.util.ResourceBundle;

import bb.RESTfw.payload.CreateUser;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class UserEndPoints {
	
	// Loading the .properties file
	static ResourceBundle getURL() {	
		ResourceBundle routes = ResourceBundle.getBundle("Routes");
		return routes;	
	}
	
	// passing the CreateUser pojo class obejct as payload in the parameter
	public static Response createUser(CreateUser payload) {
		String POST_URL = getURL().getString("POST_URL");
		
		Response response = given()
			.accept(ContentType.JSON)
			.contentType(ContentType.JSON)
			.body(payload)
//			.log().body()
		.when()
			.post(POST_URL);
//			.post(RoutesUsers.POST_URL);
		
		
		return response;		
	}
	
	public static Response getUser(String username) {
		String GET_URL = getURL().getString("GET_URL");
		
		Response response = given()
			.accept(ContentType.JSON)
			.pathParam("username", username)
//			.log().uri()
		.when()
			.get(GET_URL);
//			.get(RoutesUsers.GET_URL);
		
		return response;		
	}
	
	public static Response updateUser(String username, CreateUser payload) {
		String PUT_URL = getURL().getString("GET_URL");
		
		Response response = given()
			.accept(ContentType.JSON)
			.contentType(ContentType.JSON)
			.pathParam("username", username)
			.body(payload)
		.when()
			.put(PUT_URL);
//			.put(RoutesUsers.PUT_URL);
		
		return response;		
	}
	
	public static Response deleteUser(String username) {
		String DELETE_URL = getURL().getString("GET_URL");
		
		Response response = given()
			.accept(ContentType.JSON)
			.pathParam("username", username)
		.when()
			.delete(DELETE_URL);
//			.delete(RoutesUsers.DELETE_URL);
		
		return response;		
	}

}
