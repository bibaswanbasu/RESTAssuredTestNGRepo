package bb.RESTfw.endpoints;

import static io.restassured.RestAssured.given;

import bb.RESTfw.payload.CreateUser;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

/*
 * IN THIS CLASS WE HAVE FETCHED THE ENDPOINTS FROM RoutesUsers.java class insteed of properties file
 * WE ARE NOT USING THIS IN THE CURRENT FRAMEWORK
 */
public class UserEndPoints_FromClass {

	// passing the CreateUser pojo class obejct as payload in the parameter
	public static Response createUser(CreateUser payload) {
		Response response = given().accept(ContentType.JSON).contentType(ContentType.JSON).body(payload)
//			.log().body()
				.when().post(RoutesUsers.POST_URL);

		return response;
	}

	public static Response getUser(String username) {
		Response response = given().accept(ContentType.JSON).pathParam("username", username)
//			.log().uri()
				.when().get(RoutesUsers.GET_URL);

		return response;
	}

	public static Response updateUser(String username, CreateUser payload) {
		Response response = given().accept(ContentType.JSON).contentType(ContentType.JSON)
				.pathParam("username", username).body(payload).when().put(RoutesUsers.PUT_URL);

		return response;
	}

	public static Response deleteUser(String username) {
		Response response = given().accept(ContentType.JSON).pathParam("username", username).when()
				.delete(RoutesUsers.DELETE_URL);

		return response;
	}

}
