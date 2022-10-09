/**
 * 
 */
package com.credence.bank.run;

import java.io.IOException;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Balamurugan
 *
 */
public class BankMain 
{
	static FileHandler fileHandler;
	static Logger logFile = Logger.getLogger(BankMain.class.getName());
	static Logger logConsole = Logger.getLogger(BankMain.class.getName());
	public static void main(String[] args) 
	{		
		boolean bankingAccess = false;
		try 
		{
			fileHandler  = new FileHandler("BankMain.log");
			logFile.addHandler(fileHandler);
		} 
		catch (SecurityException e1) 
		{
			e1.printStackTrace();
		}
		catch (IOException e1) 
		{
			e1.printStackTrace();
		}
		BankAuthentication userAuth = new BankAuthentication();
		while(!userAuth.isAuthenticated())
		{
			userAuth.loginPage();
			bankingAccess = true;
			break;
		}
		while(bankingAccess)
		{
			logConsole.log(Level.INFO, "Enter Banking Options\n1.Display Info");
			Scanner scan = new Scanner(System.in);
			int choice = scan.nextInt();
			switch(choice)
			{
			case 1:
			{
				
				//cache.addDumpData();
				logConsole.log(Level.INFO, "Enter UserId to Check User Details");
				//Integer uid = scan.nextInt();
				//logConsole.log(Level.INFO, "{0}",cache.getData(uid));
				//logConsole.log(Level.INFO, "{0}",cache.getAccountsInfo(uid));
				break;
			}
			case 0:
			{
				bankingAccess = false;
				break;
			}
			default:
			{
				logConsole.log(Level.INFO, "Invalid Option");
			}
			}
			scan.close();
		}
		
	}

}