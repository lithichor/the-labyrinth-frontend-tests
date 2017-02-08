package test.tests.api.monsters;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.labyrinth.client.GamesClient;
import com.labyrinth.client.MonstersClient;
import com.labyrinth.client.TilesClient;

import test.parents.LabyrinthAPITest;

public class MonstersGetTests extends LabyrinthAPITest
{
	@BeforeTest
	public void setup()
	{
		super.startup();
		System.out.println("STARTING MONSTERS GET TESTS");
		gamesClient = new GamesClient(email, password1);
		tilesClient = new TilesClient(email, password1);
		monstersClient = new MonstersClient(email, password1);
	}
	
	@Test
	public void getMonster()
	{
		MonstersVerifier verifier = new MonstersVerifier();
		// make game
		String game = gamesClient.createGame();
		JsonObject gamesObj = gson.fromJson(game, JsonObject.class);
		
		//get mapId
		int mapId = gamesObj.get("mapIds").getAsJsonArray().get(0).getAsInt();
		
		// get first tile containing monster
		String tiles = tilesClient.getTilesForMap(mapId);
		JsonArray tilesObj = gson.fromJson(tiles, JsonArray.class);
		int tileId = 0;
		for(JsonElement t: tilesObj)
		{
			if(((JsonObject)t).get("hasMonster").getAsBoolean())
			{
				tileId = ((JsonObject)t).get("id").getAsInt();
			}
		}
		
		// verify retrieved monster is valid
		String monster = monstersClient.getMonsterForTile(tileId);
		JsonObject monstersObj = gson.fromJson(monster, JsonObject.class);
		assertTrue(verifier.verifyMonster(monster), verifier.getErrorsAsString());

		// verify both monsters endpoints return the same object
		String monsterTwo = monstersClient.getMonster(monstersObj.get("id").getAsInt());
		assertEquals(monster, monsterTwo, "The monster objects do not appear to be the same");
		
		gamesClient.deleteGame(gamesObj.get("id").getAsInt());
	}
	
	@Test
	public void getMonsterWithCrossTenantUser()
	{
		// create second user
		String firstName = faker.getFirstName();
		String lastName = faker.getLastName();
		String email = firstName + "@" + lastName + ".corn";
		String password = faker.getPassword();
		String data = "{firstName: " + firstName + ", lastName: " + lastName + ", email: " + email + ", password: " + password + "}";
		userClient.createUser(data);
		
		// monsters client with other user
		MonstersClient monstersTwo = new MonstersClient(email, password);
		
		// create game and get mapId
		String game = gamesClient.createGame();
		JsonObject gamesObj = gson.fromJson(game, JsonObject.class);
		int mapId = gamesObj.get("mapIds").getAsJsonArray().get(0).getAsInt();
		
		// get tiles for map and find first tile with monster
		String tiles = tilesClient.getTilesForMap(mapId);
		JsonArray tilesObj = gson.fromJson(tiles, JsonArray.class);
		int tileId = 0;
		for(JsonElement t: tilesObj)
		{
			if(((JsonObject)t).get("hasMonster").getAsBoolean())
			{
				tileId = ((JsonObject)t).get("id").getAsInt();
			}
		}
		
		// get monster using cross-tenant user
		String monster = monstersTwo.getMonsterForTile(tileId);
		String message = "There was no Monster found with that Tile ID";
		assertTrue(monster.contains(message), "Did not get the expected response:\n"
				+ "\nExpected: " + message
				+ "\nRecieved: " + monster);
		
		gamesClient.deleteGame(gamesObj.get("id").getAsInt());
	}
	
	@Test
	public void getMonsterWithNoIdString()
	{
		String monster = monstersClient.getMonster("");
		String message = "There has to be a Monster ID to get the Monster";
		assertTrue(monster.contains(message), "Did not get the expected response:\n"
				+ "\nExpected: " + message
				+ "\nRecieved: " + monster);
	}
	
	@Test
	public void getMonsterWithNullIdString()
	{
		String nullStr = null;
		String monster = monstersClient.getMonster(nullStr);
		String message = "There has to be a Monster ID to get the Monster";
		assertTrue(monster.contains(message), "Did not get the expected response:\n"
				+ "\nExpected: " + message
				+ "\nRecieved: " + monster);
	}
	
	@Test
	public void getMonsterWithNoIdInteger()
	{
		String monster = monstersClient.getMonster(0);
		String message = "There has to be a Monster ID to get the Monster";
		assertTrue(monster.contains(message), "Did not get the expected response:\n"
				+ "\nExpected: " + message
				+ "\nRecieved: " + monster);
	}

	@Test
	public void getMonsterWithInvalidIdString()
	{
		String monster = monstersClient.getMonster("qweasd");
		String message = "There has to be a Monster ID to get the Monster";
		assertTrue(monster.contains(message), "Did not get the expected response:\n"
				+ "\nExpected: " + message
				+ "\nRecieved: " + monster);
	}
	
	@Test
	public void getMonsterWithInvalidIdInteger()
	{
		String monster = monstersClient.getMonster(-2);
		String message = "There was no Monster found with that ID";
		assertTrue(monster.contains(message), "Did not get the expected response:"
				+ "\nExpected: " + message
				+ "\nRecieved: " + monster);
	}

	@Test
	public void getMonsterFromTileWithNoIdString()
	{
		String monster = monstersClient.getMonsterForTile("");
		String message = "You need to give me an ID if you want to get the Monster for that Tile";
		assertTrue(monster.contains(message), "Did not get the expected response:\n"
				+ "\nExpected: " + message
				+ "\nRecieved: " + monster);
	}
	
	@Test
	public void getMonsterFromTileWithNullIdString()
	{
		String nullStr = null;
		String monster = monstersClient.getMonsterForTile(nullStr);
		String message = "You need to give me an ID if you want to get the Monster for that Tile";
		assertTrue(monster.contains(message), "Did not get the expected response:\n"
				+ "\nExpected: " + message
				+ "\nRecieved: " + monster);
	}
	
	@Test
	public void getMonsterFromTileWithNoIdInteger()
	{
		String monster = monstersClient.getMonsterForTile(0);
		String message = "You need to give me an ID if you want to get the Monster for that Tile";
		assertTrue(monster.contains(message), "Did not get the expected response:\n"
				+ "\nExpected: " + message
				+ "\nRecieved: " + monster);
	}

	@Test
	public void getMonsterFromTileWithInvalidIdString()
	{
		String monster = monstersClient.getMonsterForTile("qweasd");
		String message = "You need to give me an ID if you want to get the Monster for that Tile";
		assertTrue(monster.contains(message), "Did not get the expected response:\n"
				+ "\nExpected: " + message
				+ "\nRecieved: " + monster);
	}
	
	@Test
	public void getMonsterFromTileWithInvalidIdInteger()
	{
		String monster = monstersClient.getMonsterForTile(-2);
		String message = "There was no Monster found with that Tile ID";
		assertTrue(monster.contains(message), "Did not get the expected response:"
				+ "\nExpected: " + message
				+ "\nRecieved: " + monster);
	}
}
