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
		
		String updatedUser = client.updateUser(updatedUserJson);
		JsonObject jobj = gson.fromJson(updatedUser, JsonObject.class);
		
		// verify the user has been updated
		// check that it returns a valid user object
		if(!verifier.verifyUser(jobj))
		{
			fail(verifier.getErrors());
		}
		
		if(LabyrinthTestConstants.FIRST_NAME.equals(changedFields.get(0)) &&
				!jobj.get("firstName").getAsString().equals(newFirstName))
		{
			fail("The first name was not updated");
		}
		if(LabyrinthTestConstants.LAST_NAME.equals(changedFields.get(0)) &&
				!jobj.get("lastName").getAsString().equals(newLastName))
		{
			fail("The last name was not updated");
		}
		if(!verifier.verifyUserUpdated(updatedUser, user, changedFields))
		{
			fail(verifier.getErrors());
		}
	}
}
