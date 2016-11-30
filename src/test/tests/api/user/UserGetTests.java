package test.tests.api.user;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.labyrinth.client.UserClient;

import test.helpers.UserVerifier;

public class UserGetTests extends UserAPITest
{
	private UserVerifier verifier;
	
	@BeforeTest
	public void setup()
	{
		System.out.println("STARTING USER GET TESTS");
		verifier = new UserVerifier();
		createNewClient();
	}
	
	@Test
	public void getCurrentUser()
	{
		createNewUser();
		String resp = client.getUser();
		
		if(!verifier.verifyUser(resp))
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
