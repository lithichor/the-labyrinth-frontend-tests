package test.tests.api.user;

import java.util.ArrayList;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.google.gson.JsonObject;
import com.labyrinth.client.UserClient;

import test.helpers.UserVerifier;
import test.models.constants.LabyrinthTestConstants;
import test.parents.LabyrinthAPITest;

public class UserPutTests extends LabyrinthAPITest
{
	private UserClient client;
	private UserVerifier verifier;

	@BeforeTest
	public void setup()
	{
		System.out.println("STARTNG USER PUT TESTS");
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
		
		client = new UserClient(email, password);

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
		ArrayList<String> changedFields = new ArrayList<String>();
		changedFields.add(LabyrinthTestConstants.FIRST_NAME);
		
		// verify the user has been updated
		// check that it returns a valid user object
		if(!verifier.verifyUser(jobj))
		{
			fail(verifier.getErrors());
		}
		
		// have to strip the quotes, because get() returns a JSON object
		if(!(jobj.get("firstName").toString()).replace("\"", "").equals(newFirstName))
		{
			fail("The first name was not updated");
		}
		if(!verifier.verifyUserUpdated(updatedUser, user, changedFields))
		{
			fail(verifier.getErrors());
		}
	}
}
