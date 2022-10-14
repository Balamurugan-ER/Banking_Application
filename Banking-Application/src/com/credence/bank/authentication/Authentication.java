/**
 * 
 */
package com.credence.bank.authentication;

import com.credence.bank.banking.Storage;
import com.credence.bank.info.SessionInfo;
import com.credence.bank.info.UserInfo;
import com.credence.bank.util.BMException;
import com.credence.bank.util.Session;
import com.credence.bank.util.Utilities;

/**
 * @author Balamurugan
 *
 */
public class Authentication implements Cloneable
{
	private  long loginSessionTime;
	private boolean admin;
	private int userID;
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
		int orgUserId = user.getUserId();
		String status = user.getStatus();
		String adminAccess = user.getAdminAccess();
		if(adminAccess.equals("admin"))
		{
			this.isAdmin();
		}
		if(status.equals("Inactive"))
		{
			throw new BMException("InActive User Found Contact Administrator");
		}
		if(email.equals(orgEmail) 
				&& password.equals(orgPassword) 
				&& userId.equals(orgUserId))
		{
			this.userID = userId;
			this.authenticated(userId,adminAccess);
			return true;
		}
		return false;
	}
	private void authenticated(Integer userId,String role) throws BMException
	{
		this.loginSessionTime = System.currentTimeMillis();
		SessionInfo session = new SessionInfo();
		session.setTime(loginSessionTime);
		session.setUserId(this.userID);
		session.setRole(role);
		Session.INST.addEntry(this.userID, session);
	}
	public boolean isAdmin()
	{
		this.admin = true;
		return this.admin;
	}
	public boolean isAuthenticated() throws BMException
	{
		long difference = 300000;
		long currentTime = System.currentTimeMillis();
		if(currentTime - this.loginSessionTime > difference)
		{
			throw new BMException("Session Time Expired Login Again");
		}
		return true;
	}
	
}
