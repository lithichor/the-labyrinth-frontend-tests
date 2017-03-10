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
		String user = createNewUser();
		
		String newFirstName = random.oneWord();
		String newLastName = random.oneWord();
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
		
		String updatedUser = userClient.updateUser(updatedUserJson);
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
		String originalUser = createNewUser();
		JsonObject userObj = gson.fromJson(originalUser, JsonObject.class);
		String email = userObj.get("email").getAsString();
		
		// make a new API client for that user (password is from parent object)
		userClient = new UserClient(email, password);
		
		// reset the user's password
		String password = random.oneWord() + rand.nextInt(10) + random.oneWord().toUpperCase();
		userClient.updateUser("{password: " + password + "}");
		
		// attempt to get user (this should fail)
		String newUser = userClient.getUser();
		String message = "There is no Player matching that email-password combination";
		assertTrue(newUser.contains(message), "The password may not have been updated;\n"
				+ "Expected: " + message
				+ "\n Actual: " + newUser + "\n");
		
		// create new client with new password
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
}
