package test.tests.api.maps;

import com.google.gson.JsonObject;

import test.parents.LabyrinthAPITest;

public class MapsAPITests extends LabyrinthAPITest
{
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
}
