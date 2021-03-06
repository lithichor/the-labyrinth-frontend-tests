package test.tests.api.maps;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.labyrinth.client.GamesClient;
import com.labyrinth.client.MapsClient;

public class MapsGetTests extends MapsAPITests
{
	@BeforeTest
	public void setup()
	{
		super.startup();
		System.out.println("STARTING MAPS GET TESTS");
		mapsClient = new MapsClient(email, password1);
		gamesClient = new GamesClient(email, password1);
	}
	
	@Test
	public void getMapsForCurrentGame()
	{
		// create new game
		String game = gamesClient.createGame();
		int mapIdFromGame = getMapIdFromGame(game);
		int gameIdFromGame = getGameIdFromGame(game);
		
		//get maps for the game
		String map = mapsClient.getCurrentGameMaps();
		int mapId = getMapIdFromMap(map);
		int gameIdFromMap = getGameIdFromMap(map);
		
		// delete the game before the assertions, in case they fail
		gamesClient.deleteGame(gameIdFromGame);
		
		assertEquals(mapIdFromGame, mapId,
				"The Map IDs do not match:\nFrom Game object: " + mapIdFromGame + "\nFrom Map object: " + mapId);
		assertEquals(gameIdFromGame, gameIdFromMap,
				"The Game IDs do not match:\nFrom Game object: " + gameIdFromGame + "\nFrom Map object: " + gameIdFromMap);
	}
	
	@Test
	public void getMapsUsingGameId()
	{
		String game = gamesClient.createGame();
		int gameId = getGameIdFromGame(game);
		int mapId = getMapIdFromGame(game);
		
		String map = mapsClient.getMapsForGame(gameId);
		int gameIdFromMap = getGameIdFromMap(map);
		int mapIdFromMap = getMapIdFromMap(map);
		
		gamesClient.deleteGame(gameId);
		
		assertEquals(gameId, gameIdFromMap,
				"The Game IDs do not match:\nFrom Game Object: " + gameId + "\nFrom Map Object: " + gameIdFromMap);
		assertEquals(mapId, mapIdFromMap,
				"The Map IDs do not match:\nFrom Game Object: " + mapId + "\nFrom Map Object: " + mapIdFromMap);
	}
	
	@Test
	public void getMapsUsingMapId()
	{
		String game = gamesClient.createGame();
		int gameId = getGameIdFromGame(game);
		int mapId = getMapIdFromGame(game);
		
		String map = mapsClient.getMapsFromMapId(mapId);
		int gameIdFromMap = getGameIdFromMap(map);
		int mapIdFromMap = getMapIdFromMap(map);
		
		gamesClient.deleteGame(gameId);
		
		assertEquals(gameId, gameIdFromMap,
				"The Game IDs do not match:\nFrom Game Object: " + gameId + "\nFrom Map Object: " + gameIdFromMap);
		assertEquals(mapId, mapIdFromMap,
				"The Map IDs do not match:\nFrom Game Object: " + mapId + "\nFrom Map Object: " + mapIdFromMap);
	}
	
	@Test
	public void getMapsForDeletedGame()
	{
		// create a game
		String game = gamesClient.createGame();
		int gameId = getGameIdFromGame(game);
		// delete the game
		gamesClient.deleteGame(gameId);
		
		// get the maps for the game
		String maps = mapsClient.getMapsForGame(gameId);
		
		assertTrue(maps.contains("We could not find a Map for that Game ID"),
				"We should have gotten an error message, but instead got this:\n" +
				maps);
	}
	
	@Test
	public void getMapUsingBogusMapId()
	{
		// create a game
		String game = gamesClient.createGame();
		int gameId = getGameIdFromGame(game);
		int mapId = getMapIdFromGame(game);
		
		// try to get the map with bogus string id
		// it should return the map
		String map = mapsClient.getMapsForGame("this_is_bogus");
		int mapIdFromMap = getMapIdFromMap(map);
		int gameIdFromMap = getGameIdFromMap(map);
		
		// delete the game before the assertions, in case they fail
		gamesClient.deleteGame(gameId);
		
		assertEquals(gameId,
				gameIdFromMap,
				"The game IDs do not match\nGame: " + game + "\nMap: " + map);
		assertEquals(mapId,
				mapIdFromMap,
				"The map IDs do not match\nGame: " + game + "\nMap: " + map);
	}
	
	@Test
	public void getMapUsingInvalidId()
	{
		// create a game
		String game = gamesClient.createGame();
		int gameId = getGameIdFromGame(game);
		
		// try to get the map using various methods with
		// an invalid ID
		String map1 = mapsClient.getMapsForGame(10);
		String map2 = mapsClient.getMapsForGame("10");
		String map3 = mapsClient.getMapsFromMapId(10);
		String map4 = mapsClient.getMapsFromMapId("10");
		
		String message1 = "We could not find a Map for that Game ID";
		String message2 = "We did not find a Map with that ID";
		
		// delete the game before the assertions, in case they fail
		gamesClient.deleteGame(gameId);
		
		assertTrue(map1.contains(message1),
				"We should have gotten an error message for map1, but instead got this: " + map1);
		assertEquals(getMessageCount(map1), 1,
				"Looks like more than one error message in the response:\n" + map1);
		assertTrue(map2.contains(message1),
				"We should have gotten an error message for map2, but instead got this: " + map2);
		assertEquals(getMessageCount(map2), 1,
				"Looks like more than one error message in the response:\n" + map2);
		assertTrue(map3.contains(message2),
				"We should have gotten an error message for map3, but instead got this: " + map3);
		assertEquals(getMessageCount(map3), 1,
				"Looks like more than one error message in the response:\n" + map3);
		assertTrue(map4.contains(message2),
				"We should have gotten an error message for map4, but instead got this: " + map4);
		assertEquals(getMessageCount(map4), 1,
				"Looks like more than one error message in the response:\n" + map4);
	}
	
	@Test
	public void getMapWithInvalidUser()
	{
		String game = gamesClient.createGame();
		int gameId = getGameIdFromGame(game);
		int mapId = getMapIdFromGame(game);
		
		// this user doesn't exist
		MapsClient newMapsClient = new MapsClient("albert@brooks.corn", "2POIpoi");
		String map1 = newMapsClient.getMapsFromMapId(mapId);
		String map2 = newMapsClient.getMapsForGame(gameId);
		
		String message = "There is no Player matching that email-password combination";
		
		// delete the game before the assertions, in case they fail
		gamesClient.deleteGame(gameId);
		
		assertTrue(map1.contains(message),
				"We should have gotten " + message + ", but instead got this: " + map1);
		assertEquals(getMessageCount(map1), 1,
				"Looks like more than one error message in the response:\n" + map1);
		assertTrue(map2.contains(message),
				"We should have gotten " + message + ", but instead got this: " + map2);
		assertEquals(getMessageCount(map2), 1,
				"Looks like more than one error message in the response:\n" + map2);
	}
	
	@Test
	public void verifyCannotGetCrossTenantMap()
	{
		String game = gamesClient.createGame();
		int gameId = getGameIdFromGame(game);
		int mapId = getMapIdFromGame(game);
		
		String[] secondUser = createSecondUser();
		MapsClient secondMaps = new MapsClient(secondUser[0], secondUser[1]);
		String resp1 = secondMaps.getMapsFromMapId(mapId);
		String resp2 = secondMaps.getMapsForGame(gameId);
		
		String message1 = "We did not find a Map with that ID";
		String message2 = "We could not find a Map for that Game ID";
		
		gamesClient.deleteGame(gameId);
		
		assertTrue(resp1.contains(message1),
				"We should have gotten: " + message1 + ", but instead got this: " + resp1);
		assertEquals(getMessageCount(resp1), 1,
				"Looks like more than one error message in the response:\n" + resp1);
		assertTrue(resp2.contains(message2),
				"We should have gotten: " + message2 + ", but instead got this: " + resp2);
		assertEquals(getMessageCount(resp2), 1,
				"Looks like more than one error message in the response:\n" + resp2);
	}
}
