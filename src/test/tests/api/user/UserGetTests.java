package test.tests.api.user;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.google.gson.JsonObject;
import com.labyrinth.client.UserClient;

import test.helpers.UserVerifier;
import test.parents.LabyrinthAPITest;

public class UserGetTests extends LabyrinthAPITest
{
	private UserVerifier verifier;
	private UserClient client;
	
	@BeforeTest
	public void setup()
	{
		System.out.println("STARTNG USER GET TESTS");
		verifier = new UserVerifier();
		client = new UserClient(username, password);
	}
	
	@Test
	public void getCurrentUser()
	{
		String resp = client.getUser();
		JsonObject user = gson.fromJson(resp, JsonObject.class);
		
		if(!verifier.verifyUser(user))
		{
			fail(verifier.getErrors());
		}
	}
}
