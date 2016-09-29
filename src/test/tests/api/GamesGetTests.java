package test.tests.api;

import java.io.IOException;

import org.apache.http.ParseException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.testng.annotations.Test;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import test.parents.LabyrinthAPITest;

public class GamesGetTests extends LabyrinthAPITest
{
	@Test
	public void getAllGamesForUser()
	{
		HttpGet get = makeGetMethod("games");
		
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
		
		JsonArray games = gson.fromJson(responseString, JsonArray.class);
		
		if(!verifier.verifyAllGames(games))
		{
			fail(verifier.getErrors());
		}
		
		if(debug)
		{
			for(JsonElement j: games)
			{
				System.out.println(j.toString());
			}
		}
	}
	
	@Test
	public void getOneGameForUser()
	{
		HttpGet get = makeGetMethod("games/13");
		
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
		
		JsonObject game = gson.fromJson(responseString, JsonArray.class).get(0).getAsJsonObject();
		if(!verifier.verifyOneGame(game))
		{
			fail(verifier.getErrors());
		}
	}
}
