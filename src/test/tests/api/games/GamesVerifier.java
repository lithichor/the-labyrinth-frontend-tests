package test.tests.api.games;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import test.parents.LabyrinthAPITestVerifier;

public class GamesVerifier extends LabyrinthAPITestVerifier
{
	public boolean verifyAllGames(String gamesArray)
	{
		JsonArray games = null;
		try
		{
			games = gson.fromJson(gamesArray, JsonArray.class);
		}
		catch(ClassCastException cce)
		{
			games = new JsonArray();
			games.add(gson.fromJson(gamesArray, JsonObject.class));
		}
		
		boolean verified = true;
		
		if(games.size() < 1)
		{
			errors.add("The response held no objects");
			verified = false;
		}
		for(JsonElement game: games)
		{
			verified = verifyOneGame(gson.toJson(game));
		}
		
		return verified;
	}
	
	public boolean verifyOneGame(String gameStr)
	{
		JsonObject game = gson.fromJson(gameStr, JsonObject.class);
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
	
	public boolean compareGameIds(String fromAll, String fromLast)
	{
		boolean valid = true;
		JsonObject fromAllObj = gson.fromJson(fromAll, JsonObject.class);
		JsonObject fromLastObj = gson.fromJson(fromLast, JsonObject.class);
		
		int idFromAll = fromAllObj.get("id").getAsInt();
		int idFromLast = fromLastObj.get("id").getAsInt();
		
		if(!(idFromAll == idFromLast))
		{
			valid = false;
			errors.add("The IDs are not the same");
		}
		
		return valid;
	}
	
	public boolean compareGames(String gameOne, String gameTwo)
	{
		boolean matches = true;
		JsonObject gameOneObj = gson.fromJson(gameOne, JsonObject.class);
		JsonObject gameTwoObj = gson.fromJson(gameTwo, JsonObject.class);
		
		if(!(gameOneObj.get("id").getAsInt() == gameTwoObj.get("id").getAsInt()))
		{
			matches = false;
			errors.add("The IDs do not match");
		}
		if(!(gameOneObj.get("heroId").getAsInt() == gameTwoObj.get("heroId").getAsInt()))
		{
			matches = false;
			errors.add("The Hero IDs do not match");
		}
		if(!(gameOneObj.get("userId").getAsInt() == gameTwoObj.get("userId").getAsInt()))
		{
			matches = false;
			errors.add("The User IDs do not match");
		}
		JsonArray gameOneMaps = gameOneObj.get("mapIds").getAsJsonArray();
		JsonArray gameTwoMaps = gameTwoObj.get("mapIds").getAsJsonArray();
		if(!(gameOneMaps.size() == gameTwoMaps.size()))
		{
			matches = false;
			errors.add("The sizes of the mapId arrays don't match");
		}
		else
		{
			for(int x = 0; x < gameOneMaps.size(); x++)
			{
				if(!(gameOneMaps.get(x).getAsInt() == gameTwoMaps.get(x).getAsInt()))
				{
					matches = false;
					errors.add("The mapIds at index " + x + " don't match");
				}
			}
		}
		
		return matches;
	}
	
	public boolean verifyGamesOptions(String opts)
	{
		boolean valid = true;
		JsonObject options = gson.fromJson(opts, JsonObject.class);
		
		if(options.entrySet().size() > 5)
		{
			errors.add("There were more than five fields");
			valid = false;
		}
		if(options.entrySet().size() < 5)
		{
			errors.add("There were fewer than five fields");
			valid = false;
		}
		
		if(!options.has("basics"))
		{
			errors.add("The Basics field is missing from the Game Options");
			valid = false;
		}
		if(!options.has("delete"))
		{
			errors.add("The Delete field is missing from the Game Options");
			valid = false;
		}
		if(!options.has("fields"))
		{
			errors.add("The Fields field is missing from the Game Options");
			valid = false;
		}
		if(!options.has("get"))
		{
			errors.add("The Get field is missing from the Game Options");
			valid = false;
		}
		if(!options.has("post"))
		{
			errors.add("The Post field is missing from the Game Options");
			valid = false;
		}
		
		return valid;
	}
}
