package test.tests.api.monsters;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.labyrinth.client.MonstersClient;

import test.parents.LabyrinthAPITest;

public class MonstersPutTests extends LabyrinthAPITest
{
	@BeforeTest
	public void setup()
	{
		System.out.println("STARTING MONSTERS PUT TESTS");
		monstersClient = new MonstersClient(email, password1);
	}
	
	@Test
	public void verifyDeleteNotPermitted()
	{
		String response = monstersClient.makeArbitraryAPICall("monsters", "put");
		assertTrue(response.contains("PUT not supported for this endpont"),
				"The PUT method should not be allowed, but we didn't get the correct response:\n\t" + response);
	}
}
