package test.helpers;

import java.util.Map.Entry;
import java.util.Set;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import test.parents.LabyrinthAPITestVerifier;

public class ConstantsVerifier extends LabyrinthAPITestVerifier
{
	public boolean verifyConstants(String constants)
	{
		boolean verified = true;
		JsonObject constantsObj = gson.fromJson(constants, JsonObject.class);
		
		Set<Entry<String, JsonElement>> x = constantsObj.entrySet();
		
		if(!(x.size() == 1))
		{
			errors.add("The size of the response is " + x.size() + ", not ONE.\nThis is what we got: " + constants);
			verified = false;
		}
		
		if(!constantsObj.has("GRID_SIZE"))
		{
			errors.add("Looks like Grid Size is missing from the response");
			verified = false;
		}
		
		return verified;
	}
}
