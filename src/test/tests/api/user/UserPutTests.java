package test.tests.api.user;

import java.util.ArrayList;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.google.gson.JsonObject;

import test.models.constants.LabyrinthTestConstants;

public class UserPutTests extends UserAPITest
{
	private UserVerifier verifier;

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
		
		String updatedUser = client.updateUser(updatedUserJson);
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
	public void updateUserWithNoData()
	{
		// create a user, then try to update it with an empty string
		createNewUser();
		String updatedUser = client.updateUser("");
		String message = "If you do not provide any data, then the user cannot change";
		
		assertTrue(updatedUser.contains(message),
				"The response should have contained an error message, but instead contained this:\n"
						+ updatedUser);
	}
	
//	@Test
	public void updateUserWithHashForStrings()
	{
		createNewUser();
		String data = "{";
		switch(rand.nextInt(2))
		{
		case 0:
			data += "firstName: {a: A}";
			break;
		case 1:
			data += "lastName: {2: 345}";
			break;
		}
		
		data += "}";
		
		String user = userClient.updateUser(data);
		
		System.out.println(user);
	}
}
