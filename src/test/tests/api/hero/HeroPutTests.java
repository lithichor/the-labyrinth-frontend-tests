package test.tests.api.hero;

import java.util.ArrayList;
import java.util.HashMap;

import org.testng.annotations.AfterTest;
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
	private int gameId = 0;

	@BeforeTest
	public void setup()
	{
		System.out.println("STARTNG USER PUT TESTS");
		herosClient = new HerosClient(username, password);
		gamesClient = new GamesClient(username, password);
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
		gameId = gameObject.get("id").getAsInt();
		
		// verify heroId is correct
		String hero = herosClient.getCurrentHero();
		JsonObject heroObject = gson.fromJson(hero, JsonObject.class);
		if(!(heroId == heroObject.get("id").getAsInt()))
		{
			fail("The Hero ID from the game did not match the ID of the current Hero");
		}
		
		// update the hero's attributes
		String data = "{strength: " + strength + 
				", magic: " + magic + 
				", attack: " + attack + 
				", defense: " + defense + "}";
		String updatedHero = herosClient.updateCurrentHero(data);
		
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
	}
	
	@AfterTest
	public void cleanup()
	{
		// delete the game
		System.out.println("CLEAN UP");
		gamesClient.deleteGame(gameId);
	}
}
