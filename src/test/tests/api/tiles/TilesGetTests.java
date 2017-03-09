package test.tests.api.tiles;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.labyrinth.client.ConstantsClient;
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
		super.startup();
		System.out.println("STARTING TILES GET TESTS");
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
		ConstantsClient constants = new ConstantsClient(email, password1);
		String constantsStr = constants.getConstants();
		JsonObject constantsObj = gson.fromJson(constantsStr, JsonObject.class);
		int sizeOfArray = constantsObj.get("GRID_SIZE").getAsInt();
		
		String game = gamesClient.createGame();
		JsonObject gameObj = gson.fromJson(game, JsonObject.class);
		int gameId = gameObj.get("id").getAsInt();
		int mapId = gameObj.get("mapIds").getAsJsonArray().get(0).getAsInt();
		
		String tiles = tilesClient.getTilesForMap(mapId);
		JsonArray tilesObj = gson.fromJson(tiles, JsonArray.class);
		
		assertEquals(tilesObj.size(), (int)Math.pow(sizeOfArray, 2), "The array of Tiles doesn't have the correct size");
		
		gamesClient.deleteGame(gameId);
	}
	
	@Test
	public void getSingleTileWithCrossTenantId()
	{
		String response = tilesClient.getTile(1);
		String message = "There are no tiles matching that ID";
		assertTrue(response.contains(message), "Did not get the error message we expected:"
				+ "\nResponse from Server: " + response
				+ "Expected Message: " + message + "\n**");
	}
	
	@Test
	public void getAllTilesForMapWithCrossTenantId()
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
	
	@Test
	public void getTileWithNoIdString()
	{
		String tile = tilesClient.getTile("");
		String message = "You have to provide an ID if you want to get the Tile";
		assertTrue(tile.contains(message), "Did not get the expected response:\n"
				+ "\nExpected: " + message
				+ "\nRecieved: " + tile + "\n");
	}
	
	@Test
	public void getTileWithNullIdString()
	{
		String str = null;
		String tile = tilesClient.getTile(str);
		String message = "You have to provide an ID if you want to get the Tile";
		assertTrue(tile.contains(message), "Did not get the expected response:\n"
				+ "\nExpected: " + message
				+ "\nRecieved: " + tile + "\n");
	}
	
	@Test
	public void getTileWithNoIdInteger()
	{
		String tile = tilesClient.getTile(0);
		String message = "You have to provide an ID if you want to get the Tile";
		assertTrue(tile.contains(message), "Did not get the expected response:\n"
				+ "\nExpected: " + message
				+ "\nRecieved: " + tile + "\n");
	}
	
	@Test
	public void getTileWithNullIdInteger()
	{
		Integer integer = null;
		String tile = tilesClient.getTile(integer);
		String message = "You have to provide an ID if you want to get the Tile";
		assertTrue(tile.contains(message), "Did not get the expected response:\n"
				+ "\nExpected: " + message
				+ "\nRecieved: " + tile + "\n");
	}
	
	@Test
	public void getTileWithInvalidIdString()
	{
		String tile = tilesClient.getTile("qweqwe");
		String message = "You have to provide an ID if you want to get the Tile";
		assertTrue(tile.contains(message), "Did not get the expected response:\n"
				+ "\nExpected: " + message
				+ "\nRecieved: " + tile + "\n");
	}
	
	@Test
	public void getTileWithInvalidIdInteger()
	{
		String tile = tilesClient.getTile(-123);
		String message = "You have to provide an ID if you want to get the Tile";
		assertTrue(tile.contains(message), "Did not get the expected response:\n"
				+ "\nExpected: " + message
				+ "\nRecieved: " + tile + "\n");
	}
	
	@Test
	public void getTilesForMapWithNoIdString()
	{
		String tile = tilesClient.getTilesForMap("");
		String message = "A Map ID is required for this endpoint";
		assertTrue(tile.contains(message), "Did not get the expected response:\n"
				+ "\nExpected: " + message
				+ "\nRecieved: " + tile + "\n");
	}
	
	@Test
	public void getTilesForMapWithNullIdString()
	{
		String str = null;
		String tile = tilesClient.getTilesForMap(str);
		String message = "A Map ID is required for this endpoint";
		assertTrue(tile.contains(message), "Did not get the expected response:\n"
				+ "\nExpected: " + message
				+ "\nRecieved: " + tile + "\n");
	}
	
	@Test
	public void getTilesForMapWithNoIdInteger()
	{
		String tile = tilesClient.getTilesForMap(0);
		String message = "A Map ID is required for this endpoint";
		assertTrue(tile.contains(message), "Did not get the expected response:\n"
				+ "\nExpected: " + message
				+ "\nRecieved: " + tile + "\n");
	}
	
	@Test
	public void getTilesForMapWithNullIdInteger()
	{
		Integer integer = null;
		String tile = tilesClient.getTilesForMap(integer);
		String message = "A Map ID is required for this endpoint";
		assertTrue(tile.contains(message), "Did not get the expected response:\n"
				+ "\nExpected: " + message
				+ "\nRecieved: " + tile + "\n");
	}
	
	@Test
	public void getTilesForMapWithInvalidIdString()
	{
		String tile = tilesClient.getTilesForMap("qweqwe");
		String message = "A Map ID is required for this endpoint";
		assertTrue(tile.contains(message), "Did not get the expected response:\n"
				+ "\nExpected: " + message
				+ "\nRecieved: " + tile + "\n");
	}
	
	@Test
	public void getTilesForMapWithInvalidIdInteger()
	{
		String tile = tilesClient.getTilesForMap(-123);
		String message = "A Map ID is required for this endpoint";
		assertTrue(tile.contains(message), "Did not get the expected response:\n"
				+ "\nExpected: " + message
				+ "\nRecieved: " + tile + "\n");
	}
}
