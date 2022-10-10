/**
 * 
 */
package com.credence.bank.util;

import java.sql.SQLException;

/**
 * @author Balamurugan
 *
 */
public class BMException extends Exception 
{
	private static final long serialVersionUID = 1L;

	public BMException(String message)
	{
		super(message);
	}

	public BMException(String message, Exception e) 
	{
		super(message,e);
	}
}
