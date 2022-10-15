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
public class Authentication
{
	private  long loginSessionTime;
	private boolean admin;
	private int userID;
	private void validate(Integer userId,String email, String password,UserInfo user) throws BMException
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
		if(user == null)
		{
			throw new BMException("User not found");
		}
		String adminAccess = user.getAdminAccess();
		if(adminAccess.equals("admin"))
		{
			this.validateAdmin();
		}
		String status = user.getStatus();
		if(status.equals("Inactive"))
		{
			throw new BMException("InActive User Found Contact Administrator");
		}

	}
	public boolean login(Integer userId,String email, String password) throws BMException 
	{
		Storage banking = Utilities.INST.getStorage();
		UserInfo user = banking.getUserInfo(userId);
		String orgEmail = user.getEmail();
		String orgPassword = user.getPassword();
		int orgUserId = user.getUserId();
		String adminAccess = user.getAdminAccess();
		validate(userId, email, password, user);
		if(email.equals(orgEmail) && password.equals(orgPassword) 
				&& userId.equals(orgUserId))
		{
			this.userID = userId;
			this.addSession(userId,adminAccess);
			return true;
		}
		return false;
	}
	private void addSession(Integer userId,String adminAccess) throws BMException
	{
		this.loginSessionTime = System.currentTimeMillis();
		SessionInfo session = new SessionInfo();
		session.setTime(loginSessionTime);
		session.setUserId(this.userID);
		session.setRole(adminAccess);
		Session.INST.addEntry(this.userID, session);
	}
	public void validateAdmin()
	{
		this.admin = true;
	}
	public boolean isAdmin(int userId)
	{
		return Session.INST.isAdmin(userId);
	}
	public boolean isAuthenticated(int userId) throws BMException
	{
		SessionInfo session = Session.INST.getEntryAuth(userId);
		long loginTime = session.getTime();
		doAuthentication(loginTime);
		session.setTime(System.currentTimeMillis());
		return true;
	}
	private void doAuthentication(long loginTime) throws BMException
	{
		long difference = 60000;
		long currentTime = System.currentTimeMillis();
		if(currentTime - loginTime > difference)
		{
			throw new BMException("Session Time Expired Login Again");
		}
	}
	
}
