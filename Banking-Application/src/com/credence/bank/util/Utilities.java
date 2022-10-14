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
		isNull(email);
		String emailPattern = "^[a-zA-Z0-9]*@{1}[a-zA-Z0-9]*[\\.][\\.a-zA-Z0-9]+";
		return Pattern.matches(emailPattern, email);
	}
	public boolean isPassword(String password) throws BMException
	{
		isNull(password);
		String passwordPattern = ".{8,25}";
		return Pattern.matches(passwordPattern, password);
	}
	public Storage getStorage() throws BMException
	{
		EnvProperties.INST.writingProps();
		String className = EnvProperties.INST.envProps.getProperty("storage");
		try 
		{	
			Class<?> classObj = Class.forName(className);
			Constructor<?>[] constObj = classObj.getDeclaredConstructors();
			for(Constructor con : constObj)
			{
				con.setAccessible(true);
				banking = (Storage) con.newInstance();
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
