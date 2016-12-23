package test.tests.api.games;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.google.gson.JsonArray;
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
		System.out.println("STARTING GAMES GET TESTS");
		verifier = new GamesVerifier();
		client = new GamesClient(username, password);
	}
	
	@Test
	public void getAllGamesForUser()
	{
		String resp = client.getAllGames();
		assertTrue(verifier.verifyAllGames(resp), verifier.getErrorsAsString());
	}
	
	@Test
	public void getOneGameForUser()
	{
		// create game and get id
		String game = client.createGame();
		JsonObject gameObj = gson.fromJson(game, JsonObject.class);
		int gameId = gameObj.get("id").getAsInt();
		
		// get the game (redundant, but we want to test get)
		String resp = client.getOneGame(gameId);
		
		// verify the game
		assertTrue(verifier.verifyOneGame(resp), verifier.getErrorsAsString());
		
		// verify the game matches what we created
		assertTrue(verifier.compareGames(game, resp), verifier.getErrorsAsString());
		
		// delete the game
		client.deleteGame(gameId);
	}
	
	@Test
	public void getLastGameVsAllGames()
	{
		// get all games and extract last game
		String allGames = client.getAllGames();
		JsonArray allGamesArray = gson.fromJson(allGames, JsonArray.class);
		int lastGameIndex = allGamesArray.size() - 1;
		JsonObject lastGameObj = (JsonObject)allGamesArray.get(lastGameIndex);
		String lastGameFromAll = gson.toJson(lastGameObj);
		
		// get games/last
		String lastGame = client.getLastGame();
		
		//compare the two
		assertTrue(verifier.compareGames(lastGameFromAll, lastGame), verifier.getErrorsAsString());
		
		// get one game (same id)
		String oneGame = client.getOneGame(lastGameObj.get("id").getAsInt());
		
		//compare to games/last
		assertTrue(verifier.compareGames(oneGame, lastGame), verifier.getErrorsAsString());
	}
}
