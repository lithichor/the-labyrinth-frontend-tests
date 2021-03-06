package test.tests.api.user;

import java.util.ArrayList;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.google.gson.JsonObject;
import com.labyrinth.client.UserClient;

import test.models.constants.LabyrinthTestConstants;

public class UserPutTests extends UserAPITest
{
	@BeforeTest
	public void setup()
	{
		System.out.println("STARTING USER PUT TESTS");
		verifier = new UserVerifier();
		createNewUserClient();
	}
	
	@Test
	public void updateUser()
	{
		// create a new user
		String email = faker.getEmail();
		String password = faker.getPassword();
		String userData = "{firstName:" + faker.getFirstName() + ","
				+ "lastName:" + faker.getLastName() + ","
				+ "email:" + email + ","
				+ "password:" + password + "}";
		String user = userClient.createUser(userData);
		
		String newFirstName = faker.getFirstName();
		String newLastName = faker.getLastName();
		String updatedUserJson = "";
		ArrayList<String> changedFields = new ArrayList<String>();
		
		// randomly select first name or last name to update
		switch(rand.nextInt(2))
		{
		case 0:
			updatedUserJson = "{firstName: \"" + newFirstName + "\"}";
			changedFields.add(LabyrinthTestConstants.FIRST_NAME);
			break;
		case 1:
			updatedUserJson = "{lastName: \"" + newLastName + "\"}";
			changedFields.add(LabyrinthTestConstants.LAST_NAME);
			break;
		}
		
		UserClient newClient = new UserClient(email, password);
		String updatedUser = newClient.updateUser(updatedUserJson);
		JsonObject jobj = gson.fromJson(updatedUser, JsonObject.class);
		
		// verify the user has been updated
		// check that it returns a valid user object
		assertTrue(verifier.verifyUser(updatedUser), verifier.getErrorsAsString());

		assertFalse(LabyrinthTestConstants.FIRST_NAME.equals(changedFields.get(0)) &&
				!jobj.get("firstName").getAsString().equals(newFirstName),
				"The first name was not updated");
		assertFalse(LabyrinthTestConstants.LAST_NAME.equals(changedFields.get(0)) &&
				!jobj.get("lastName").getAsString().equals(newLastName),
				"The last name was not updated");
		assertTrue(verifier.verifyUserUpdated(updatedUser, user, changedFields),
				verifier.getErrorsAsString());
	}
	
	@Test
	public void changeUserPassword()
	{
		// make a user
		String email = faker.getEmail();
		String originalPassword = faker.getPassword();
		String data = "{firstName:" + faker.getFirstName() + ","
				+ "lastName:" + faker.getLastName() + ","
				+ "email:" + email + ","
				+ "password:" + originalPassword + "}";
		String originalUser = userClient.createUser(data);
		
		// re-create API client for that user (password is from parent object)
		userClient = new UserClient(email, originalPassword);
		
		// reset the user's password
		String password = faker.getPassword();
		userClient.updateUser("{password: " + password + "}");
		
		// attempt to get user (this should fail)
		String newUser = userClient.getUser();
		String message = "There is no Player matching that email-password combination";
		assertTrue(newUser.contains(message), "The password may not have been updated;\n"
				+ "Expected: " + message
				+ "\n Actual: " + newUser + "\n");
		
		// re-create the client with new password
		userClient = new UserClient(email, password);
		newUser = userClient.getUser();

		// the users should be identical (password is not in response)
		assertEquals(originalUser, newUser);
	}
	
	@Test
	public void updateUserWithNoData()
	{
		// create a user, then try to update it with an empty string
		createNewUser();
		String updatedUser = userClient.updateUser("");
		String message = "If you do not provide any data, then the user cannot change";
		
		assertTrue(updatedUser.contains(message),
				"The response should have contained an error message, but instead contained this:\n"
						+ updatedUser);
	}
	
	@Test
	public void updateUserWithHashForStrings()
	{
		createNewUser();
		
		String data = "{";
		String field = "";
		switch(rand.nextInt(3))
		{
		case 0:
			data += "firstName: {a: A}";
			field = "first name";
			break;
		case 1:
			data += "lastName: {2: 345}";
			field = "last name";
			break;
		case 2:
			data += "password: {0: abc}";
			field = "password";
			break;
		}
		data += "}";

		String message = "The " + field + " field has to be a String";
		String user = userClient.updateUser(data);
		
		assertTrue(user.contains(message),
				"The response should have contained:\n\t" + message + ",\nbut instead was:\n\t" + user);
	}

	@Test
	public void updateUserWithArrayForStrings()
	{
		createNewUser();
		
		String data = "{";
		String field = "";
		switch(rand.nextInt(3))
		{
		case 0:
			data += "firstName: [a, A]";
			field = "first name";
			break;
		case 1:
			data += "lastName: [2, 345]";
			field = "last name";
			break;
		case 2:
			data += "password: [2, 2]";
			field = "password";
			break;
		}
		data += "}";

		String message = "The " + field + " field has to be a String";
		String user = userClient.updateUser(data);
		
		assertTrue(user.contains(message),
				"The response should have contained:\n\t" + message + ",\nbut instead was:\n\t" + user);
	}
	
	@Test
	public void updateUserWithWeakPassword()
	{
		createNewUser();
		userClient = new UserClient(email, password);
		String newPwd = "123";
		String message = "The password needs to be six (6) or more characters long";
		String message2 = "A password has to have at least one digit (0-9), one uppercase letter, and one lowercase letter";
		
		String response = userClient.updateUser("{password:" + newPwd + "}");
		
		assertTrue(response.contains(message),
				"The response should have contained:\n\t" + message + ",\nbut instead was:\n\t" + response);
		assertTrue(response.contains(message2),
				"The response should have contained:\n\t" + message2 + ",\nbut instead was:\n\t" + response);
	}
}
