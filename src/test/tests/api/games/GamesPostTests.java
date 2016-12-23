package test.tests.api.games;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.google.gson.JsonObject;
import com.labyrinth.client.GamesClient;

import test.helpers.GamesVerifier;
import test.parents.LabyrinthAPITest;

public class GamesPostTests extends LabyrinthAPITest
{
	private GamesVerifier verifier;
	private GamesClient client;
	
	@BeforeTest
	public void setup()
	{
		System.out.println("STARTING GAMES POST TESTS");
		verifier = new GamesVerifier();
		client = new GamesClient("eric@eric.corn", "1qweqwe");
	}
	
	@Test
	public void startNewGame()
	{
		String resp = client.createGame();
		JsonObject game = gson.fromJson(resp, JsonObject.class);
		int gameId = game.get("id").getAsInt();
		
		assertTrue(verifier.verifyOneGame(resp), verifier.getErrorsAsString());
		client.deleteGame(gameId);
	}
}
