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
		System.out.println("STARTING MAPS DELETE TESTS");
		mapsClient = new MapsClient(email, password1);
		gamesClient = new GamesClient(email, password1);
	}
	
	@Test
	public void deleteMap()
	{
//		String game = gamesClient.createGame();
//		int gameId = getGameIdFromGame(game);
//		int mapId = getMapIdFromGame(game);
//		String mapExists = mapsClient.getMapsForGame(gameId);
//		String mapDeleted = mapsClient.deleteMap(mapId);
	}
	
	@Test
	public void deleteMapWithNoId()
	{
//		String response = mapsClient.deleteMap("");
//		String message = "You have to include an ID if you want to delete a Map";
	}
	
	@Test
	public void deleteMapWithInvalidStringId()
	{
//		String response = mapsClient.deleteMap("nonsense");
//		String message = "You have to include an ID if you want to delete a Map";
	}

	@Test
	public void deleteMapWithInvalidIntegerId()
	{
//		String response = mapsClient.deleteMap(1);
//		String message = "You have to include an ID if you want to delete a Map";
	}
}
