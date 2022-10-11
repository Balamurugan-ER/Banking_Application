/**
 * 
 */
package com.credence.bank.authentication;

import java.util.HashMap;

import com.credence.bank.banking.Storage;
import com.credence.bank.util.BMException;
import com.credence.bank.util.Utilities;

/**
 * @author Balamurugan
 *
 */
public class Authentication 
{
	public static boolean login(Integer userId,String email,String password) throws BMException
	{
		Utilities.INST.isNull(email);
		Utilities.INST.isNull(password);
		Utilities.INST.isEmail(email);
		Utilities.INST.isPassword(password);
		Storage banking = Utilities.INST.getStorage();
		return banking.login(userId, email, password);
	}
}
