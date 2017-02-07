package test.tests.api.monsters;

import com.google.gson.JsonObject;

import test.parents.LabyrinthAPITestVerifier;

public class MonstersVerifier extends LabyrinthAPITestVerifier
{
	public boolean verifyMonster(String monsterStr)
	{
		boolean valid = true;
		JsonObject monster = gson.fromJson(monsterStr, JsonObject.class);
		
		if(monster.has("message"))
		{
			errors.add("There was an error from the server preventing validation:\n\t" + monster.get("message"));
			return false;
		}
		
		if(!monster.has("id"))
		{
			errors.add("There was no ID");
			valid = false;
		}
		if(!monster.has("tileId"))
		{
			errors.add("There was no Tile ID");
			valid = false;
		}
		if(!monster.has("health"))
		{
			errors.add("There is no health attribute");
			valid = false;
		}
		if(!monster.has("attack"))
		{
			errors.add("There is no attack attribute");
			valid = false;
		}
		if(!monster.has("defense"))
		{
			errors.add("There is no defense attribute");
			valid = false;
		}
		if(monster.entrySet().size() > 5)
		{
			errors.add("There are more attributes than expected");
			valid = false;
		}
		if(monster.entrySet().size() < 5)
		{
			errors.add("There are fewer attributes than expected");
			valid = false;
		}
		
		return valid;
	}
}
