package test.helpers;

import java.util.ArrayList;

import com.google.gson.JsonObject;

import test.models.constants.LabyrinthTestConstants;
import test.parents.LabyrinthAPITestVerifier;

public class UserVerifier extends LabyrinthAPITestVerifier
{
	public boolean verifyUser(JsonObject user)
	{
		boolean verified = true;
		
		if(user.has("messages"))
		{
			errors.add("There was an error: " + user.get("messages"));
			return false;
		}
		
		if(!user.has("id"))
		{
			errors.add("There was no ID");
			verified = false;
		}
		if(!user.has("firstName"))
		{
			errors.add("There was no first name");
			verified = false;
		}
		if(!user.has("lastName"))
		{
			errors.add("There was no last name");
			verified = false;
		}
		if(!user.has("email"))
		{
			errors.add("There was no email");
			verified = false;
		}
		if(!user.has("gameIds"))
		{
			errors.add("There were no Game IDs");
			verified = false;
		}
		
		return verified;
	}
	
	public boolean verifyUserUpdated(String newUser, String oldUser, ArrayList<String> changedFields)
	{
		JsonObject newUserJson = gson.fromJson(newUser, JsonObject.class);
		JsonObject oldUserJson = gson.fromJson(oldUser, JsonObject.class);
		boolean verified = true;
		
		if(!oldUserJson.get("id").equals(newUserJson.get("id")))
		{
			errors.add("The IDs do not match");
			verified = false;
		}
		if(changedFields.contains(LabyrinthTestConstants.FIRST_NAME) &&
				oldUserJson.get("firstName").equals(newUserJson.get("firstName")))
		{
			errors.add("The First Names match, but they shouldn't");
			verified = false;
		}
		if(changedFields.contains(LabyrinthTestConstants.LAST_NAME) &&
				oldUserJson.get("lastName").equals(newUserJson.get("lastName")))
		{
			errors.add("The Last Names match, but they shouldn't");
			verified = false;
		}
		if(!oldUserJson.get("email").equals(newUserJson.get("email")))
		{
			errors.add("The Emails do not match");
			verified = false;
		}
		if(changedFields.contains(LabyrinthTestConstants.PASSWORD) &&
				oldUserJson.get("password").equals(newUserJson.get("password")))
		{
			errors.add("The Passwords match, but they shouldn't");
			verified = false;
		}
		if(changedFields.contains(LabyrinthTestConstants.GAME_IDS) &&
				oldUserJson.get("gameIds").equals(newUserJson.get("gameIds")))
		{
			errors.add("The GameIds match, but they shouldn't");
			verified = false;
		}
		
		return verified;
	}
}
