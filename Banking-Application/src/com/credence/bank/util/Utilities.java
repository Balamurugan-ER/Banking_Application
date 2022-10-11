/**
 * 
 */
package com.credence.bank.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.regex.Pattern;

import com.credence.bank.banking.Storage;

/**
 * @author Balamurugan
 *
 */
public enum Utilities 
{
	INST;
	Storage banking;
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
	public Storage getStorage() throws BMException
	{
		String className = EnvProperties.INST.envProps.getProperty("storage");
		try 
		{	
			Class<?> classObj = Class.forName(className);
			Constructor<?>[] constObj = classObj.getDeclaredConstructors();
			for(Constructor con : constObj)
			{
				banking = (Storage) con.newInstance(constObj);
			}
		}
		catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
				IllegalArgumentException | InvocationTargetException e ) 
		{
			throw new BMException("Implementation Class not found",e);
		}
		
		return banking;
	}

}
