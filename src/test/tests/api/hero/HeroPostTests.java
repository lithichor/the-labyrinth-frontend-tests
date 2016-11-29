package test.tests.api.hero;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.labyrinth.client.HerosClient;

import test.parents.LabyrinthAPITest;

public class HeroPostTests extends LabyrinthAPITest
{
	@BeforeTest
	public void setup()
	{
		System.out.println("STARTING HEROS POST TESTS");
		herosClient = new HerosClient(email, password1);
	}
	
	@Test
	public void verifyPostNotPermitted()
	{
		String response = herosClient.makeArbitraryAPICall("heros", "post");
		if(!response.contains("POST not supported for this endpont"))
		{
			fail("The POST method should not be allowed, but we didn't get the correct response:\n\t" + response);
		}
	}
}
