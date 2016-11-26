package test.tests.api.user;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.google.gson.JsonObject;
import com.labyrinth.client.UserClient;

import test.helpers.UserVerifier;

public class UserGetTests extends UserAPITest
{
	private UserVerifier verifier;
	
	@BeforeTest
	public void setup()
	{
		System.out.println("STARTNG USER GET TESTS");
		verifier = new UserVerifier();
		createNewClient();
	}
	
	@Test
	public void getCurrentUser()
	{
		createNewUser();
		String resp = client.getUser();
		JsonObject user = gson.fromJson(resp, JsonObject.class);
		
		if(!verifier.verifyUser(user))
		{
			fail(verifier.getErrors());
		}
	}
	
	@Test
	public void getUserWithBadAuthentication()
	{
		UserClient badAuthClient = new UserClient(username, "bad password");
		String resp = badAuthClient.getUser();
		if(!resp.contains("There is no Player matching that email-password combination"))
		{
			fail("The error message doesn't match what was expected");
		}
	}
}
