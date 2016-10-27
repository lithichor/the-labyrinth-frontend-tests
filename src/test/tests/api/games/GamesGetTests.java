package test.tests.api.games;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.labyrinth.client.GamesClient;

import test.helpers.GamesVerifier;
import test.parents.LabyrinthAPITest;

public class GamesGetTests extends LabyrinthAPITest
{
	private GamesVerifier verifier;
	private GamesClient client;
	
	@BeforeTest
	public void setup()
	{
		System.out.println("STARTNG GAMES GET TESTS");
		verifier = new GamesVerifier();
		client = new GamesClient(username, password);
	}
	
	@Test
	public void getAllGamesForUser()
	{
		String resp = client.getAllGames();
		JsonArray games = gson.fromJson(resp, JsonArray.class);
		
		if(!verifier.verifyAllGames(games))
		{
			fail(verifier.getErrors());
			
			for(JsonElement j: games)
			{
				System.out.println(j.toString());
			}
		}
	}
	
	@Test
	public void getOneGameForUser()
	{
		String resp = client.getOneGame(19);
		JsonObject game = gson.fromJson(resp, JsonArray.class).get(0).getAsJsonObject();
		
		if(!verifier.verifyOneGame(game))
		{
			fail(verifier.getErrors());
		}
	}
	
	@Test
	public void getLastGameForUser()
	{
		// getting the two responses is actually
		// testing the API client, not the API
		String lastGame = client.getOneGame("last");
		String lastGameRedux = client.getLastGame();
		JsonObject lastGameJson = gson.fromJson(lastGame, JsonElement.class);
		JsonObject lastGameJsonRedux = gson.fromJson(lastGameRedux, JsonElement.class);
		
		if(!verifier.verifyOneGame(lastGameJson))
		{
			fail("Failure while verifying last game from String", verifier.getErrors());
		}
		if(!verifier.verifyOneGame(lastGameJsonRedux))
		{
			fail("Failure while verifying last game from getLastGame method", verifier.getErrors());
		}
	}
}
