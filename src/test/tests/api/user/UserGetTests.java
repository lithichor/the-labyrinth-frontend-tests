package test.tests.api.user;

import java.io.IOException;

import org.apache.http.ParseException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.testng.annotations.Test;

import com.google.gson.JsonObject;

import test.parents.LabyrinthAPITest;

public class UserGetTests extends LabyrinthAPITest
{

	@Test(groups = {"full_regression", "get"})
	public void getCurrentUser()
	{
		HttpGet get = makeGetMethod("user");
		
		if(sendRequest(get))
		{
			try
			{
				responseString = EntityUtils.toString(response.getEntity());
			}
			catch(ParseException | IOException pe_ioe)
			{
				if(debug){ pe_ioe.printStackTrace(); }
				fail("There was an error parsing the response: " + pe_ioe.getMessage());
			}
		}
	
		JsonObject user = gson.fromJson(responseString, JsonObject.class);
		if(!verifier.verifyCurrentUser(user))
		{
			fail(verifier.getErrors());
		}

	}
}
