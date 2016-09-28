package test.parents;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.HttpClients;
import org.testng.annotations.BeforeTest;

import com.google.gson.Gson;

public abstract class LabyrinthAPITest
{
	protected boolean debug = false;
	
	protected String username = "eric@eric.corn";
	protected String password = "1qweqwe";

	protected String baseUrl = "http://localhost:8080/TheLabyrinth";
	protected HttpClient request = HttpClients.createDefault();
	protected CloseableHttpResponse response = null;
	protected Gson gson = new Gson();
	
	@BeforeTest
	public void setup()
	{
		System.out.println("STARTING TESTS ...");
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
	
	protected boolean sendRequest(HttpRequestBase req)
	{
		boolean successful = false;
		
		try
		{
			response = (CloseableHttpResponse) request.execute(req);
			successful = true;
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		
		return successful;
	}
	
	protected String encrypt64(String username, String password)
	{
		// http://stackoverflow.com/a/10319155
		byte[] strAsBytes = StringUtils.getBytesUtf8(username + ":" + password);
		return Base64.encodeBase64String(strAsBytes);
	}
}
