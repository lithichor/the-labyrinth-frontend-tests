package test.tests.api.user;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class UserOptionsTests extends UserAPITest
{
	@BeforeTest
	public void setup()
	{
		super.startup();
	}
	
	@Test
	public void verifyUserOptions()
	{
		String options = userClient.getOptions();
		assertTrue(verifier.verifyUserOptions(options), verifier.getErrorsAsString());
	}
}
