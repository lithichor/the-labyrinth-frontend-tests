package test.parents;

import java.util.ArrayList;

import com.google.gson.Gson;

public class LabyrinthAPITestVerifier
{
	protected ArrayList<String> errors = new ArrayList<String>();
	protected Gson gson = new Gson();
	
	public ArrayList<String> getErrors()
	{
		return errors;
	}
}
