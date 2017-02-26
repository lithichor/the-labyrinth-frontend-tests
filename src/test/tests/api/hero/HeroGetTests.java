package test.tests.api.hero;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.google.gson.JsonObject;
import com.labyrinth.client.GamesClient;
import com.labyrinth.client.HerosClient;

import test.parents.LabyrinthAPITest;

public class HeroGetTests extends LabyrinthAPITest
{
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
		int gameId = gson.fromJson(game, JsonObject.class).get("id").getAsInt();
		gamesClient.deleteGame(gameId);
		
		assertTrue(heroId == gameHeroId, "The IDs for the hero did not match");
	}
	
	@Test
	public void getHeroWithId()
	{
		String game = gamesClient.createGame();
		JsonObject gameObj = gson.fromJson(game, JsonObject.class);
		int gameId = gameObj.get("id").getAsInt();
		int heroId = gameObj.get("heroId").getAsInt();
		
		String hero = herosClient.getHero(heroId);
		JsonObject heroObj = gson.fromJson(hero, JsonObject.class);
		int heroIdFromHero = heroObj.get("id").getAsInt();
		
		gamesClient.deleteGame(gameId);
		
		assertEquals(heroId, heroIdFromHero,
				"The IDs of the heros are not the same:\n"
				+ "FROM GAME: " + heroId + "\n"
				+ "FROM HERO: " + heroIdFromHero + "\n");
	}
	
	@Test
	public void getHeroCrossTenant()
	{
		String game = gamesClient.createGame();
		int heroId = gson.fromJson(game, JsonObject.class).get("heroId").getAsInt();
		int gameId = gson.fromJson(game, JsonObject.class).get("id").getAsInt();
		gamesClient.deleteGame(gameId);

		// create second user and new heros client
		String secondUser[] = createSecondUser();
		HerosClient secondHerosClient = new HerosClient(secondUser[0], secondUser[1]);
		
		String message = "We did not find a Hero with that ID";
		String hero = secondHerosClient.getHero(heroId);

		assertTrue(hero.contains(message), "We should have gotten an error message, but didn't:\n"
				+ "EXPECTED: " + message
				+ "\nACTUAL: " + hero);
	}
	
	@Test
	public void getHeroWithAlphStringForId()
	{
		// String instead of integer in URL is ignored
		// create a game and get the ID
		String gameOne = gamesClient.createGame();
		JsonObject gameObjOne = gson.fromJson(gameOne, JsonObject.class);
		int gameIdOne = gameObjOne.get("id").getAsInt();
		
		// get the hero using the game's heroId
		int heroIdFromGame = gameObjOne.get("heroId").getAsInt();
		
		// get the hero with an alphabetic String
		String hero = herosClient.getHero("asd");
		JsonObject heroObj = gson.fromJson(hero, JsonObject.class);
		int heroId = heroObj.get("id").getAsInt();
		
		gamesClient.deleteGame(gameIdOne);
		
		assertEquals(heroId, heroIdFromGame, "The IDs should have been the same");
	}
}
