package test.tests.api.maps;

import org.testng.annotations.Test;

public class MapsDeleteTests extends MapsAPITests
{
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
