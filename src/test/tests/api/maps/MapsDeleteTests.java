package test.tests.api.maps;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.labyrinth.client.GamesClient;
import com.labyrinth.client.MapsClient;

public class MapsDeleteTests extends MapsAPITests
{
	@BeforeTest
	public void setup()
	{
		super.startup();
		System.out.println("STARTING MAPS DELETE TESTS");
		mapsClient = new MapsClient(email, password1);
		gamesClient = new GamesClient(email, password1);
	}
	
	@Test
	public void deleteMap()
	{
		String message = "We could not find a Map for that Game ID";
		String game = gamesClient.createGame();
		int gameId = getGameIdFromGame(game);
		int mapId = getMapIdFromGame(game);
		
		mapsClient.deleteMap(mapId);
		
		String response = mapsClient.getMapsFromMapId(mapId);
		
		assertTrue(response.contains(message),
				"The response should have contained an error message, but instead was this:\n"
				+ response);
		gamesClient.deleteGame(gameId);
	}
	
	@Test
	public void deleteMapWithNoId()
	{
		String response = mapsClient.deleteMap("");
		String message = "You have to include an ID if you want to delete a Map";
		
		assertTrue(response.contains(message), 
				"The response should have contained an error message, but instead was this:\n"
				+ response);
	}
	
	@Test
	public void deleteMapWithInvalidStringId()
	{
		String response = mapsClient.deleteMap("nonsense");
		String message = "You have to include an ID if you want to delete a Map";

		assertTrue(response.contains(message), 
				"The response should have contained an error message, but instead was this:\n"
				+ response);
	}

	@Test
	public void deleteMapWithInvalidIntegerId()
	{
		String response = mapsClient.deleteMap(1);
		String message = "We did not find a Map with that ID";
		
		assertTrue(response.contains(message), 
				"The response should have contained an error message, but instead was this:\n"
				+ response);
	}
}
