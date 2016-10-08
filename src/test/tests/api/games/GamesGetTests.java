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
}
