package test.tests.api.turns;

import test.parents.LabyrinthAPITest;

public class TurnsApiTest extends LabyrinthAPITest
{
	protected String createTurnsData()
	{
		String data = "{direction: ";
		switch(rand.nextInt(12))
		{
		case 0:
			data += "n";
			break;
		case 1:
			data += "e";
			break;
		case 2:
			data += "s";
			break;
		case 3:
			data += "w";
			break;
		case 4:
			data += "N";
			break;
		case 5:
			data += "E";
			break;
		case 6:
			data += "S";
			break;
		case 7:
			data += "W";
			break;
		case 8:
			data += "nORth";
			break;
		case 9:
			data += "East";
			break;
		case 10:
			data += "SOUTH";
			break;
		case 11:
			data += "west";
			break;
		}
		data += "}";
		
		return data;
	}
}
