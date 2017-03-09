package test.tests.api.games;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.labyrinth.client.GamesClient;

import test.parents.LabyrinthAPITest;

public class GamesOptionsTests extends LabyrinthAPITest
{
	@BeforeTest
	public void setup()
	{
		gamesClient = new GamesClient(email, password1);
	}

	@Test
	public void verifyMapsOptions()
	{
		GamesVerifier verifier = new GamesVerifier();
		String options = gamesClient.getGameOptions();
		assertTrue(verifier.verifyGamesOptions(options), verifier.getErrorsAsString());
	}
}