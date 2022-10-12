/**
 * 
 */
package com.credence.bank.run;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.credence.bank.info.UserInfo;
import com.credence.bank.routes.BankingRouterProvider;
import com.credence.bank.routes.UserRouter;
import com.credence.bank.util.BMException;

/**
 * @author Balamurugan
 *
 */
public class UserPage 
{
	public static Logger logConsole = Logger.getLogger(UserPage.class.getName());
	private static Scanner scan;
	public void userPage(boolean authenticated,Scanner scanner)
	{
		scan = scanner;
		boolean session = false;
		UserRouter userRouter = BankingRouterProvider.INST;

		logConsole.log(Level.INFO,"Welcome User\n"
				+ "1.Profile Details\n"
				+ "2.Create new User\n"
				+ "3.Create new Bank Account\n"
				+ "4.Deactivate User Account\n"
				+ "5.check Balance\n"
				+ "6.with Draw\n"
				+ "7.self Transfer\n"
				+ "8.others Transfer\n"
				+ "9.self Deposit\n"
				+ "10.others Deposit\n"
				+ "11.List All MyAccounts Information\n"
				+ "12.MyAccount Information\n"
				+ "13.Deactive My Account\n"
				+ "14.update Mobile Number\n"
				+ "15.update Email\n"
				+ "16.update Aadhar\n"
				+ "17.update Name\n"
				+ "18.update Role\n"
				+ "19.update Password\n"
				+ "20.change City\n"
				+ "21.change Type\n"
				+ "22.change AtmPin\n"
				+ "23.Check Transaction Information\n"
				+ "");
		if(authenticated)
		{
			session = true;
		}
		while(session)
		{

			logConsole.log(Level.INFO, "Enter Your Wish : ");
			int operation = scan.nextInt();
			switch(operation)
			{
			case 1:
			{
				logConsole.log(Level.INFO, "Enter Your UserId : ");
				int userId = scan.nextInt();
				try 
				{
					UserInfo user = userRouter.getProfileInfo(userId);
					logConsole.log(Level.INFO,"{0}" ,user);
				}
				catch (BMException e) 
				{
					logConsole.log(Level.SEVERE, "{0}",e.getMessage());
				}
				break;
			}
			case 2:
			{
				UserInfo userInfo = new UserInfo();
				logConsole.log(Level.INFO, "");
				
				
			}
			case 0:
			{
				session = false;
				break;
			}
			default:
			{
				logConsole.log(Level.INFO, "Invalid Operation Id");
				break;
			}
			}
		}
	}
}
