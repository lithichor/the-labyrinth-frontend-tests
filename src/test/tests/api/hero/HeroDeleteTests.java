package test.tests.api.hero;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.labyrinth.client.HerosClient;

import test.parents.LabyrinthAPITest;

public class HeroDeleteTests extends LabyrinthAPITest
{
	@BeforeTest
	public void setup()
	{
		System.out.println("STARTING HEROS DELETE TESTS");
		herosClient = new HerosClient(email, password1);
	}
	
	@Test
	public void verifyDeleteNotPermitted()
	{
		String response = herosClient.makeArbitraryAPICall("heros", "delete");
		assertTrue(response.contains("DELETE not supported for this endpont"),
				"The DELETE method should not be allowed, but we didn't get the correct response:\n\t" + response);
	}
}
