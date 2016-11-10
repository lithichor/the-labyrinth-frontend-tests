package test.tests.api.user;

import java.util.ArrayList;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.google.gson.JsonObject;

import test.helpers.UserVerifier;
import test.models.constants.LabyrinthTestConstants;

public class UserPutTests extends UserAPITest
{
	private UserVerifier verifier;

	@BeforeTest
	public void setup()
	{
		System.out.println("STARTNG USER PUT TESTS");
		verifier = new UserVerifier();
		createNewClient();
	}
	
	@Test
	public void updateUser()
	{
		// create a new user
		String user = createNewUser();
		
		// update the first name of the user
		String newFirstName = random.oneWord();
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
