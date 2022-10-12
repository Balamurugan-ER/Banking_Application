/**
 * 
 */
package com.credence.bank.run;

import java.io.Console;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.credence.bank.authentication.Authentication;
import com.credence.bank.banking.DBBanking;
import com.credence.bank.banking.FBanking;
import com.credence.bank.info.UserInfo;
import com.credence.bank.routes.*;
import com.credence.bank.util.BMException;
import com.credence.bank.util.Utilities;

/**
 * @author Balamurugan
 *
 */
public class BankMain 
{
	boolean authenticated;
	boolean admin;
	public static FileHandler fileHandler;
	public static Logger logFile = Logger.getLogger(BankMain.class.getName());
	public static Logger logConsole = Logger.getLogger(BankMain.class.getName());
	private static Scanner scanner = new Scanner(System.in);
	private boolean doAuthentication() throws BMException
	{
		if(authenticated == true)
		{
			return authenticated;
		}

		System.out.println("Enter UserId    : ");
		int userId = scanner.nextInt();
		System.out.println("Enter EmailId   : ");
		String email = scanner.next();
		System.out.println("Enter Password  : ");
		String password = scanner.next();
		BankMain banking = new BankMain();
		try 
		{
			Authentication auth = new Authentication();
			authenticated = auth.login(userId, email, password);
			UserInfo currentUser = BankingRouterProvider.INST.getProfileInfo(userId);
			String role = currentUser.getAdminAccess();
			if(role.equals("user"))
			{
				admin = false;
			}
			if(role.equals("admin"))
			{
				admin = true;
			}
		}
		catch (BMException e) 
		{
			e.printStackTrace();
			logConsole.log(Level.SEVERE, "{0}",e.getMessage());
		}
		return authenticated;

	}
	
	public static void main(String[] args) 
	{
		BankMain banking = new BankMain();
		try 
		{
			banking.doAuthentication();
		} 
		catch (BMException e) 
		{
			e.printStackTrace();
		}
		if(banking.admin)
		{
			AdminPage admin = new AdminPage();
			admin.adminPage(banking.authenticated,scanner);
		}
		else
		{
			UserPage user = new UserPage();
			user.userPage(banking.authenticated,scanner);
		}
	}
}
