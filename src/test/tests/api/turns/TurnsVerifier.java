package test.tests.api.turns;

import com.google.gson.JsonObject;

import test.parents.LabyrinthAPITestVerifier;

public class TurnsVerifier extends LabyrinthAPITestVerifier
{
	public boolean verifyFields(String turn)
	{
		boolean valid = true;
		JsonObject turnObj = gson.fromJson(turn, JsonObject.class);
		JsonObject coords = null;
		
		if(turnObj.entrySet().size() > 6)
		{
			errors.add("There are more than 6 fields in the turn");
			valid = false;
		}
		if(turnObj.entrySet().size() < 6)
		{
			errors.add("There are fewer than 6 fields in the turn");
			valid = false;
		}
		if(!turnObj.has("id"))
		{
			errors.add("There is no ID field");
			valid = false;
		}
		if(!turnObj.has("iteration"))
		{
			errors.add("There is no Iteration field");
			valid = false;
		}
		if(!turnObj.has("userId"))
		{
			errors.add("There is no User ID field");
			valid = false;
		}
		if(!turnObj.has("gameId"))
		{
			errors.add("There is no Game ID field");
			valid = false;
		}
		if(!turnObj.has("mapId"))
		{
			errors.add("There is no Map ID field");
			valid = false;
		}
		if(!turnObj.has("coords"))
		{
			errors.add("There is no Coord field");
			valid = false;
		}
		else
		{
			coords = turnObj.get("coords").getAsJsonObject();
			if(coords.entrySet().size() > 2)
			{
				errors.add("There are less than 2 coordinates");
				valid = false;
			}
			if(coords.entrySet().size() > 2)
			{
				errors.add("There are more than 2 coordinates");
				valid = false;
			}
			if(!coords.has("x"))
			{
				errors.add("There is no X coordinate");
				valid = false;
			}
			if(!coords.has("y"))
			{
				errors.add("There is no Y coordinate");
				valid = false;
			}
		}
		
		return valid;
	}
	
	public boolean updateTurn(String newTurn)
	{
		boolean valid = true;
		JsonObject turnsObj = gson.fromJson(newTurn, JsonObject.class);
		
		if(!verifyFields(newTurn))
		{
			valid = false;
		}
		else if(!(turnsObj.get("iteration").getAsInt() == 1))
		{
			errors.add("The iteration fields was not 1");
			valid = false;
		}
		
		return valid;
	}
}
