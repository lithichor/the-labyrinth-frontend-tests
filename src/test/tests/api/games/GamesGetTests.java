package test.tests.api.games;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.labyrinth.client.GamesClient;

import test.parents.LabyrinthAPITest;

public class GamesGetTests extends LabyrinthAPITest
{
	private GamesVerifier verifier;
	
	@BeforeTest
	public void setup()
	{
		super.startup();
		System.out.println("STARTING GAMES GET TESTS");
		verifier = new GamesVerifier();
		gamesClient = new GamesClient(email, password1);
	}
	
	@Test
	public void getAllGamesForUser()
	{
		// create random number of games
		int numberGames = rand.nextInt(4) + 1;
		
		// array to hold the game ids
		Integer[] ids = new Integer[numberGames];
		
		for(int x = 0; x < numberGames; x++)
		{
			String game = gamesClient.createGame();
			ids[x] = ((JsonObject)gson.fromJson(game, JsonObject.class)).get("id").getAsInt();
		}
		String resp = gamesClient.getAllGames();
		
		// delete the games before verifying
		for(int x = 0; x < numberGames; x++)
		{
			gamesClient.deleteGame(ids[x]);
		}
		assertTrue(verifier.verifyAllGames(resp), verifier.getErrorsAsString());
	}

	@Test
	public void getOneGameForUser()
	{
		// create game and get id
		String game = gamesClient.createGame();
		JsonObject gameObj = gson.fromJson(game, JsonObject.class);
		int gameId = gameObj.get("id").getAsInt();
		
		// get the game (redundant, but we want to test get)
		String resp = gamesClient.getOneGame(gameId);
		
		// verify the game
		assertTrue(verifier.verifyOneGame(resp), verifier.getErrorsAsString());
		
		// verify the game matches what we created
		assertTrue(verifier.compareGames(game, resp), verifier.getErrorsAsString());
		
		// delete the game
		gamesClient.deleteGame(gameId);
	}
	
	@Test
	public void getLastGameVsAllGames()
	{
		// create random number of games
		int numberGames = rand.nextInt(3) + 2;
		
		// array to hold the game ids
		Integer[] ids = new Integer[numberGames];
		
		for(int x = 0; x < numberGames; x++)
		{
			String game = gamesClient.createGame();
			ids[x] = ((JsonObject)gson.fromJson(game, JsonObject.class)).get("id").getAsInt();
		}

		// get all games and extract last game
		String allGames = gamesClient.getAllGames();
		JsonArray allGamesArray = gson.fromJson(allGames, JsonArray.class);
		int lastGameIndex = allGamesArray.size() - 1;
		JsonObject lastGameObj = (JsonObject)allGamesArray.get(lastGameIndex);
		String lastGameFromAll = gson.toJson(lastGameObj);
		
		// get games/last
		String lastGame = gamesClient.getLastGame();
		
		// get one game (same id)
		String oneGame = gamesClient.getOneGame(lastGameObj.get("id").getAsInt());
		
		// delete the games before verifying
		for(int x = 0; x < numberGames; x++)
		{
			gamesClient.deleteGame(ids[x]);
		}

		//compare the two
		assertTrue(verifier.compareGames(lastGameFromAll, lastGame), verifier.getErrorsAsString());
		
		//compare to games/last
		assertTrue(verifier.compareGames(oneGame, lastGame), verifier.getErrorsAsString());
	}
	
	@Test
	public void getGameWithNoIdString()
	{
//		int gameId = gson.fromJson(gamesClient.createGame(), JsonObject.class).get("id").getAsInt();
		
		String game = gamesClient.getOneGame("");
		String message = "This Player has no active Games";
		
//		gamesClient.deleteGame(gameId);
		assertTrue(game.contains(message), "Did not get the expected response:\n"
				+ "\nExpected: " + message
				+ "\nRecieved: " + game + "\n");
	}
	
	@Test
	public void getGameWithNullIdString()
	{
		String str = null;
		String game = gamesClient.getOneGame(str);
		String message = "That is not what I would call a valid Game ID";
		assertTrue(game.contains(message), "Did not get the expected response:\n"
				+ "\nExpected: " + message
				+ "\nRecieved: " + game + "\n");
	}
	
	@Test
	public void getGameWithNoIdInteger()
	{
		String game = gamesClient.getOneGame(0);
		String message = "That is not what I would call a valid Game ID";
		assertTrue(game.contains(message), "Did not get the expected response:\n"
				+ "\nExpected: " + message
				+ "\nRecieved: " + game + "\n");
	}
	
	@Test
	public void getGameWithNullIdInteger()
	{
		Integer integer = null;
		String game = gamesClient.getOneGame(integer);
		String message = "That is not what I would call a valid Game ID";
		assertTrue(game.contains(message), "Did not get the expected response:\n"
				+ "\nExpected: " + message
				+ "\nRecieved: " + game + "\n");
	}
	
	@Test
	public void getGameWithInvalidIdString()
	{
		String game = gamesClient.getOneGame("qweqwe");
		String message = "That is not what I would call a valid Game ID";
		assertTrue(game.contains(message), "Did not get the expected response:\n"
				+ "\nExpected: " + message
				+ "\nRecieved: " + game + "\n");
	}
	
	@Test
	public void getGameWithInvalidIdInteger()
	{
		String game = gamesClient.getOneGame(-123);
		String message = "That is not what I would call a valid Game ID";
		assertTrue(game.contains(message), "Did not get the expected response:\n"
				+ "\nExpected: " + message
				+ "\nRecieved: " + game + "\n");
	}
}
