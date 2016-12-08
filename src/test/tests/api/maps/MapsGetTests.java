package test.tests.api.maps;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.labyrinth.client.GamesClient;
import com.labyrinth.client.MapsClient;

import test.parents.LabyrinthAPITest;

public class MapsGetTests extends LabyrinthAPITest
{
	@BeforeTest
	public void setup()
	{
		System.out.println("STARTING MAPS GET TESTS");
		mapsClient = new MapsClient(email, password1);
		gamesClient = new GamesClient(email, password1);
	}
	
	@Test
	public void getMapsForCurrentGame()
	{
		// create new game
		String game = gamesClient.createGame();
		JsonObject gameObj = gson.fromJson(game, JsonObject.class);
		JsonArray mapsFromGame = gameObj.get("mapIds").getAsJsonArray();
		int mapIdFromGame = mapsFromGame.get(0).getAsInt();
		
		//get maps for the game
		String map = mapsClient.getCurrentGameMaps();
		JsonObject mapObj = gson.fromJson(map, JsonObject.class);
		int mapId = mapObj.get("id").getAsInt();
		
		Assert.assertEquals(mapIdFromGame, mapId,
				"The IDs do not match:\nFrom Game object: " + mapIdFromGame + "\nFrom Map object: " + mapId);
	}
	
	@Test
	public void getMapsForDeletedGame()
	{
		// create a game
		String game = gamesClient.createGame();
		JsonObject gameObj = gson.fromJson(game, JsonObject.class);
		int gameId = gameObj.get("id").getAsInt();
		// delete the game
		gamesClient.deleteGame(gameId);
		
		// get the maps for the game
		String maps = mapsClient.getMapsForGame(gameId);
		
		Assert.assertTrue(maps.contains("We could not find a Map for that Game ID"),
				"We should have gotten an error message, but instead got this:\n" +
				maps);
		
	}
}
