package test.tests.api.user;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.google.gson.JsonObject;
import com.labyrinth.client.UserClient;

import test.helpers.UserVerifier;
import test.parents.LabyrinthAPITest;

public class UserPostTests extends LabyrinthAPITest
{
	private UserClient client;
	private UserVerifier verifier;

	@BeforeTest
	public void setup()
	{
		System.out.println("STARTNG USER POST TESTS");
		client = new UserClient("eric@eric.corn", "1qweqwe");
		verifier = new UserVerifier();
	}

	@Test
	public void createNewUser()
	{
		String firstName = faker.getFirstName();
		String lastName = faker.getLastName();
		String email = firstName + "@" + lastName + ".corn";
		String password = faker.getPassword();
		
		// create json for a new user
		String userJson = "{firstName: \"" + firstName +
				"\", lastName: \"" + lastName +
				"\", email: \"" + email +
				"\", password: \"" + password +
				"\"}";
		String user = client.createUser(userJson);
		
		// verify response has a user
		if(!verifier.verifyUser(gson.fromJson(user, JsonObject.class)))
		{
			fail(verifier.getErrors());
		}
		
		// verify user was created
		UserClient client2 = new UserClient(email, password);
		String getUser = client2.getUser();
		
		if(!verifier.verifyUser(gson.fromJson(getUser, JsonObject.class)))
		{
			fail(verifier.getErrors());
		}
	}
	
	@Test
	public void createUserWithError()
	{
		String firstName = faker.getFirstName();
		String lastName = faker.getLastName();
		String email = firstName + "@" + lastName + ".corn";
		String password = faker.getPassword();
		String expectedMessage = "";
		int mod = rand.nextInt(4);

		// randomly set one of the fields to an empty string
		switch(mod % 4)
		{
			case 0:
				firstName = "";
				expectedMessage = "The Player needs to have a first name";
				break;
			case 1:
				lastName = "";
				expectedMessage = "The Player needs to have a last name";
				break;
			case 2:
				email = "";
				expectedMessage = "You need to include an email address";
				break;
			case 3:
				password = "";
				expectedMessage = "The password needs to be more than six (6) characters";
				break;
		}
		
		// create json for a new user
		String userJson = "{firstName: \"" + firstName +
				"\", lastName: \"" + lastName +
				"\", email: \"" + email +
				"\", password: \"" + password +
				"\"}";
		
		// this will be an error message
		String postUser = client.createUser(userJson);
		
		//Labyrinth bug #64 - no error returned when missing field
		if(!postUser.contains(expectedMessage))
		{
			System.out.println("Response was: " + postUser);
			fail("The error message returned was not correct");
		}
	}
}
