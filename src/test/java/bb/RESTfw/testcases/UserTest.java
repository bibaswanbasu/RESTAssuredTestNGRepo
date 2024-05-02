package bb.RESTfw.testcases;

import java.io.IOException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;
import com.prac.location.utils.ExcelReader;

import bb.RESTfw.endpoints.UserEndPoints;
import bb.RESTfw.payload.CreateUser;
import io.restassured.response.Response;

public class UserTest {

	Faker faker;
	CreateUser createUserPayload;
	public static Logger logger;

	@BeforeClass
	public void generateTestData() {
		faker = new Faker();
		createUserPayload = new CreateUser();

		createUserPayload.setId(faker.idNumber().hashCode());
		createUserPayload.setUsername(faker.name().username());
		createUserPayload.setFirstName(faker.name().firstName());
		createUserPayload.setLastName(faker.name().lastName());
		createUserPayload.setEmail(faker.internet().safeEmailAddress());
		createUserPayload.setPassword(faker.internet().password(5, 10));
		createUserPayload.setPhone(faker.phoneNumber().cellPhone());

//		obtain logger
		logger = LogManager.getLogger("PetStoreAPI");

	}

	@Test(priority = 1, enabled = true)
	public void testCreateUser() {
		try {
			List<String> firstNames = ExcelReader.readExcelByColumnName("CreateUser", "firstName");
		} catch (IOException e) {
			e.printStackTrace();
		}

		Response createUserResponse = UserEndPoints.createUser(createUserPayload);

		createUserResponse.then().log().all();

		Assert.assertEquals(createUserResponse.getStatusCode(), 200);

		Assert.assertEquals(createUserPayload.getId(),
				Integer.parseInt(createUserResponse.jsonPath().getString("message")));

		logger.info("[LOG] Create User Executed");

	}

	@Test(priority = 2, enabled = true)
	public void testGetUser() {

		Response getUserResponse = UserEndPoints.getUser(this.createUserPayload.getUsername());

		getUserResponse.then().log().all();

		Assert.assertEquals(getUserResponse.getStatusCode(), 200);
		System.out.println("UserName is - " + this.createUserPayload.getUsername());

		logger.info("[LOG] GET User Executed");
	}

	@Test(priority = 3, enabled = true)
	public void testUpdateUser() {

		System.out.println("Old FirstName is - " + this.createUserPayload.getFirstName());

		// update only first name using the faker method
		createUserPayload.setFirstName(faker.name().firstName());

		Response updateUserResponse = UserEndPoints.updateUser(this.createUserPayload.getFirstName(),
				createUserPayload);

		updateUserResponse.then().log().all();

		Assert.assertEquals(updateUserResponse.getStatusCode(), 200);

		Response getUserResponse = UserEndPoints.getUser(this.createUserPayload.getUsername());
		System.out.println("Updated FirstName- " + getUserResponse.jsonPath().getString("firstName"));

		getUserResponse.then().log().all();

		logger.info("[LOG] User Updated");
	}

	@Test(priority = 4, enabled = true)
	public void testDeleteUser() {

		Response getUserResponse = UserEndPoints.deleteUser(this.createUserPayload.getUsername());

		getUserResponse.then().log().all();

		Assert.assertEquals(getUserResponse.getStatusCode(), 200);
		logger.warn("[LOG] User DELETED");
	}

}
