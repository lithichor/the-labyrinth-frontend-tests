package test.parents;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.HttpClients;
import org.testng.annotations.BeforeTest;

import com.google.gson.Gson;

import test.helpers.GamesVerifier;

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
	
	protected GamesVerifier verifier = new GamesVerifier();

	@BeforeTest
	public void setup()
	{
		// TODO: TEST #5 - parse test parameters
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

	protected HttpGet makeGetMethod(String endpoint)
	{
		HttpGet get = new HttpGet(baseUrl + "/api/" + endpoint);
		get.setHeader("authorization", "Basic " + encrypt64(username, password));

		return get;
	}

	protected HttpPost makePostMethod(String endpoint)
	{
		HttpPost post = new HttpPost(baseUrl + "/api/" + endpoint);
		post.setHeader("authorization", "Basic " + encrypt64(username, password));

		return post;
	}

	protected HttpDelete makeDeleteMethod(String endpoint)
	{
		HttpDelete delete = new HttpDelete(baseUrl + "/api/" + endpoint);
		delete.setHeader("authorization", "Basic " + encrypt64(username, password));
		
		return delete;
	}
}
