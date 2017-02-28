package test.tests.api.instructions;

import com.google.gson.JsonObject;

import test.parents.LabyrinthAPITestVerifier;

public class InstructionsVerifier extends LabyrinthAPITestVerifier
{
	public boolean verifyInstructions(String inst)
	{
		boolean valid = true;
		JsonObject instructions = gson.fromJson(inst, JsonObject.class);
		
		if(instructions.entrySet().size() > 1)
		{
			errors.add("There are too many fields displayed");
			valid = false;
		}
		if(instructions.entrySet().size() < 1)
		{
			errors.add("There are not enough fields displayed");
			valid = false;
		}
		if(!instructions.has("endpoints"))
		{
			errors.add("The Endpoints field is missing");
			valid = false;
		}
		
		return valid;
	}
}
