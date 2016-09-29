package test.tests.api;

import java.io.IOException;

import org.apache.http.ParseException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.util.EntityUtils;
import org.testng.annotations.Test;

import com.google.gson.JsonObject;

import test.parents.LabyrinthAPITest;

/**
 * This test should not be run during general regression tests; it is
 * covered in the Delete test.
 * 
 * It should, however, run when we only want to test the Post method.
 * @author spiralgyre
 *
 */
public class GamesPostTests extends LabyrinthAPITest
{
	
	@Test
	public void startNewGame()
	{
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
		if(!verifier.verifyOneGame(game))
		{
			fail(verifier.getErrors());
		}
	}

}
