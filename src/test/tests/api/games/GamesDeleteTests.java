package test.tests.api.games;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.google.gson.JsonObject;
import com.labyrinth.client.GamesClient;

import test.parents.LabyrinthAPITest;

/**
 * This test should be run for general regression purposes.
 * 
 * @author spiralgyre
 *
 */
public class GamesDeleteTests extends LabyrinthAPITest
{
	private GamesClient client;

	@BeforeTest
	public void setup()
	{
		System.out.println("STARTNG GAMES DELETE TESTS");
		client = new GamesClient("eric@eric.corn", "1qweqwe");
	}

	@Test
	public void deleteGame()
	{
		// create a new game (post) and get the id from the response
		String resp = client.createGame();
		JsonObject game = gson.fromJson(resp, JsonObject.class);
		int id = (game.get("id")).getAsInt();

		// delete the game just created
		resp = client.deleteGame(id);
		if(!resp.equalsIgnoreCase(""))
		{
			fail("There was an error deleting the game");
		}
		
		// get the game just deleted; we should receive an error
		resp = client.getOneGame(id);
		if(!resp.contains("This Player has no active Games"))
		{
			fail("The game does not seem to be deleted");
		}
	}
}
