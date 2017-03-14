package test.tests.api.maps;

import com.google.gson.JsonObject;

import test.parents.LabyrinthAPITestVerifier;

public class MapsVerifier extends LabyrinthAPITestVerifier
{
	public boolean verifyMap(String map)
	{
		boolean valid = true;
		JsonObject mapObj = gson.fromJson(map, JsonObject.class);
		
		if(mapObj.has("messages"))
		{
			valid = false;
			errors.add("There was an error: " + mapObj.get("messages"));
		}
		
		if(mapObj.entrySet().size() < 4)
		{
//			errors.add("There are fewer than 4 fields");
//			valid = false;
		}
		if(mapObj.entrySet().size() > 4)
		{
//			errors.add("There are more than 4 fields");
//			valid = false;
		}
		
		if(!mapObj.has("id"))
		{
			valid = false;
			errors.add("There is no ID for this map");
		}
		if(!mapObj.has("gameId"))
		{
			valid = false;
			errors.add("This map doesn't have a GameId field");
		}
		if(!mapObj.has("gridSize"))
		{
//			valid = false;
//			errors.add("This map doesn't have a GridSize field");
		}
		if(!mapObj.has("firstTileId"))
		{
//			valid = false;
//			errors.add("This map doesn't have a FirstTileId field");
		}
		
		return valid;
	}
	
	public boolean verifyMapsOptions(String opts)
	{
		boolean valid = true;
		JsonObject options = gson.fromJson(opts, JsonObject.class);
		
		if(options.entrySet().size() > 6)
		{
			errors.add("There were more than six fields");
			valid = false;
		}
		if(options.entrySet().size() < 6)
		{
			errors.add("There were fewer than six fields");
			valid = false;
		}
		
		if(!options.has("basics"))
		{
			errors.add("The Basics field is missing from the Map Options");
			valid = false;
		}
		if(!options.has("delete"))
		{
			errors.add("The Delete field is missing from the Map Options");
			valid = false;
		}
		if(!options.has("fields"))
		{
			errors.add("The Fields field is missing from the Map Options");
			valid = false;
		}
		if(!options.has("get"))
		{
			errors.add("The Get field is missing from the Map Options");
			valid = false;
		}
		if(!options.has("post"))
		{
			errors.add("The Post field is missing from the Map Options");
			valid = false;
		}
		if(!options.has("seeAlso"))
		{
			errors.add("The seeAlso field is missing from the Map Options");
			valid = false;
		}

		return valid;
	}
	
	public boolean verifyMapsGameOptions(String opts)
	{
		boolean valid = true;

		JsonObject options = gson.fromJson(opts, JsonObject.class);
		
		if(options.entrySet().size() > 1)
		{
			errors.add("There was more than one field");
			valid = false;
		}
		if(options.entrySet().size() < 1)
		{
			errors.add("There was less than one field");
			valid = false;
		}
		
		if(!options.has("get"))
		{
			errors.add("The Get field is missing from the Map Options");
			valid = false;
		}

		return valid;
	}
}
