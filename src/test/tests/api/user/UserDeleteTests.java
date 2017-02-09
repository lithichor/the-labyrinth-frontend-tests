package test.tests.api.user;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class UserDeleteTests extends UserAPITest
{
	@BeforeTest
	public void setup()
	{
		System.out.println("STARTING USER DELETE TESTS");
		createNewUserClient();
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

		String getUser = userClient.getUser();
		assertTrue(getUser.contains("There is no Player matching that email-password combination"),
				"The user does not appear to be deleted: " + getUser);
	}
}
