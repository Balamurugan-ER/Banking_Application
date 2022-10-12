/**
 * 
 */
package com.credence.bank.authentication;

import com.credence.bank.banking.Storage;
import com.credence.bank.info.UserInfo;
import com.credence.bank.util.BMException;
import com.credence.bank.util.Utilities;

/**
 * @author Balamurugan
 *
 */
public class Authentication 
{
	public boolean login(Integer userId,String email, String password) throws BMException 
	{
		Utilities.INST.isNull(userId);
		boolean emailVerification = Utilities.INST.isEmail(email);
		if(!emailVerification)
		{
			throw new BMException("Invalid Email");
		}
		boolean passwordVerification = Utilities.INST.isPassword(password);
		if(!passwordVerification)
		{
			throw new BMException("Invalid password");
		}
		Storage banking = Utilities.INST.getStorage();
		UserInfo user = banking.getUserInfo(userId);
		if(user == null)
		{
			throw new BMException("User not found");
		}
		String orgEmail = user.getEmail();
		String orgPassword = user.getPassword();
		if(email.equals(orgEmail) && password.equals(orgPassword))
		{
			return true;
		}
		return false;
	}
}
