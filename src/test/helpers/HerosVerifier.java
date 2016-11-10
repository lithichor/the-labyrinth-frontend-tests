package test.helpers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.google.gson.JsonObject;

import test.parents.LabyrinthAPITestVerifier;

public class HerosVerifier extends LabyrinthAPITestVerifier
{
	public static final String STRENGTH = "strength";
	public static final String MAGIC = "magic";
	public static final String ATTACK = "attack";
	public static final String DEFENSE = "defense";
	
	public boolean verifyHeroUpdated(String newHero, String oldHero, ArrayList<String> fieldsChanged)
	{
		boolean updated = true;
		JsonObject newHeroJson = gson.fromJson(newHero, JsonObject.class);
		JsonObject oldHeroJson = gson.fromJson(oldHero, JsonObject.class);
		
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
}
