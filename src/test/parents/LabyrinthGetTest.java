package test.parents;

import org.apache.http.client.methods.HttpGet;

import test.helpers.GamesVerifier;

public class LabyrinthGetTest extends LabyrinthAPITest
{
	protected GamesVerifier verifier = new GamesVerifier();

	protected HttpGet makeGetMethod(String endpoint)
	{
		HttpGet get = new HttpGet(baseUrl + "/api/" + endpoint);
		get.setHeader("authorization", "Basic " + encrypt64(username, password));

		return get;
	}
}
