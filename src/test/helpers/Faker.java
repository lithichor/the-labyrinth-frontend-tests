package test.helpers;

import test.models.RandomStrings;

/**
 * Some methods to abstract the behavior of making
 * names, emails, and passwords
 * 
 * @return
 */
public class Faker extends RandomStrings
{
//	private RandomStrings rs = new RandomStrings();
	
	public String getFirstName()
	{
		return oneWord();
	}
	
	public String getLastName()
	{
		return oneWord();
	}
	
	public String getEmail()
	{
		return oneWord() + "@" + oneWord() + ".corn";
	}
	
	public String getPassword()
	{
		return oneWord() + oneWord() + "1Qq";
	}

}
