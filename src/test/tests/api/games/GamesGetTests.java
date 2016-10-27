package test.tests.api.games;

import java.util.ArrayList;

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
	public void getLastGameVsAllGames()
	{
		String allGames = client.getAllGames();
		JsonArray allGamesJson = gson.fromJson(allGames, JsonArray.class);
		JsonObject lastGameFromAll = (JsonObject)allGamesJson.get(allGamesJson.size() - 1);
		JsonArray fromAll = (JsonArray) lastGameFromAll.get("mapIds");
		
		String lastGame = client.getOneGame("last");
		JsonObject lastGameJson = gson.fromJson(lastGame, JsonElement.class);
		JsonArray fromLast = (JsonArray)lastGameJson.get("mapIds");
		
		if(!verifier.compareGamesArrays(fromAll, fromLast))
		{
			fail(verifier.getErrors());
		}
	}
	
	@Test
	public void getLastGameVsOneGame()
	{
		String allGames = client.getOneGame(19);
		JsonArray allGamesJson = gson.fromJson(allGames, JsonArray.class);
		JsonObject lastGameFromAll = (JsonObject)allGamesJson.get(0);
		JsonArray fromAll = (JsonArray) lastGameFromAll.get("mapIds");
		
		String lastGame = client.getLastGame();
		JsonObject lastGameJson = gson.fromJson(lastGame, JsonElement.class);
		JsonArray fromLast = (JsonArray)lastGameJson.get("mapIds");
		
		if(!verifier.compareGamesArrays(fromAll, fromLast))
		{
			fail(verifier.getErrors());
		}
	}
}
