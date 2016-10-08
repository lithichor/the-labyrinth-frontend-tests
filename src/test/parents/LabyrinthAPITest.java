package test.parents;

import java.util.ArrayList;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.HttpClients;
import org.testng.annotations.BeforeTest;

import com.google.gson.Gson;

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
	
	@BeforeTest
	public void startup()
	{
		System.out.println("STARTING TESTS ...\n\n");
	}
	
	protected void fail(String message)
	{
		throw new RuntimeException(message);
	}
	
	protected void fail(ArrayList<String> messages)
	{
		String allMessages = "";
		
		for(String message: messages)
		{
			allMessages += message + "\n";
		}
		
		throw new RuntimeException(allMessages);
	}
}
