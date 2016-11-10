package test.tests.api.hero;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.google.gson.JsonObject;
import com.labyrinth.client.GamesClient;
import com.labyrinth.client.HerosClient;

import test.parents.LabyrinthAPITest;

public class HeroGetTests extends LabyrinthAPITest
{
	private HerosClient herosClient;
	private GamesClient gamesClient;
	private int gameId = 0;
	
	@BeforeTest
	public void setup()
	{
		System.out.println("STARTING HERO GET TESTS");
		herosClient = new HerosClient(username, password);
		gamesClient = new GamesClient(username, password);
	}
	
	@Test
	public void getHero()
	{
		// create game
		String game = gamesClient.createGame();
		
		// get hero
		String hero = herosClient.getCurrentHero();
		
		// verify hero's id matches heroId from game
		int heroId = gson.fromJson(hero, JsonObject.class).get("id").getAsInt();
		int gameHeroId = gson.fromJson(game, JsonObject.class).get("heroId").getAsInt();
		gameId = gson.fromJson(game, JsonObject.class).get("id").getAsInt();
		
		if(!(heroId == gameHeroId))
		{
			fail("The IDs for the hero did not match");
		}
	}
	
	@AfterTest
	public void cleanup()
	{
		System.out.println("CLEANING UP AFTER HERO GET TESTS");
		gamesClient.deleteGame(gameId);
	}
}
