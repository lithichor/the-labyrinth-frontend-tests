package test.models;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

// https://www.google.com/search?q=how+to+write+rest+api+client&ie=utf-8&oe=utf-8 
// https://docs.oracle.com/cd/E24329_01/web.1211/e24983/client.htm#RESTF150
// http://hc.apache.org/httpcomponents-client-ga/quickstart.html
public class APIClient
{
	public APIClient() throws ClientProtocolException, IOException
	{
		CloseableHttpClient client = HttpClients.createDefault();
		HttpGet get = new HttpGet("localhost:8080/TheLabyrinth/api/games/10");
		CloseableHttpResponse response = client.execute(get);
		
		System.out.println(response.getStatusLine());
	    HttpEntity entity1 = response.getEntity();
	    // do something useful with the response body
	    // and ensure it is fully consumed
	    EntityUtils.consume(entity1);
	    
	    response.close();
	}
}
