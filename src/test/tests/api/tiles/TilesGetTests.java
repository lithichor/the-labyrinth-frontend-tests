package test.tests.api.tiles;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.labyrinth.client.GamesClient;
import com.labyrinth.client.MapsClient;
import com.labyrinth.client.TilesClient;
import com.labyrinth.client.UserClient;

import test.parents.LabyrinthAPITest;

public class TilesGetTests extends LabyrinthAPITest
{
	@BeforeTest
	public void setup()
	{
		System.out.println("STARTING MAPS GET TESTS");
		userClient = new UserClient(email, password1);
		gamesClient = new GamesClient(email, password1);
		mapsClient = new MapsClient(email, password1);
		tilesClient = new TilesClient(email, password1);
	}
	
	@Test
	public void getSingleTile()
	{
		String game = gamesClient.createGame();
		JsonObject gameObj = gson.fromJson(game, JsonObject.class);
		int gameId = gameObj.get("id").getAsInt();
		int mapId = gameObj.get("mapIds").getAsJsonArray().get(0).getAsInt();
		
		String tiles = tilesClient.getTilesForMap(mapId);
		JsonArray tilesObj = gson.fromJson(tiles, JsonArray.class);
		int tItem = rand.nextInt(tilesObj.size() - 1);
		
		JsonObject tileObjFromArray = tilesObj.get(tItem).getAsJsonObject();
		int tileIdFromArray = tileObjFromArray.get("id").getAsInt();
		
		String tile = tilesClient.getTile(tileIdFromArray);
		JsonObject tileObj = gson.fromJson(tile, JsonObject.class);
		int tileId = tileObj.get("id").getAsInt();
		
		assertEquals(tileIdFromArray, tileId, "The IDs do not match:"
				+ "\nFrom Array: " + tileIdFromArray
				+ "\nFrom GET: " + tileId + "\n**");
		
		gamesClient.deleteGame(gameId);
	}
	
	@Test
	public void getAllTilesForMap()
	{
		int sizeOfArray = 100;
		
		String game = gamesClient.createGame();
		JsonObject gameObj = gson.fromJson(game, JsonObject.class);
		int gameId = gameObj.get("id").getAsInt();
		int mapId = gameObj.get("mapIds").getAsJsonArray().get(0).getAsInt();
		
		String tiles = tilesClient.getTilesForMap(mapId);
		JsonArray tilesObj = gson.fromJson(tiles, JsonArray.class);
		
		assertEquals(sizeOfArray, tilesObj.size(), "The array of Tiles doesn't have the correct size");
		
		gamesClient.deleteGame(gameId);
	}
	
	@Test
	public void getSingleTileWithInvalidId()
	{
		String response = tilesClient.getTile(1);
		String message = "There are no tiles matching that ID";
		assertTrue(response.contains(message), "Did not get the error message we expected:"
				+ "\nResponse from Server: " + response
				+ "Expected Message: " + message + "\n**");
	}
	
	@Test
	public void getAllTilesForMapWithInvalidId()
	{
		String expected = "There is not a tile for the map ID you gave me";
		String response = tilesClient.getTilesForMap(1);
		
		assertTrue(response.contains(expected), "The response doesn't look like what we expected"
				+ "\nEXPECTED: " + expected
				+ "\nRESPONSE: " + response);
	}
	
	@Test
	public void getTileWithInvalidUser()
	{
		TilesClient newTilesClient = new TilesClient("invalid@user.corn", "invalid password");
		String response = newTilesClient.getTile("1024");
		String message = "There is no Player matching that email-password combination";
		assertTrue(response.contains(message), "Did not get the error message we expected:"
				+ "\nResponse from Server: " + response
				+ "Expected Message: " + message + "\n**");
	}
	
	@Test
	public void getAllTilesForMapWithInvalidUser()
	{
		TilesClient newTilesClient = new TilesClient("invalid@user.corn", "invalid password");
		String response = newTilesClient.getTile("1024");
		String message = "There is no Player matching that email-password combination";
		assertTrue(response.contains(message), "Did not get the error message we expected:"
				+ "\nResponse from Server: " + response
				+ "Expected Message: " + message + "\n**");
	}
}
