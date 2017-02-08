package test.tests.api.user;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.labyrinth.client.UserClient;

public class UserGetTests extends UserAPITest
{
	private UserVerifier verifier;
	
	@BeforeTest
	public void setup()
	{
		System.out.println("STARTING USER GET TESTS");
		verifier = new UserVerifier();
		createNewUserClient();
	}
	
	@Test
	public void getCurrentUser()
	{
		createNewUser();
		String resp = client.getUser();
		
		assertTrue(verifier.verifyUser(resp), verifier.getErrorsAsString());
	}
	
	@Test
	public void getUserWithBadAuthentication()
	{
		UserClient badAuthClient = new UserClient(email, "bad password");
		String resp = badAuthClient.getUser();
		assertTrue(resp.contains("There is no Player matching that email-password combination"),
				"The error message doesn't match what was expected");
	}
}
