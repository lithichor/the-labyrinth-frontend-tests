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
		super.startup();
		System.out.println("STARTING HEROS GET TESTS");
		herosClient = new HerosClient(email, password1);
		gamesClient = new GamesClient(email, password1);
	}
	
	@Test
	public void getHero()
	{
		// create game
		String game = gamesClient.createGame();
		
		// get hero
		String hero = herosClient.getHeros();
		
		// verify hero's id matches heroId from game
		int heroId = gson.fromJson(hero, JsonObject.class).get("id").getAsInt();
		int gameHeroId = gson.fromJson(game, JsonObject.class).get("heroId").getAsInt();
		gameId = gson.fromJson(game, JsonObject.class).get("id").getAsInt();
		
		assertTrue(heroId == gameHeroId, "The IDs for the hero did not match");
	}
	
	@Test
	public void getHeroWithId()
	{
		String game = gamesClient.createGame();
		JsonObject gameObj = gson.fromJson(game, JsonObject.class);
		gameId = gameObj.get("id").getAsInt();
		int heroId = gameObj.get("heroId").getAsInt();
		
		String hero = herosClient.getHero(heroId);
		JsonObject heroObj = gson.fromJson(hero, JsonObject.class);
		int heroIdFromHero = heroObj.get("id").getAsInt();
		
		assertEquals(heroId, heroIdFromHero,
				"The IDs of the heros are not the same:\n"
				+ "FROM GAME: " + heroId + "\n"
				+ "FROM HERO: " + heroIdFromHero + "\n");
	}
	
//	@Test
	public void getHeroWithInvalidId()
	{
		// Waiting on Labyrinth Bug #148
		String hero = herosClient.getHero(1);
		String message = "There are no Heros matching that ID";
		assertTrue(hero.contains(message));
	}
	
//	@Test
	public void getHeroWithAlphForId()
	{
		// waiting on Labyrinth API Client issue #21
//		String hero = herosClient.getHero("asd");
		String hero = "";
		String message = "There are no Heros matching that ID";
		assertTrue(hero.contains(message));
	}
	
	@AfterTest
	public void cleanup()
	{
		System.out.println("CLEANING UP AFTER HERO GET TESTS");
		gamesClient.deleteGame(gameId);
	}
}
