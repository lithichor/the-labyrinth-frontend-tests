package test.parents;

import java.util.ArrayList;
import java.util.Random;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.HttpClients;
import org.testng.annotations.BeforeSuite;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.labyrinth.client.GamesClient;
import com.labyrinth.client.HerosClient;
import com.labyrinth.client.UserClient;

import test.helpers.Faker;
import test.models.RandomStrings;

public abstract class LabyrinthAPITest
{
	protected boolean debug = false;
	
	protected String username = "eric@eric.corn";
	protected String password = "1qweqwe";

	protected String baseUrl = "http://localhost:8080/TheLabyrinth";
	protected String responseString = "";

	protected HttpClient request = HttpClients.createDefault();
	protected CloseableHttpResponse response = null;
	protected Gson gson = new Gson();
	protected RandomStrings random = new RandomStrings();
	protected Faker faker = new Faker();
	protected Random rand = new Random();
	
	// clients
	protected HerosClient herosClient;
	protected UserClient userClient;
	protected GamesClient gamesClient;
	
	// user
	protected String firstName = faker.getFirstName();
	protected String lastName = faker.getLastName();
	protected String email = firstName + "@" + lastName + ".corn";
	protected String password1 = faker.getPassword();
	private String data = "{firstName: " + firstName + ","
			+ "lastName: " + lastName + ","
			+ "email: " + email + ","
			+ "password: " + password1 + ","
			+ "}";
	protected JsonObject userObj;

	@BeforeSuite
	public void startup()
	{
		System.out.println("STARTING TESTS ...\n\n");
		
		// create a user for the test suite
		userObj = gson.fromJson(new UserClient(email, password1).createUser(data), JsonObject.class);
	}
	
	// for a single failure message
	protected void fail(String message)
	{
		throw new RuntimeException(message);
	}
	
	// for failure with the errors array
	protected void fail(ArrayList<String> messages)
	{
		String allMessages = "";
		
		for(String message: messages)
		{
			allMessages += message + "\n";
		}
		
		throw new RuntimeException(allMessages);
	}
	
	// for a custom message with the errors array
	protected void fail(String message, ArrayList<String> messages)
	{
		String allMessages = message;
		
		for(String m: messages)
		{
			allMessages += m + "\n";
		}
		
		throw new RuntimeException(allMessages);
	}
}
