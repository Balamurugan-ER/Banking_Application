/**
 * 
 */
package com.credence.bank.authentication;

import java.util.HashMap;

import com.credence.bank.util.BMException;
import com.credence.bank.util.Utilities;

/**
 * @author Balamurugan
 *
 */
public class Authentication 
{
	private static HashMap<String,String> auth = new HashMap<String,String>();
	public static void addDumpUsers()
	{
		auth.put("bala", "123");
		auth.put("test", "123");
		auth.put("Jack", "Jack@123");
		auth.put("admin", "Admin@123");
	}
	public static int login(String username,String password) throws BMException
	{
		Utilities.INST.isNull(password);
		if(password.equals(auth.get(username)))
		{
			return 200;
		}
		return 400;
	}
}
