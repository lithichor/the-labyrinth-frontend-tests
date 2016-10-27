package test.parents;

import java.util.ArrayList;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.HttpClients;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

import com.google.gson.Gson;

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
	
	@BeforeSuite
	public void startup()
	{
		System.out.println("STARTING TESTS ...\n\n");
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
