/**
 * 
 */
package com.credence.bank.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
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
	public void check(String className) throws BMException
	{		
		int passedCount=0,failedCount=0;
		try 
		{
//			Class classObject = Class.forName("com.bm.framework.String.BeginnerString");
			Class<?> classObject = Class.forName(className);
			Object obj = classObject.newInstance();
			Method[] methodObject = classObject.getDeclaredMethods();

			for(Method methodIterator : methodObject)
			{
				Parameter[] parameter = methodIterator.getParameters();
				Object[] paramValues = new Object[parameter.length];

				int i = 0;
				System.out.println(methodIterator.getName());
				for(Parameter param : parameter)
				{
					if(param.getType().isPrimitive())
					{
						if(param.getType().toString().equals("boolean"))
						{
							paramValues[i++] = false;
						}
						if(param.getType().toString().equals("int"))
						{
							paramValues[i++] = 0;
						}
						if(param.getType().toString().equals("char"))
						{
							paramValues[i++]='a';
						}
					}
					else
					{
						paramValues[i++] = null;
					}
				}			
				methodIterator.setAccessible(true);
				try {
					methodIterator.invoke(obj,paramValues);
				} 
				catch (InvocationTargetException e) {
					if(e.getCause() instanceof BMException)
					{
						passedCount++;
						System.out.println("passed "+methodIterator.getName());
					}
					else
					{
						failedCount++;
						System.out.println("failed "+methodIterator.getName());
					}
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		System.out.println(passedCount+" Cases Passed");
		System.out.println(failedCount+" Failed Cases");
	}

}
