package test.tests.api.tiles;

import org.testng.Assert;
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
		
		String tile = tilesClient.getTiles(tileIdFromArray);
		JsonObject tileObj = gson.fromJson(tile, JsonObject.class);
		int tileId = tileObj.get("id").getAsInt();
		
		Assert.assertEquals(tileIdFromArray, tileId, "The IDs do not match:"
				+ "\nFrom Array: " + tileIdFromArray
				+ "\nFrom GET: " + tileId + "\n**");
		
		gamesClient.deleteGame(gameId);
	}
	
	@Test
	public void getSingleTileWithInvalidId()
	{
		String response = tilesClient.getTiles(1);
		String message = "There is not a tile for the map ID you gave me";
		Assert.assertTrue(response.contains(message), "Did not get the error message we expected:"
				+ "\nResponse from Server: " + response
				+ "Expected Message: " + message + "\n**");
	}
	
	@Test
	public void getTileWithInvalidUser()
	{
		TilesClient newTilesClient = new TilesClient("invalid@user.corn", "invalid password");
		String response = newTilesClient.getTiles("1024");
		String message = "There is no Player matching that email-password combination";
		Assert.assertTrue(response.contains(message), "Did not get the error message we expected:"
				+ "\nResponse from Server: " + response
				+ "Expected Message: " + message + "\n**");
	}
}
