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
		
		if(!mapObj.has("id"))
		{
			valid = false;
			errors.add("There is no ID for this map");
		}
		if(!mapObj.has("gameId"))
		{
			valid = false;
			errors.add("This map doesn't have a gameId");
		}
		
		return valid;
	}
}
