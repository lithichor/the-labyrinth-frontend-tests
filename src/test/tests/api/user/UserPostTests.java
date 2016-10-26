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
		String firstName = random.oneWord();
		String lastName = random.oneWord();
		String email = firstName + "@" + lastName + ".corn";
		String password = random.sentence(2);
		
		// create a new user
		String newUser = "{firstName: \"" + firstName +
				"\", lastName: \"" + lastName +
				"\", email: \"" + email +
				"\", password: \"" + password +
				"\"}";
		String postUser = client.createUser(newUser);
		
		// verify response has a user
		if(!verifier.verifyCurrentUser(gson.fromJson(postUser, JsonObject.class)))
		{
			fail(verifier.getErrors());
		}
		
		// verify user was created
		UserClient client2 = new UserClient(email, password);
		String getUser = client2.getUser();
		
		if(!verifier.verifyCurrentUser(gson.fromJson(getUser, JsonObject.class)))
		{
			fail(verifier.getErrors());
		}
	}
}
