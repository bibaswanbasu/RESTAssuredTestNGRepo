package bb.RESTfw.testcases;

import org.testng.Assert;
import org.testng.annotations.Test;

import bb.RESTfw.endpoints.UserEndPoints;
import bb.RESTfw.payload.CreateUser;
import bb.RESTfw.utilities.DataProviders;
import io.restassured.response.Response;

public class UserTest_DD {

	@Test(priority = 1, dataProvider = "userData", dataProviderClass = DataProviders.class, enabled = true)
	public void testCreateUser(String id, String firstName, String lastName, String username, String email,
			String phone, String password, String userStatus) {

		CreateUser createUserPayload = new CreateUser();

		createUserPayload.setId(Integer.parseInt(id));
		createUserPayload.setUsername(username);
		createUserPayload.setFirstName(firstName);
		createUserPayload.setLastName(lastName);
		createUserPayload.setEmail(email);
		createUserPayload.setPassword(password);
		createUserPayload.setPhone(phone);

		Response createUserResponse = UserEndPoints.createUser(createUserPayload);

//		createUserResponse.then().log().all();

		// Assertions:
		// 1. Validate Status Code
		Assert.assertEquals(createUserResponse.getStatusCode(), 200);

		// 2. Validate id
		Assert.assertEquals(createUserPayload.getId(),
				Integer.parseInt(createUserResponse.jsonPath().getString("message")));

		System.out.println(
				"[INFO] USER CREATED - " + createUserPayload.getUsername() + "(" + createUserPayload.getId() + ")");

	}

	@Test(priority = 2, dataProvider = "userNameData", dataProviderClass = DataProviders.class, enabled = true)
	public void testDeleteUser(String userName) {

		Response getUserResponse = UserEndPoints.deleteUser(userName);

//		getUserResponse.then().log().all();

		Assert.assertEquals(getUserResponse.getStatusCode(), 200);

		System.out.println("[INFO] USER DELETED SUCCESSFULLY - " + userName);

	}

	@Test(priority = 3, dataProvider = "userNameData", dataProviderClass = DataProviders.class, enabled = true)
	public void testGetUser(String userName) {

		Response getUserResponse = UserEndPoints.getUser(userName);

		// Assertion:1- Status code should be 404
		Assert.assertEquals(getUserResponse.getStatusCode(), 404);

		// Assertion:2- message should be "User not found"
		Assert.assertTrue(getUserResponse.jsonPath().getString("message").equalsIgnoreCase("User not found"));
		
		System.out.println("[INFO] USER NOT FOUND - " + userName);

	}

}
