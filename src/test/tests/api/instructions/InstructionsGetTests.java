package test.tests.api.instructions;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.labyrinth.client.InstructionsClient;

import test.parents.LabyrinthAPITest;

public class InstructionsGetTests extends LabyrinthAPITest
{
	@BeforeTest
	public void setup()
	{
		System.out.println("STARTING INSTRUCTIONS GET TESTS");
		instructionsClient = new InstructionsClient();
	}
	
	@Test
	public void getInstructions()
	{
		InstructionsVerifier verifier = new InstructionsVerifier();
		String instructions = instructionsClient.getInstructions();
		assertTrue(verifier.verifyInstructions(instructions), verifier.getErrorsAsString());
	}
	
	@Test
	public void testEndpoints()
	{
		String instructions = instructionsClient.getInstructions();
		JsonArray endpoints = gson.fromJson(instructions, JsonObject.class).get("endpoints").getAsJsonArray();
		endpoints.get(0).getAsString();
		
		for(JsonElement endpoint: endpoints)
		{
			String ep = endpoint.getAsString().replace("api/", "");
			String result = instructionsClient.makeArbitraryAPICall(ep, "options");
			if(!ep.equals("constants") &&
					!ep.contains("instructions") &&
					!ep.equals("turns") &&
					!ep.equals("turns/game"))
			{
				assertFalse(result.contains("message"), "Looks like the " + ep + " endpoint doesn't support the Options method\n");
			}
		}
	}
}
