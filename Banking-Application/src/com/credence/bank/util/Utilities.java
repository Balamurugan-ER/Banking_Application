/**
 * 
 */
package com.credence.bank.util;

import java.util.regex.Pattern;

/**
 * @author Balamurugan
 *
 */
public enum Utilities 
{
	INST;
	public void isNull(Object obj) throws BMException
	{
		if(obj == null)
		{
			throw new BMException("Null and Empty Not Allowed");
		}
	}
	public boolean isEmail(String email) throws BMException
	{
		String emailPattern = "^[a-zA-Z0-9]*@{1}[a-zA-Z0-9]*[\\.][\\.a-zA-Z0-9]+";
		return Pattern.matches(emailPattern, email);
	}
	public boolean isPassword(String password) throws BMException
	{
		String passwordPattern = ".{8,25}";
		return Pattern.matches(passwordPattern, password);
	}
}
