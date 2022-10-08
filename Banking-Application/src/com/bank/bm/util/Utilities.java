/**
 * 
 */
package com.bank.bm.util;

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
			throw new BMException("Null Value Not Allowed");
		}
	}
}
