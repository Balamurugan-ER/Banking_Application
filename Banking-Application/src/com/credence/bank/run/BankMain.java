/**
 * 
 */
package com.credence.bank.run;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.credence.bank.authentication.Authentication;
import com.credence.bank.info.UserInfo;
import com.credence.bank.routes.*;
import com.credence.bank.util.BMException;
import com.credence.bank.util.Credence;

/**
 * @author Balamurugan
 *
 */
public class BankMain 
{
	boolean authenticated;
	boolean admin;
	int userId;
//	public static FileHandler fileHandler;
//	public static Logger logFile = Logger.getLogger(BankMain.class.getName());
	public static Logger logConsole = Logger.getLogger(BankMain.class.getName());
	private static Scanner scanner = new Scanner(System.in);
	private boolean doAuthentication() throws BMException
	{
		if(authenticated == true)
		{
			return authenticated;
		}
		try 
		{
			System.out.println("---------------LOGIN TO CRENDENCE BANK-------------------");
			System.out.println("Enter UserId    : ");
			userId = Integer.parseInt(scanner.next());
			System.out.println("Enter EmailId   : ");
			String email = scanner.next();
			System.out.println("Enter Password  : ");
			String password = scanner.next();
			Authentication auth = new Authentication();
			authenticated = auth.login(userId, email, password);
			if(!authenticated)
			{
				throw new BMException("Invalid Credentials");
			}
			BankingRouterProvider bankingRouterProvider = new BankingRouterProvider(userId);
			UserInfo currentUser = bankingRouterProvider.getProfileInfo(userId);
			String adminAccess = currentUser.getAdminAccess();
			if(authenticated)
			{
				if(adminAccess.equals(Credence.Access.USER.getAccess()))
				{
					admin = false;
				}
				if(adminAccess.equals(Credence.Access.ADMIN.getAccess()))
				{
					admin = true;
				}
			}
		}
		catch(NumberFormatException e)
		{
			throw new BMException("Invalid Input",e);
		}
		catch (BMException e) 
		{
			throw new BMException(e.getMessage());
		}
		catch(InputMismatchException e)
		{
			throw new BMException("Invalid Input",e);
		}
		return authenticated;

	}
	private void activateUser() throws BMException
	{
		logConsole.log(Level.INFO, "To ReActivate your user Account \nEnter UserId : ");
		try
		{
			int userId = Integer.parseInt(scanner.next());
			UserRouter user = new BankingRouterProvider(userId);
			user.reActivateUserRequest(userId);
			System.out.println("Your Will be Reactivated Soon....");
		}
		catch(NumberFormatException e)
		{
			throw new BMException("Invalid Input",e);
		}		
	}
	public static void main(String[] args) 
	{
		BankMain banking = new BankMain();
		try 
		{
			banking.doAuthentication();
			if(banking.admin)
			{
				AdminPage admin = new AdminPage();
				admin.adminPage(banking.authenticated,scanner,banking.userId);
			}
			else
			{
				UserPage user = new UserPage();
				user.userPage(banking.userId,banking.authenticated,scanner);
			}
		} 
		catch (BMException e) 
		{
			String message = e.getMessage();
			logConsole.log(Level.SEVERE, message, e.getCause());
			if(message.equals("Session Time Expired Login Again") 
					|| message.equals("Invalid Input")
					|| message.equals("Invalid Email")
					|| message.equals("Invalid password")
					|| message.equals("User not found")
					|| message.equals("Invalid Credentials"))
			{
				main(null);
			}
			if(message.equals("InActive User Found Contact Administrator"))
			{
				try 
				{
					banking.activateUser();
				}
				catch (BMException e1) 
				{
					e1.printStackTrace();
				}
			}
		}	
		
	}
}
