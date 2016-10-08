package test.tests.api.games;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.google.gson.JsonObject;
import com.labyrinth.client.GamesClient;

import test.helpers.GamesVerifier;
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
	private GamesVerifier verifier;
	private GamesClient client;
	
	@BeforeTest
	public void setup()
	{
		System.out.println("STARTNG GAMES POST TESTS");
		verifier = new GamesVerifier();
		client = new GamesClient("eric@eric.corn", "1qweqwe");
	}
	
	@Test
	public void ZstartNewGame()
	{
		String resp = client.createGame();
		JsonObject game = gson.fromJson(resp, JsonObject.class);
		
		if(!verifier.verifyOneGame(game))
		{
			fail(verifier.getErrors());
		}
	}
}
