package test.tests.api.monsters;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.labyrinth.client.MonstersClient;

import test.parents.LabyrinthAPITest;

public class MonstersPostTests extends LabyrinthAPITest
{
	@BeforeTest
	public void setup()
	{
		System.out.println("STARTING MONSTERS POST TESTS");
		monstersClient = new MonstersClient(email, password1);
	}
	
	@Test
	public void verifyPostNotPermitted()
	{
		String response = monstersClient.makeArbitraryAPICall("monsters", "post");
		assertTrue(response.contains("POST not supported for this endpont"),
				"The POST method should not be allowed, but we didn't get the correct response:\n\t" + response);
	}
}
