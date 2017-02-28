package test.tests.api.turns;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.google.gson.JsonObject;
import com.labyrinth.client.GamesClient;
import com.labyrinth.client.TurnsClient;

public class TurnsPutTests extends TurnsApiTest
{
	@BeforeTest
	public void setup()
	{
		super.startup();
		gamesClient = new GamesClient(email, password1);
		turnsClient = new TurnsClient(email, password1);
	}
	
	@Test
	public void updateTurn()
	{
		TurnsVerifier verifier = new TurnsVerifier();
		String game = gamesClient.createGame();
		int gameId = gson.fromJson(game, JsonObject.class).get("id").getAsInt();
		
		String turn = turnsClient.getTurnForGame(gameId);
		int turnId = gson.fromJson(turn, JsonObject.class).get("id").getAsInt();
		
		String newTurn = turnsClient.updateTurn(turnId, createTurnsData());
		
		assertTrue(verifier.updateTurn(newTurn), verifier.getErrorsAsString());
		
		gamesClient.deleteGame(gameId);
	}
	
	@Test
	public void updateTurnWithNoData()
	{
		String game = gamesClient.createGame();
		int gameId = gson.fromJson(game, JsonObject.class).get("id").getAsInt();
		String turn = turnsClient.getTurnForGame(gameId);
		int turnId = gson.fromJson(turn, JsonObject.class).get("id").getAsInt();
		
		String newTurn = turnsClient.updateTurn(turnId, "");
		String message = "You have to give me some data to work with";
		
		gamesClient.deleteGame(gameId);
		assertTrue(newTurn.contains(message), "Did not get the error message we expected:"
				+ "\nResponse from Server: " + newTurn
				+ "Expected Message: " + message + "\n");
	}
	
//	@Test
	public void updateTurnWithHashForString()
	{
		String game = gamesClient.createGame();
		int gameId = gson.fromJson(game, JsonObject.class).get("id").getAsInt();
		String turn = turnsClient.getTurnForGame(gameId);
		int turnId = gson.fromJson(turn, JsonObject.class).get("id").getAsInt();
		
		String newTurn = turnsClient.updateTurn(turnId, "{direction: {a: bc}}");
		String message = "";
		System.out.println(newTurn);

		gamesClient.deleteGame(gameId);
		assertTrue(newTurn.contains(message), "Did not get the error message we expected:"
				+ "\nResponse from Server: " + newTurn
				+ "Expected Message: " + message + "\n");
	}
	
//	@Test
	public void updateTurnWithArrayForString()
	{
		String game = gamesClient.createGame();
		int gameId = gson.fromJson(game, JsonObject.class).get("id").getAsInt();
		String turn = turnsClient.getTurnForGame(gameId);
		int turnId = gson.fromJson(turn, JsonObject.class).get("id").getAsInt();
		
		String newTurn = turnsClient.updateTurn(turnId, "{direction: [a, b, c]}");
		String message = "";
		System.out.println(newTurn);
		
		gamesClient.deleteGame(gameId);
		assertTrue(newTurn.contains(message), "Did not get the error message we expected:"
				+ "\nResponse from Server: " + newTurn
				+ "Expected Message: " + message + "\n");
	}
	
	@Test
	public void updateTurnWithCrossTenantId()
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
				+ "Expected Message: " + message + "\n");		
	}
	
//	@Test
	public void updateTurnWithAlphaForID()
	{
		// change the ID to a string after API Client #35
		String turn = turnsClient.updateTurn(1, createTurnsData());
		String message = "There is no turn matching that ID";
		
		System.out.println(turn);
		
		assertTrue(turn.contains(message), "Did not get the error message we expected:"
				+ "\nResponse from Server: " + turn
				+ "Expected Message: " + message + "\n");		
	}
}
