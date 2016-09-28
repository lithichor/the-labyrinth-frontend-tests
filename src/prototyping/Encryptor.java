package prototyping;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;

import sun.misc.BASE64Encoder;

public class Encryptor
{
	private static Encryptor instance = null;
	private MessageDigest md = null;

	private Encryptor(){}
	
	public synchronized String encrypt(String text)
	{
		try
		{
			md = MessageDigest.getInstance("SHA");
			md.update(text.getBytes("UTF-8"));
		}
		catch(NoSuchAlgorithmException nsae)
		{
			System.out.println("Error getting encryption algorithm: " + nsae.getMessage());
			nsae.printStackTrace();
		}
		catch(UnsupportedEncodingException uee)
		{
			System.out.println("Error encoding string: " + uee.getMessage());
			uee.printStackTrace();
		}
		
		byte raw[] = md.digest();
		String hash = (new BASE64Encoder()).encode(raw);
		
		return hash;
	}
	
	public synchronized String decrypt(String text)
	{
		// http://stackoverflow.com/a/10319155
		return StringUtils.newStringUtf8(Base64.decodeBase64(text));
	}

	public synchronized String encrypt64(String username, String password)
	{
		// same Stackoverflow post as above
		byte[] strAsBytes = StringUtils.getBytesUtf8(username + ":" + password);
		return Base64.encodeBase64String(strAsBytes);
	}

	public static Encryptor getInstance()
	{
		if(Encryptor.instance == null)
		{
			instance = new Encryptor();
		}
		return Encryptor.instance;
	}
}
