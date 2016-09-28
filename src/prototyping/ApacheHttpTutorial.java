package prototyping;

import java.io.IOException;

import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class ApacheHttpTutorial
{
	
	public static void main(String[] args)
	{
		
		HttpGet get = new HttpGet("http://localhost:8080/TheLabyrinth/api/games/10");
		System.out.println(get.getURI());
		
		HttpClient request = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		Encryptor encrypt = Encryptor.getInstance();
		String usernamePassword = encrypt.encrypt64("eric@eric.corn", "1qweqwe").toString();
		
		get.addHeader("authorization", "Basic " + usernamePassword);
		
		try
		{
			response = (CloseableHttpResponse) request.execute(get);
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		
		System.out.println(response.getStatusLine());
		try
		{
			System.out.println(EntityUtils.toString(response.getEntity()));
		}
		catch(ParseException | IOException e1)
		{
			e1.printStackTrace();
		}
		try
		{
			response.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}

}
