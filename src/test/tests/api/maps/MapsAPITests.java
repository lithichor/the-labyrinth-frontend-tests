package test.tests.api.maps;

import org.testng.annotations.BeforeTest;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.labyrinth.client.GamesClient;
import com.labyrinth.client.MapsClient;

import test.parents.LabyrinthAPITest;

public class MapsAPITests extends LabyrinthAPITest
{
	@BeforeTest
	public void setup()
	{
		System.out.println("STARTING MAPS GET TESTS");
		mapsClient = new MapsClient(email, password1);
		gamesClient = new GamesClient(email, password1);
	}
	
	protected Integer getGameIdFromGame(String game)
	{
		JsonObject gameObj = gson.fromJson(game, JsonObject.class);
		return gameObj.get("id").getAsInt();
	}
	
	protected Integer getMapIdFromGame(String game)
	{
		JsonObject gameObj = gson.fromJson(game, JsonObject.class);
		return gameObj.get("mapIds").getAsJsonArray().get(0).getAsInt();
	}
	
	protected Integer getGameIdFromMap(String map)
	{
		JsonObject mapObj = gson.fromJson(map, JsonObject.class);
		return mapObj.get("gameId").getAsInt();
	}
	
	protected Integer getMapIdFromMap(String map)
	{
		JsonObject mapObj = gson.fromJson(map, JsonObject.class);
		return mapObj.get("id").getAsInt();
	}
	
	protected int getMessageCount(String errorMessage)
	{
		try
		{
			JsonArray messages = gson.fromJson(errorMessage, JsonArray.class);
			return messages.size();
		}
		catch(ClassCastException mje_cce)
		{
			// this means it's just one message, not
			// an array of them
			return 1;
		}
	}
}
