package test.tests.api.maps;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.google.gson.JsonObject;
import com.labyrinth.client.GamesClient;
import com.labyrinth.client.MapsClient;

import test.helpers.MapsVerifier;
import test.parents.LabyrinthAPITest;

public class MapsPostTests extends LabyrinthAPITest
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
		JsonObject gameObj = gson.fromJson(game, JsonObject.class);
		int gameId = gameObj.get("id").getAsInt();
		
		//create a new map for the game
		String data = "{gameId: " + gameId + "}";
		String map = mapsClient.makeNewMapForGame(data);
		JsonObject mapObj = gson.fromJson(map, JsonObject.class);
		int gameIdFromMap = mapObj.get("gameId").getAsInt();
		
		// delete the game
		gamesClient.deleteGame(gameId);
		
		// verify the new map is well-formed and references the game
		MapsVerifier verifier = new MapsVerifier();
		Assert.assertTrue(verifier.verifyMap(map), "The map doesn't seem to have the correct values");
		Assert.assertEquals(gameId, gameIdFromMap, "The game ID from the game doesn't match the game ID from the map");
	}
	
	@Test
	public void newMapWithHashesOrArraysForInts()
	{
		String data = "{gameId: {q: q}}";
		String response = mapsClient.makeNewMapForGame(data);
		Assert.assertTrue(response.contains("The gameId needs to be an integer, not whatever it was you gave me"),
				"The response should have contained an error message, not this: " + response);
		
		data = "{gameId: {}}";
		response = mapsClient.makeNewMapForGame(data);
		Assert.assertTrue(response.contains("The gameId needs to be an integer, not whatever it was you gave me"),
				"The response should have contained an error message, not this: " + response);
		
		data = "{gameId: [1, 2]}";
		response = mapsClient.makeNewMapForGame(data);
		Assert.assertTrue(response.contains("The gameId needs to be an integer, not whatever it was you gave me"),
				"The response should have contained an error message, not this: " + response);
		
		data = "{gameId: []}";
		response = mapsClient.makeNewMapForGame(data);
		Assert.assertTrue(response.contains("The gameId needs to be an integer, not whatever it was you gave me"),
				"The response should have contained an error message, not this: " + response);
	}
	
	@Test
	public void newMapUsingInvalidUser()
	{
		String game = gamesClient.createGame();
		JsonObject gamesObj = gson.fromJson(game, JsonObject.class);
		int gameId = gamesObj.get("id").getAsInt();
		
		MapsClient newMapsClient = new MapsClient("albert@brooks.corn", "2POIpoi");
		String data = "{gameId: " + gameId + "}";
		String map = newMapsClient.makeNewMapForGame(data);
		
		String message = "There is no Player matching that email-password combination";
		
		Assert.assertTrue(map.contains(message));
	}
}
