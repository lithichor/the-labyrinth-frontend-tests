package test.tests.api.constants;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.labyrinth.client.ConstantsClient;
import com.labyrinth.client.UserClient;

import test.parents.LabyrinthAPITest;

public class ConstantsGetTests extends LabyrinthAPITest
{
	@BeforeTest
	public void setup()
	{
		super.startup();
		System.out.println("STARTING CONSTANTS GET TESTS");
		userClient = new UserClient(email, password1);
		constantsClient = new ConstantsClient(email, password1);
	}
	
	@Test
	public void getConstants()
	{
		ConstantsVerifier verifier = new ConstantsVerifier();
		String constants = constantsClient.getConstants();
		
		assertTrue(verifier.verifyConstants(constants), verifier.getErrorsAsString());
	}
}
