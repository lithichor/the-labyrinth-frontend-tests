package test.tests.api.games;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.labyrinth.client.GamesClient;
import com.labyrinth.client.MapsClient;

import test.parents.LabyrinthAPITest;

/**
 * This test should be run for general regression purposes.
 * 
 * @author spiralgyre
 *
 */
public class GamesDeleteTests extends LabyrinthAPITest
{
	@BeforeTest
	public void setup()
	{
		super.startup();
		System.out.println("STARTING GAMES DELETE TESTS");
		gamesClient = new GamesClient(email, password1);
		mapsClient = new MapsClient(email, password1);
	}

	@Test
	public void deleteGame()
	{
		// create a new game (post) and get the id from the response
		String resp = gamesClient.createGame();
		JsonObject game = gson.fromJson(resp, JsonObject.class);
		int id = (game.get("id")).getAsInt();

		// delete the game just created
		resp = gamesClient.deleteGame(id);
		// this should be an empty string
		assertTrue(resp.equalsIgnoreCase(""),
				"There was an error deleting the game: " + resp);
		
		// get the game just deleted; we should receive an error
		resp = gamesClient.getOneGame(id);
		assertTrue(resp.contains("This Player does not have an active Game with that ID"),
				"The game does not seem to be deleted");
	}
	
	@Test
	public void verifyNoErrorWhenNoMap()
	{
		// create game
		String game = gamesClient.createGame();
		JsonObject gameObj = gson.fromJson(game, JsonObject.class);
		JsonArray maps = gameObj.get("mapIds").getAsJsonArray();
		int mapId = maps.get(0).getAsInt();
		int gameId = gameObj.get("id").getAsInt();
		
		// delete map
		mapsClient.deleteMap(mapId);
		
		//delete game
		String resp = gamesClient.deleteGame(gameId);
		assertTrue(resp.equalsIgnoreCase(""),
				"There was an error deleting the game: " + resp);
	}
}
