package test.helpers;

import test.models.RandomStrings;

/**
 * Some methods to abstract the behavior of making
 * names, emails, and passwords
 * 
 * @return
 */
public class Faker
{
	private RandomStrings rs = new RandomStrings();
	
	public String getFirstName()
	{
		return rs.oneWord();
	}
	
	public String getLastName()
	{
		return rs.oneWord();
	}
	
	public String getPassword()
	{
		return rs.sentence(2) + "1Qq";
	}

}
