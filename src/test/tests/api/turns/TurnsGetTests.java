package test.tests.api.turns;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.google.gson.JsonObject;
import com.labyrinth.client.GamesClient;
import com.labyrinth.client.TurnsClient;

public class TurnsGetTests extends TurnsApiTest
{
	@BeforeTest
	public void setup()
	{
		super.startup();
		System.out.println("STARTING TURNS GET TESTS");
		gamesClient = new GamesClient(email, password1);
		turnsClient = new TurnsClient(email, password1);
	}
	
	@Test
	public void getTurn()
	{
		String game = gamesClient.createGame();
		int gameId = gson.fromJson(game, JsonObject.class).get("id").getAsInt();
		
		// get the turn from the gameId
		String turnFromGame = turnsClient.getTurnForGame(gameId);
		// then get the turn from the turnId
		int turnId = gson.fromJson(turnFromGame, JsonObject.class).get("id").getAsInt();
		String turnFromTurn = turnsClient.getTurn(turnId);
		
		assertEquals(turnFromGame, turnFromTurn);
		
		gamesClient.deleteGame(gameId);
	}
	
	@Test
	public void verifyTurnFields()
	{
		TurnsVerifier verifier = new TurnsVerifier();
		String game = gamesClient.createGame();
		int gameId = gson.fromJson(game, JsonObject.class).get("id").getAsInt();
		
		String turn = turnsClient.getTurnForGame(gameId);
		assertTrue(verifier.verifyFields(turn), verifier.getErrorsAsString());
		
		gamesClient.deleteGame(gameId);
	}
	
	@Test
	public void getTurnWithCrossTenantGameId()
	{
		String game = gamesClient.createGame();
		int gameId = gson.fromJson(game, JsonObject.class).get("id").getAsInt();
		
		String[] secondUser = createSecondUser();
		TurnsClient secondTurnsClient = new TurnsClient(secondUser[0], secondUser[1]);
		
		String secondTurn = secondTurnsClient.getTurnForGame(gameId);
		String message = "There is no turn matching that Game ID";
		
		gamesClient.deleteGame(gameId);
		
		assertTrue(secondTurn.contains(message), "Did not get the error message we expected:"
				+ "\nResponse from Server: " + secondTurn
				+ "Expected Message: " + message + "\n**");
	}
	
	@Test
	public void getTurnWithCrossTenantTurnId()
	{
		String game = gamesClient.createGame();
		int gameId = gson.fromJson(game, JsonObject.class).get("id").getAsInt();
		
		String turn = turnsClient.getTurnForGame(gameId);
		int turnId = gson.fromJson(turn, JsonObject.class).get("id").getAsInt();
		
		String[] secondUser = createSecondUser();
		TurnsClient secondTurnsClient = new TurnsClient(secondUser[0], secondUser[1]);
		
		String secondTurn = secondTurnsClient.getTurn(turnId);
		String message = "There is no turn matching that ID";
		
		gamesClient.deleteGame(gameId);
		
		assertTrue(secondTurn.contains(message), "Did not get the error message we expected:"
				+ "\nResponse from Server: " + secondTurn
				+ "Expected Message: " + message + "\n**");
	}

	@Test
	public void getTurnWithNoIdString()
	{
		String turn = turnsClient.getTurn("");
		String message = "There is no turn matching that ID";
		assertTrue(turn.contains(message), "Did not get the expected response:\n"
				+ "\nExpected: " + message
				+ "\nRecieved: " + turn);
	}
	
	@Test
	public void getTurnWithNullIdString()
	{
		String nullStr = null;
		String turn = turnsClient.getTurn(nullStr);
		String message = "There is no turn matching that ID";
		assertTrue(turn.contains(message), "Did not get the expected response:\n"
				+ "\nExpected: " + message
				+ "\nRecieved: " + turn);
	}
	
	@Test
	public void getTurnWithNoIdInteger()
	{
		String turn = turnsClient.getTurn(0);
		String message = "There is no turn matching that ID";
		assertTrue(turn.contains(message), "Did not get the expected response:\n"
				+ "\nExpected: " + message
				+ "\nRecieved: " + turn);
	}

	@Test
	public void getTurnWithNullIdInteger()
	{
		Integer integer = null;
		String turn = turnsClient.getTurn(integer);
		String message = "There is no turn matching that ID";
		assertTrue(turn.contains(message), "Did not get the expected response:\n"
				+ "\nExpected: " + message
				+ "\nRecieved: " + turn);
	}

	@Test
	public void getTurnWithInvalidIdString()
	{
		String turn = turnsClient.getTurn("qweasd");
		String message = "There is no turn matching that ID";
		assertTrue(turn.contains(message), "Did not get the expected response:\n"
				+ "\nExpected: " + message
				+ "\nRecieved: " + turn);
	}
	
	@Test
	public void getTurnWithInvalidIdInteger()
	{
		String turn = turnsClient.getTurn(-2);
		String message = "There is no turn matching that ID";
		assertTrue(turn.contains(message), "Did not get the expected response:"
				+ "\nExpected: " + message
				+ "\nRecieved: " + turn);
	}

	@Test
	public void getTurnFromTileWithNoIdString()
	{
		String turn = turnsClient.getTurnForGame("");
		String message = "There is no turn matching that Game ID";
		assertTrue(turn.contains(message), "Did not get the expected response:\n"
				+ "\nExpected: " + message
				+ "\nRecieved: " + turn);
	}
	
	@Test
	public void getTurnFromTileWithNullIdString()
	{
		String nullStr = null;
		String turn = turnsClient.getTurnForGame(nullStr);
		String message = "There is no turn matching that Game ID";
		assertTrue(turn.contains(message), "Did not get the expected response:\n"
				+ "\nExpected: " + message
				+ "\nRecieved: " + turn);
	}
	
	@Test
	public void getTurnFromTileWithNoIdInteger()
	{
		String turn = turnsClient.getTurnForGame(0);
		String message = "There is no turn matching that Game ID";
		assertTrue(turn.contains(message), "Did not get the expected response:\n"
				+ "\nExpected: " + message
				+ "\nRecieved: " + turn);
	}

	@Test
	public void getTurnFromTileWithNullIdInteger()
	{
		Integer integer = null;
		String turn = turnsClient.getTurnForGame(integer);
		String message = "There is no turn matching that Game ID";
		assertTrue(turn.contains(message), "Did not get the expected response:\n"
				+ "\nExpected: " + message
				+ "\nRecieved: " + turn);
	}

	@Test
	public void getTurnFromTileWithInvalidIdString()
	{
		String turn = turnsClient.getTurnForGame("qweasd");
		String message = "There is no turn matching that Game ID";
		assertTrue(turn.contains(message), "Did not get the expected response:\n"
				+ "\nExpected: " + message
				+ "\nRecieved: " + turn);
	}
	
	@Test
	public void getTurnFromTileWithInvalidIdInteger()
	{
		String turn = turnsClient.getTurnForGame(-2);
		String message = "There is no turn matching that Game ID";
		assertTrue(turn.contains(message), "Did not get the expected response:"
				+ "\nExpected: " + message
				+ "\nRecieved: " + turn);
	}
}
