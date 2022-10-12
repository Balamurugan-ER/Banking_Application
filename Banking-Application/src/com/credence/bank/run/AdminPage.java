/**
 * 
 */
package com.credence.bank.run;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.credence.bank.routes.BankingRouter;
import com.credence.bank.routes.BankingRouterProvider;

/**
 * @author Balamurugan
 *
 */
public class AdminPage 
{
	private static Scanner scan;
	private static Logger logConsole = Logger.getLogger(AdminPage.class.getName());
	public void adminPage(boolean authenticated,Scanner scanner)
	{
		scan = scanner;
		BankingRouter bankingRouter = BankingRouterProvider.INST;
		logConsole.log(Level.INFO,"Welcome Admin\n"
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
				+ "0.Exit");
	}
}
