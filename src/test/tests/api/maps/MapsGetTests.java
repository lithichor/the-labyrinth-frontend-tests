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
		int gameIdFromGame = gameObj.get("id").getAsInt();
		
		//get maps for the game
		String map = mapsClient.getCurrentGameMaps();
		JsonObject mapObj = gson.fromJson(map, JsonObject.class);
		int mapId = mapObj.get("id").getAsInt();
		int gameIdFromMap = mapObj.get("gameId").getAsInt();
		
		// delete the game before the assertions, in case they fail
		gamesClient.deleteGame(gameIdFromGame);
		
		Assert.assertEquals(mapIdFromGame, mapId,
				"The IDs do not match:\nFrom Game object: " + mapIdFromGame + "\nFrom Map object: " + mapId);
		Assert.assertEquals(gameIdFromGame, gameIdFromMap,
				"The IDs do not match:\nFrom Game object: " + gameIdFromGame + "\nFrom Map object: " + gameIdFromMap);
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
	
	@Test
	public void getMapUsingBogusMapId()
	{
		// create a game
		String game = gamesClient.createGame();
		JsonObject gameObj = gson.fromJson(game, JsonObject.class);
		
		// try to get the map with bogus string id
		// it should return the map
		String map = mapsClient.getMapsForGame("this_is_bogus");
		JsonObject mapObj = gson.fromJson(map, JsonObject.class);
		
		// delete the game before the assertions, in case they fail
		gamesClient.deleteGame(gameObj.get("id").getAsInt());
		
		Assert.assertEquals(gameObj.get("id").getAsInt(),
				mapObj.get("gameId").getAsInt(),
				"The game IDs do not match\nGame: " + game + "\nMap: " + map);
		Assert.assertEquals(gameObj.get("mapIds").getAsJsonArray().get(0).getAsInt(),
				mapObj.get("id").getAsInt(),
				"The map IDs do not match\nGame: " + game + "\nMap: " + map);
	}
	
	@Test
	public void getMapUsingInvalidId()
	{
		// create a game
		String game = gamesClient.createGame();
		JsonObject gameObj = gson.fromJson(game, JsonObject.class);
		
		// try to get the map using various methods with
		// and invalid ID
		String map1 = mapsClient.getMapsForGame(10);
		String map2 = mapsClient.getMapsForGame("10");
		String map3 = mapsClient.getMapsFromMapId(10);
		String map4 = mapsClient.getMapsFromMapId("10");
		
		String message = "We could not find a Map for that Game ID";
		
		// delete the game before the assertions, in case they fail
		gamesClient.deleteGame(gameObj.get("id").getAsInt());
		
		Assert.assertTrue(map1.contains(message),
				"We should have gotten an error message, but instead got this: " + map1);
		Assert.assertTrue(map2.contains(message),
				"We should have gotten an error message, but instead got this: " + map2);
		Assert.assertTrue(map3.contains(message),
				"We should have gotten an error message, but instead got this: " + map3);
		Assert.assertTrue(map4.contains(message),
				"We should have gotten an error message, but instead got this: " + map4);
	}
	
	@Test
	public void getMapWithInvalidUser()
	{
		String game = gamesClient.createGame();
		JsonObject gameObj = gson.fromJson(game, JsonObject.class);
		int gameId = gameObj.get("id").getAsInt();
		int mapId = gameObj.get("mapIds").getAsJsonArray().get(0).getAsInt();
		
		MapsClient newMapsClient = new MapsClient("albert@brooks.corn", "2POIpoi");
		String map1 = newMapsClient.getMapsFromMapId(mapId);
		String map2 = newMapsClient.getMapsForGame(gameId);
		
		String message = "There is no Player matching that email-password combination";
		String otherMessage = "Something went wrong with your request";
		
		// delete the game before the assertions, in case they fail
		gamesClient.deleteGame(gameObj.get("id").getAsInt());
		
		Assert.assertTrue(map1.contains(message),
				"We should have gotten " + message + ", but instead got this: " + map1);
		Assert.assertTrue(map2.contains(otherMessage),
				"We should have gotten " + otherMessage + ", but instead got this: " + map2);
	}
}
