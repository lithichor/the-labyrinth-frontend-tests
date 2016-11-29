package test.tests.api.games;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.labyrinth.client.GamesClient;

import test.parents.LabyrinthAPITest;

public class GamesPutTests extends LabyrinthAPITest
{
	@BeforeTest
	public void startUp()
	{
		System.out.println("STARTING GAMES PUT TESTS");
		gamesClient = new GamesClient(email, password1);
	}
	
	@Test
	public void gamesPutNotAllowed()
	{
		String response = gamesClient.makeArbitraryAPICall("games", "put");
		if(!response.contains("PUT not supported for this endpont"))
		{
			fail("The PUT method should not be allowed for the games endpoint:\nRESPONSE: " + response);
		}
	}
}
