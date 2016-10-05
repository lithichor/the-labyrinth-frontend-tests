package test.tests.api.games;

import java.io.IOException;

import org.apache.http.ParseException;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.util.EntityUtils;
import org.testng.annotations.Test;

import com.google.gson.JsonObject;

import test.parents.LabyrinthAPITest;

/**
 * This test should be run for general regression purposes.
 * 
 * @author spiralgyre
 *
 */
public class GamesDeleteTests extends LabyrinthAPITest
{
	@Test
	public void deleteGame()
	{
		// create a new game (post) and get the id from the response
		HttpPost post = makePostMethod("games");
		if(sendRequest(post))
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
		JsonObject game = gson.fromJson(responseString, JsonObject.class);
		int id = (game.get("id")).getAsInt();

		// delete the game just created
		HttpDelete delete = makeDeleteMethod("games/" + id);
		if(sendRequest(delete))
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
		
		// get the game just deleted; we should receive an error
		HttpGet get = makeGetMethod("games/" + id);
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
		
		if(!responseString.contains("This Player has no active Games"))
		{
			fail("The game does not seem to be deleted");
		}
	}
}
