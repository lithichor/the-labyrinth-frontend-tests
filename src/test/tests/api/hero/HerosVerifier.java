package test.tests.api.hero;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.google.gson.JsonObject;

import test.parents.LabyrinthAPITestVerifier;

public class HerosVerifier extends LabyrinthAPITestVerifier
{
	public static final String HEALTH = "health";
	public static final String STRENGTH = "strength";
	public static final String MAGIC = "magic";
	public static final String ATTACK = "attack";
	public static final String DEFENSE = "defense";
	
	/**
	 * Check to see if the updated fields are represented
	 * in the response. If a field is in the fieldsChanged
	 * list, compare the old and new values to see if they
	 * changed.
	 * 
	 * @param newHero
	 * @param oldHero
	 * @param fieldsChanged
	 * @return
	 */
	public boolean verifyHeroUpdated(String newHero, String oldHero, ArrayList<String> fieldsChanged)
	{
		boolean updated = true;
		JsonObject newHeroJson = gson.fromJson(newHero, JsonObject.class);
		JsonObject oldHeroJson = gson.fromJson(oldHero, JsonObject.class);
		
		if(fieldsChanged.contains(HEALTH))
		{
			if(newHeroJson.get("health").getAsInt() == oldHeroJson.get("health").getAsInt())
			{
				errors.add("The health attributes should not match");
				updated = false;
			}
		}
		if(fieldsChanged.contains(STRENGTH))
		{
			if(newHeroJson.get("strength").getAsInt() == oldHeroJson.get("strength").getAsInt())
			{
				errors.add("The strength attributes should not match");
				updated = false;
			}
		}
		if(fieldsChanged.contains(MAGIC))
		{
			if(newHeroJson.get("magic").getAsInt() == oldHeroJson.get("magic").getAsInt())
			{
				errors.add("The magic attributes should not match");
				updated = false;
			}
		}
		if(fieldsChanged.contains(ATTACK))
		{
			if(newHeroJson.get("attack").getAsInt() == oldHeroJson.get("attack").getAsInt())
			{
				errors.add("The attack attributes should not match");
				updated = false;
			}
		}
		if(fieldsChanged.contains(DEFENSE))
		{
			if(newHeroJson.get("defense").getAsInt() == oldHeroJson.get("defense").getAsInt())
			{
				errors.add("The defense attributes should not match");
				updated = false;
			}
		}
		
		return updated;
	}
	
	/**
	 * Check the values of the hero against what we expect those
	 * values to be. For each key in the hash map, compare the
	 * expected value to the actual value.
	 * @param hero
	 * @param changes
	 * @return
	 */
	public boolean verifyHero(String hero, HashMap<String, Integer> changes)
	{
		boolean matches = true;
		Collection<String> keys = changes.keySet();
		JsonObject heroObject = gson.fromJson(hero, JsonObject.class);
		
		for(String k: keys)
		{
			if(!(heroObject.get(k).getAsInt() == changes.get(k)))
			{
				errors.add("The " + k + " attribute did not match what was expected");
				matches = false;
			}
		}
		
		return matches;
	}
	
	public boolean verifyDefaultHero(String hero)
	{
		boolean valid = true;
		JsonObject heroObj = gson.fromJson(hero, JsonObject.class);
		
		if(heroObj.entrySet().size() > 9)
		{
			errors.add("There are more than 9 fields");
			valid = false;
		}
		if(heroObj.entrySet().size() < 9)
		{
			errors.add("There are fewer than 9 fields");
			valid = false;
		}
		
		if(!heroObj.has("id"))
		{
			errors.add("The Hero doesn't have an ID field");
			valid = false;
		}
		if(!heroObj.has("gameId"))
		{
			errors.add("The Hero doesn't have a Game ID field");
			valid = false;
		}
		if(!heroObj.has("health"))
		{
			errors.add("The Hero doesn't have a Health field");
			valid = false;
		}
		if(!heroObj.has("maxHealth"))
		{
			errors.add("The Hero doesn't have a MaxHealth field");
			valid = false;
		}
		if(!heroObj.has("strength"))
		{
			errors.add("The Hero doesn't have a Strength");
			valid = false;
		}
		if(!heroObj.has("magic"))
		{
			errors.add("The Hero doesn't have a Magic field");
			valid = false;
		}
		if(!heroObj.has("attack"))
		{
			errors.add("The Hero doesn't have an Attack field");
			valid = false;
		}
		if(!heroObj.has("defense"))
		{
			errors.add("The Hero doesn't have Defense field");
			valid = false;
		}
		if(!heroObj.has("experience"))
		{
			errors.add("The Hero doesn't have an Experience field");
			valid = false;
		}
		
		return valid;
	}
	
	public boolean verifyHeroOptions(String opts)
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
			errors.add("The Basics field is missing from the Hero Options");
			valid = false;
		}
		if(!options.has("fields"))
		{
			errors.add("The Fields field is missing from the Hero Options");
			valid = false;
		}
		if(!options.has("get"))
		{
			errors.add("The Get field is missing from the Hero Options");
			valid = false;
		}
		if(!options.has("put"))
		{
			errors.add("The Put field is missing from the Hero Options");
			valid = false;
		}
		if(!options.has("seeAlso"))
		{
			errors.add("The seeAlso field is missing from the Hero Options");
			valid = false;
		}
		
		return valid;
	}
	
	public boolean verifyHerosGameOptions(String opts)
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
			errors.add("The Get field is missing from the Heros-Game Options");
			valid = false;
		}
		
		return valid;
	}
}
