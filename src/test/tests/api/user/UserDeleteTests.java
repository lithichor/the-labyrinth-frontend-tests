package test.tests.api.user;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class UserDeleteTests extends UserAPITest
{
	@BeforeTest
	public void setup()
	{
		System.out.println("STARTNG USER DELETE TESTS");
		createNewClient();
	}
	
	@Test
	public void getCurrentUser()
	{
		// create new user
		createNewUser();
		
		// delete user
		String deletedUser = deleteNewUser();
		
		// verify user was deleted
		if(!"".equals(deletedUser))
		{
			fail("The response for a deleted user should be an empty string. "
					+ "Instead we got " + deletedUser);
		}

		String getUser = client.getUser();
		if(!getUser.contains("There is no Player matching that email-password combination"))
		{
			fail("The user does not appear to be deleted: " + getUser);
		}
	}
}
