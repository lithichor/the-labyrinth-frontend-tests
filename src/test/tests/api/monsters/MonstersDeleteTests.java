package test.tests.api.monsters;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.labyrinth.client.MonstersClient;

import test.parents.LabyrinthAPITest;

public class MonstersDeleteTests extends LabyrinthAPITest
{
	@BeforeTest
	public void setup()
	{
		System.out.println("STARTING MONSTERS DELETE TESTS");
		monstersClient = new MonstersClient(email, password1);
	}
	
	@Test
	public void verifyDeleteNotPermitted()
	{
		String response = monstersClient.makeArbitraryAPICall("monsters", "delete");
		assertTrue(response.contains("DELETE not supported for this endpont"),
				"The DELETE method should not be allowed, but we didn't get the correct response:\n\t" + response);
	}
}
