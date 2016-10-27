package test.helpers;

import com.google.gson.JsonObject;

import test.parents.LabyrinthAPITestVerifier;

public class UserVerifier extends LabyrinthAPITestVerifier
{
	public boolean verifyCurrentUser(JsonObject user)
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
	
	public boolean verifyUserUpdated(String newUser, String oldUser)
	{
		JsonObject newUserJson = gson.fromJson(newUser, JsonObject.class);
		JsonObject oldUserJson = gson.fromJson(oldUser, JsonObject.class);
		boolean verified = false;
		
		if(!oldUserJson.get("id").equals(newUserJson.get("id")))
		{
			errors.add("The IDs do not match");
			verified = false;
		}
		if(!oldUserJson.get("firstName").equals(newUserJson.get("firstName")))
		{
			errors.add("The First Names do not match");
			verified = false;
		}
		if(!oldUserJson.get("lastName").equals(newUserJson.get("lastName")))
		{
			errors.add("The Last Names do not match");
			verified = false;
		}
		if(!oldUserJson.get("email").equals(newUserJson.get("email")))
		{
			errors.add("The Emails do not match");
			verified = false;
		}
		// This may need to be refactored after Labyrinth bug #59 is fixed
		// not sure how these arrays will be compared
		if(!oldUserJson.get("gameIds").equals(newUserJson.get("gameIds")))
		{
			errors.add("The GameIds do not match");
			verified = false;
		}
		
		return verified;
	}
}
