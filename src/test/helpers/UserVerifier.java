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

}
