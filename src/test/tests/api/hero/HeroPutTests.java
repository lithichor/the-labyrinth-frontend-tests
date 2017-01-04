package test.tests.api.hero;

import java.util.ArrayList;
import java.util.HashMap;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.google.gson.JsonObject;
import com.labyrinth.client.GamesClient;
import com.labyrinth.client.HerosClient;

import test.helpers.HerosVerifier;
import test.parents.LabyrinthAPITest;

public class HeroPutTests extends LabyrinthAPITest
{
	private HerosClient herosClient;
	private GamesClient gamesClient;
	private HerosVerifier verifier;

	@BeforeTest
	public void setup()
	{
		super.startup();
		System.out.println("STARTING HEROS PUT TESTS");
		herosClient = new HerosClient(email, password1);
		gamesClient = new GamesClient(email, password1);
		verifier = new HerosVerifier();
	}
	
	@Test
	public void updateHero()
	{
		int strength = rand.nextInt(15) + 1;
		int magic = rand.nextInt(15) + 1;
		int attack = rand.nextInt(15) + 1;
		int defense = rand.nextInt(15) + 1;

		// create new game (which creates a hero)
		String game = gamesClient.createGame();
		JsonObject gameObject = gson.fromJson(game, JsonObject.class);
		int heroId = gameObject.get("heroId").getAsInt();
		int gameId = gameObject.get("id").getAsInt();
		
		// verify heroId is correct
		String hero = herosClient.getHeros();
		JsonObject heroObject = gson.fromJson(hero, JsonObject.class);
		if(!(heroId == heroObject.get("id").getAsInt()))
		{
			fail("The Hero ID from the game did not match the ID of the current Hero");
		}
		
		// update the hero's attributes
		String data = "{gameId: " + gameId +
				", strength: " + strength + 
				", magic: " + magic + 
				", attack: " + attack + 
				", defense: " + defense + "}";
		String updatedHero = herosClient.updateCurrentHero(heroId, data);
		
		ArrayList<String> changes = new ArrayList<>();
		changes.add(HerosVerifier.STRENGTH);
		changes.add(HerosVerifier.MAGIC);
		changes.add(HerosVerifier.ATTACK);
		changes.add(HerosVerifier.DEFENSE);
		
		// verify the fields were updated
		if(!verifier.verifyHeroUpdated(hero, updatedHero, changes))
		{
			fail(verifier.getErrors());
		}
		
		HashMap<String, Integer> changedFields = new HashMap<>();
		changedFields.put(HerosVerifier.STRENGTH, strength);
		changedFields.put(HerosVerifier.MAGIC, magic);
		changedFields.put(HerosVerifier.ATTACK, attack);
		changedFields.put(HerosVerifier.DEFENSE, defense);
		
		// check each field
		if(!verifier.verifyHero(updatedHero, changedFields))
		{
			fail(verifier.getErrors());
		}
		
		// delete game
		gamesClient.deleteGame(gameId);
	}
	
	@Test
	public void updateHeroWithStringsForInts()
	{
		// create new game
		String game = gamesClient.createGame();
		JsonObject gameObj = gson.fromJson(game, JsonObject.class);
		int gameId = gameObj.get("id").getAsInt();
		int heroId = gameObj.get("heroId").getAsInt();
		
		// update randomly with strings
		String data = "";
		switch(rand.nextInt(4))
		{
		case 0:
			data = "{strength: abc,";
			break;
		case 1:
			data = "{magic: abc,";
			break;
		case 2:
			data = "{attack: abc,";
			break;
		case 3:
			data = "{defense: abc,";
			break;
		}
		data += " gameId: " + gameId + "}";

		String response = herosClient.updateCurrentHero(heroId, data);
		
		// verify error message
		if(!response.contains("attribute has to be an integer"))
		{
			fail("There should have been an error returned, but there wasn't");
		}
		
		// delete game
		gamesClient.deleteGame(gameId);
	}
	
	@Test
	public void updateHeroWithHashForInt()
	{
		// create new game
		String game = gamesClient.createGame();
		JsonObject gameObj = gson.fromJson(game, JsonObject.class);
		int gameId = gameObj.get("id").getAsInt();
		int heroId = gameObj.get("heroId").getAsInt();
		
		// update randomly with strings
		String data = "";
		switch(rand.nextInt(8))
		{
		case 0:
			data = "{strength: {q: q},";
			break;
		case 1:
			data = "{magic: {q: Q},";
			break;
		case 2:
			data = "{attack: {p: p},";
			break;
		case 3:
			data = "{defense: {p: q},";
			break;
		case 4:
			data = "{strength: {},";
			break;
		case 5:
			data = "{magic: {},";
			break;
		case 6:
			data = "{attack: {},";
			break;
		case 7:
			data = "{defense: {},";
			break;
		}
		data += " gameId: " + gameId + "}";
		
		String response = herosClient.updateCurrentHero(heroId, data);
		
		// verify error message
		if(!response.contains("attribute has to be an integer"))
		{
			fail("There should have been an error returned, but there wasn't");
		}
		
		// delete game
		gamesClient.deleteGame(gameId);
	}

	@Test
	public void updateHeroWithArrayForInt()
	{
		// create new game
		String game = gamesClient.createGame();
		JsonObject gameObj = gson.fromJson(game, JsonObject.class);
		int gameId = gameObj.get("id").getAsInt();
		int heroId = gameObj.get("heroId").getAsInt();
		
		// update randomly with strings
		String data = "";
		switch(rand.nextInt(8))
		{
		case 0:
			data = "{strength: [1, 2, 3],";
			break;
		case 1:
			data = "{magic: [a, b, c],";
			break;
		case 2:
			data = "{attack: [a, 2, !],";
			break;
		case 3:
			data = "{defense: [1, W, c],";
			break;
		case 4:
			data = "{strength: [],";
			break;
		case 5:
			data = "{magic: [],";
			break;
		case 6:
			data = "{attack: [],";
			break;
		case 7:
			data = "{defense: [],";
			break;
		}
		data += " gameId: " + gameId + "}";
		
		String response = herosClient.updateCurrentHero(heroId, data);
		
		// verify error message
		if(!response.contains("attribute has to be an integer"))
		{
			fail("There should have been an error returned, but there wasn't");
		}
		
		// delete game
		gamesClient.deleteGame(gameId);
	}
	
	@Test
	public void updateHeroWithNoData()
	{
		// create new game
		String game = gamesClient.createGame();
		JsonObject gameObj = gson.fromJson(game, JsonObject.class);
		int heroId = gameObj.get("heroId").getAsInt();
		String response = herosClient.updateCurrentHero(heroId, "");
		String message = "You have to include JSON formatted data to update a Hero";
		
		assertTrue(response.contains(message),
				"The response should have contained an error message, but instead contained this:\n"
						+ response);
	}
}
