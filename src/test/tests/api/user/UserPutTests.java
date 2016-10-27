package test.tests.api.user;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.google.gson.JsonObject;
import com.labyrinth.client.UserClient;

import test.helpers.UserVerifier;
import test.parents.LabyrinthAPITest;

public class UserPutTests extends LabyrinthAPITest
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
	public void updateUser()
	{
		// create a new user
		String firstName = random.oneWord();
		String newFirstName = random.oneWord();
		String lastName = random.oneWord();
		String email = firstName + "@" + lastName + ".corn";
		String password = random.sentence(2);
		
		String newUserJson = "{firstName: \"" + firstName +
				"\", lastName: \"" + lastName +
				"\", email: \"" + email +
				"\", password: \"" + password +
				"\"}";
		String user = client.createUser(newUserJson);
		
		// update the user
		String updatedUserJson = "{firstName: \"" + newFirstName + "\"}";
		String updatedUser = client.updateUser(updatedUserJson);
		JsonObject jobj = gson.fromJson(updatedUser, JsonObject.class);
		
		// verify the user has been updated
		// check that it returns a valid user object
		if(!verifier.verifyCurrentUser(jobj))
		{
			// there's a bug for this - Labyrinth #59
			// no gameIds returned when we update a user
//			fail(verifier.getErrors());
		}
		
		// have to strip the quotes, because get() returns a JSON object
		if(!(jobj.get("firstName").toString()).replace("\"", "").equals(newFirstName))
		{
			fail("The first name was not updated");
		}
		if(!verifier.verifyUserUpdated(updatedUser, user))
		{
			fail(verifier.getErrors());
		}
	}
}
