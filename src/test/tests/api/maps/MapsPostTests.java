package test.tests.api.maps;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.labyrinth.client.GamesClient;
import com.labyrinth.client.MapsClient;

import test.helpers.MapsVerifier;

public class MapsPostTests extends MapsAPITests
{
	@BeforeTest
	public void startup()
	{
		super.startup();
		System.out.println("STARTING MAPS POST TESTS");
		mapsClient = new MapsClient(email, password1);
		gamesClient = new GamesClient(email, password1);
	}
	
	@Test
	public void newMapCreation()
	{
		// create the game and get its ID
		String game = gamesClient.createGame();
		int gameId = getGameIdFromGame(game);
		
		//create a new map for the game
		String data = "{gameId: " + gameId + "}";
		String map = mapsClient.makeNewMapForGame(data);
		int gameIdFromMap = getGameIdFromMap(map);
		
		// delete the game
		gamesClient.deleteGame(gameId);
		
		// verify the new map is well-formed and references the game
		MapsVerifier verifier = new MapsVerifier();
		assertTrue(verifier.verifyMap(map), "The map doesn't seem to have the correct values");
		assertEquals(gameId, gameIdFromMap, "The game ID from the game doesn't match the game ID from the map");
	}
	
	@Test
	public void newMapWithHashesOrArraysForInts()
	{
		String data = "{gameId: {q: q}}";
		String response = mapsClient.makeNewMapForGame(data);
		String message = "The gameId needs to be an integer, not whatever it was you gave me";
		
		assertTrue(response.contains(message),
				"The response should have contained an error message, not this: " + response);
		assertEquals(getMessageCount(response), 1,
				"Looks like more than one error message in the response:\n" + response);
		
		data = "{gameId: {}}";
		response = mapsClient.makeNewMapForGame(data);
		assertTrue(response.contains(message),
				"The response should have contained an error message, not this: " + response);
		assertEquals(getMessageCount(response), 1,
				"Looks like more than one error message in the response:\n" + response);
		
		data = "{gameId: [1, 2]}";
		response = mapsClient.makeNewMapForGame(data);
		assertTrue(response.contains(message),
				"The response should have contained an error message, not this: " + response);
		assertEquals(getMessageCount(response), 1,
				"Looks like more than one error message in the response:\n" + response);
		
		data = "{gameId: []}";
		response = mapsClient.makeNewMapForGame(data);
		assertTrue(response.contains(message),
				"The response should have contained an error message, not this: " + response);
		assertEquals(getMessageCount(response), 1,
				"Looks like more than one error message in the response:\n" + response);
	}
	
	@Test
	public void newMapUsingInvalidUser()
	{
		String game = gamesClient.createGame();
		int gameId = getGameIdFromGame(game);
		
		MapsClient newMapsClient = new MapsClient("albert@brooks.corn", "2POIpoi");
		String data = "{gameId: " + gameId + "}";
		String map = newMapsClient.makeNewMapForGame(data);
		
		String message = "There is no Player matching that email-password combination";
		
		gamesClient.deleteGame(gameId);
		assertTrue(map.contains(message),
				"The response should have contained an error message, not this: " + map);
		assertEquals(getMessageCount(map), 1,
				"Looks like more than one error message in the response:\n" + map);
	}
}
