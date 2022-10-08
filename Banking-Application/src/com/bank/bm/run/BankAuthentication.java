/**
 * 
 */
package com.bank.bm.run;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.bank.bm.authentication.Authentication;
import com.bank.bm.util.BMException;

/**
 * @author Balamurugan
 *
 */
public class BankAuthentication {

	private boolean authenticated;
	
	public boolean isAuthenticated() {
		return authenticated;
	}
	static Logger logConsole = Logger.getLogger(BankAuthentication.class.getName());
	private static int login(String username,String password) throws BMException
	{
		Authentication.addDumpUsers();
		return Authentication.login(username, password);
	}
	public int loginPage() 
	{
		if(authenticated)
		{
			logConsole.log(Level.INFO, "Already LoggedIn");
			return 400;
		}
		Scanner scan = new Scanner(System.in);
		logConsole.log(Level.INFO,"Enter UserId & Enter Password");
		String username = scan.next();
		String password = scan.next();
		int response=450;
		try 
		{
			response = BankAuthentication.login(username, password);
		}
		catch (BMException e) 
		{
			logConsole.log(Level.SEVERE, "{0}",e);
		}
		if(response == 200)
		{
			authenticated = true;
			logConsole.log(Level.INFO, "User Authenticated");
		}
		else
		{
			loginPage();
		}
		scan.close();
		return response;
	}

}
