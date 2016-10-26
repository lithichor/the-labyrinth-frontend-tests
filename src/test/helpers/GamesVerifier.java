package test.helpers;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import test.parents.LabyrinthAPITestVerifier;

public class GamesVerifier extends LabyrinthAPITestVerifier
{
	public boolean verifyAllGames(JsonArray games)
	{
		JsonObject lastGame = games.get(games.size() - 1).getAsJsonObject();
		boolean verified = true;
		
		if(games.size() < 1)
		{
			errors.add("The response held no objects");
			verified = false;
		}
		verified = verifyOneGame(lastGame);
		
		return verified;
	}
	
	public boolean verifyOneGame(JsonObject game)
	{
		boolean verified = true;

		if(game.has("messages"))
		{
			errors.add("There was an error: " + game.get("messages"));
			return false;
		}
		
		if(!game.has("id"))
		{
			errors.add("There was no ID");
			verified = false;
		}
		if(!game.has("userId"))
		{
			errors.add("There was no User ID");
			verified = false;
		}
		if(!game.has("heroId"))
		{
			errors.add("There was no Hero ID");
			verified = false;
		}
		if(!game.has("mapIds"))
		{
			errors.add("There was no Map IDs");
			verified = false;
		}
		
		return verified;
	}
}
