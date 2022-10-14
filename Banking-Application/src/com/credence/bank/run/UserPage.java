/**
 * 
 */
package com.credence.bank.run;

import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.credence.bank.info.AccountsInfo;
import com.credence.bank.info.TransactionInfo;
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
	private int thisUserId;
	public void userPage(int userId,boolean authenticated,Scanner scanner) throws BMException
	{
		this.thisUserId = userId;
		scan = scanner;
		boolean session = false;
		UserRouter userRouter = BankingRouterProvider.INST;
		UserInfo thisUser = new UserInfo();
		logConsole.log(Level.INFO,"Welcome User\n"
				+ "1.Profile Details\n"
				+ "2.Create new Bank Account\n"
				+ "3.Deactivate User Account\n"
				+ "4.check Balance\n"
				+ "5.with Draw\n"
				+ "6.self Transfer\n"
				+ "7.others Transfer\n"
				+ "8.self Deposit\n"
				+ "9.others Deposit\n"
				+ "10.List All MyAccounts Information\n"
				+ "11.MyAccount Information\n"
				+ "12.Deactive My Account\n"
				+ "13.update Mobile Number\n"
				+ "14.update Email\n"
				+ "15.update Aadhar\n"
				+ "16.update Name\n"
				+ "17.update Role\n"
				+ "18.update Password\n"
				+ "19.change City\n"
				+ "20.change Type\n"
				+ "21.change AtmPin\n"
				+ "22.Check Transaction Information\n"
				+ "0.Exit\n"
				+ "100.Setup Install\n"
				+ "150.Save Changes\n");
		
		if(authenticated)
		{
			session = true;
		}
		
		while(session)
		{
			int operation =-1;
			logConsole.log(Level.INFO, "Enter Your Wish : ");
			try
			{
//				operation = Integer.parseInt(scan.next());
				operation = scan.nextInt();
			}
			catch(InputMismatchException e)
			{
				//logConsole.log(Level.SEVERE, "Invalid input");
			}
			switch(operation)
			{
			case 1:
			{
				try 
				{
					UserInfo user = userRouter.getProfileInfo(thisUserId);
					logConsole.log(Level.INFO,"{0}" ,user);
				}
				catch (BMException e) 
				{
					logConsole.log(Level.SEVERE, e.getMessage(), e.getCause());
				}
				break;
			}
			case 2:
			{
				try
				{
					AccountsInfo accounts = new AccountsInfo();
					accounts.setUserId(thisUserId);
					logConsole.log(Level.INFO, "Enter AccountNumber");
					long accountNumber = scan.nextInt();
					accounts.setAccountNumber(accountNumber);
					logConsole.log(Level.INFO, "Ifsc Code");
					String ifsc = scan.next();
					accounts.setIfsc(ifsc);
					accounts.setStatus("Active");
					logConsole.log(Level.INFO,"Enter Atm Pin");
					int atmPin = scan.nextInt();
					accounts.setAtmPin(atmPin);
					logConsole.log(Level.INFO, "Enter Account Type");
					String accountType = scan.next();
					accounts.setType(accountType);
					logConsole.log(Level.INFO, "Enter Branch");
					String branch = scan.next();
					accounts.setBranch(branch);
					logConsole.log(Level.INFO, "Enter balance");
					double balance = scan.nextDouble();
					accounts.setBalance(balance);
					boolean rescode = false;
					rescode = userRouter.createAccount(userId, accounts);
					if(rescode)
					{
						logConsole.log(Level.INFO, "Accounts added SuccessFully");
					}
					else
					{
						logConsole.log(Level.INFO, "Failed to remove Account");
					}
				}
				catch(InputMismatchException e)
				{
					throw new BMException("Invalid Input");
				}
				catch(BMException e)
				{
					logConsole.log(Level.SEVERE, e.getMessage(), e.getCause());
				}
			}
			case 3:
			{
				boolean status = false;
				try
				{
					status = userRouter.removeUser(thisUserId);
				}
				catch(BMException e)
				{
					logConsole.log(Level.SEVERE, e.getMessage(), e.getCause());
				}
				if(status)
				{
					logConsole.log(Level.INFO, "User Removed SuccessFully");
				}
				else
				{
					logConsole.log(Level.INFO, "Cannot able to remove User");
				}
				break;
			}
			case 4:
			{
				logConsole.log(Level.INFO, "Enter Account number");
				int accountNumber = scan.nextInt();
				double balance = 0;
				try
				{
					balance = userRouter.checkBalance(thisUserId,accountNumber);
					System.out.println("Your Account Balance is "+balance);
				}
				catch(BMException e)
				{
					logConsole.log(Level.SEVERE, e.getMessage(), e.getCause());
				}
				break;
			}
			case 5:
			{
				logConsole.log(Level.INFO, "WithDraw Money\nEnter Your Amount");
				double amount = scan.nextDouble();
				logConsole.log(Level.INFO, "Enter Account number");
				int accountNumber = scan.nextInt();
				try
				{
					userRouter.withDraw(thisUserId,accountNumber ,amount);
				}
				catch(BMException e)
				{
					logConsole.log(Level.SEVERE, e.getMessage(), e.getCause());
				}
				break;
			}
			case 6:
			{
				logConsole.log(Level.INFO, "Self deposit");
				logConsole.log(Level.INFO, "Enter From Account Number");
				int fromAccount = scan.nextInt();
				logConsole.log(Level.INFO, "Enter To Account Number");
				int toAccount = scan.nextInt();
				logConsole.log(Level.INFO, "Enter Amount");
				Double amount = scan.nextDouble();
				try
				{
					boolean rescode = userRouter.selfTransfer(thisUserId, fromAccount, toAccount, amount);
					if(rescode)
					{
						logConsole.log(Level.INFO, "Transfer Request Submited SuccessFully");
					}
					else
					{
						logConsole.log(Level.INFO, "Failed add Request");
					}
				}
				catch(BMException e)
				{
					logConsole.log(Level.SEVERE, e.getMessage(), e.getCause());
				}
				break;
			}
			case 7:
			{
				logConsole.log(Level.INFO, "Enter From Account Number");
				int fromAccount = scan.nextInt();
				logConsole.log(Level.INFO, "Enter To Account Number");
				int toAccount = scan.nextInt();
				logConsole.log(Level.INFO, "Enter Amount");
				Double amount = scan.nextDouble();
				try
				{
					boolean rescode = userRouter.othersTransfer(thisUserId, fromAccount, toAccount, amount);
					if(rescode)
					{
						logConsole.log(Level.INFO, "Transfer Request Submited SuccessFully");
					}
					else
					{
						logConsole.log(Level.INFO, "Failed add Request");
					}
				}
				catch(BMException e)
				{
					logConsole.log(Level.SEVERE, e.getMessage(), e.getCause());
				}
				break;
			}
			case 8:
			{
				logConsole.log(Level.INFO, "Enter Account Number");
				int account = scan.nextInt();
				logConsole.log(Level.INFO, "Enter Amount");
				Double amount = scan.nextDouble();
				try
				{
					boolean rescode = userRouter.selfDeposit(thisUserId, account, amount);
					if(rescode)
					{
						logConsole.log(Level.INFO, "Transfer Request Submited SuccessFully");
					}
					else
					{
						logConsole.log(Level.INFO, "Failed add Request");
					}
				}
				catch(BMException e)
				{
					logConsole.log(Level.SEVERE, e.getMessage(), e.getCause());
				}
				break;
			}
			case 9:
			{
				logConsole.log(Level.INFO, "Enter Account Number");
				int account = scan.nextInt();
				logConsole.log(Level.INFO, "Enter Amount");
				Double amount = scan.nextDouble();
				try
				{
					boolean rescode = userRouter.othersDeposit(account, amount);
					if(rescode)
					{
						logConsole.log(Level.INFO, "Transfer Request Submited SuccessFully");
					}
					else
					{
						logConsole.log(Level.INFO, "Failed add Request");
					}
				}
				catch(BMException e)
				{
					logConsole.log(Level.SEVERE, e.getMessage(), e.getCause());
				}
				break;
			}
			case 10:
			{
				try 
				{
					Map<Integer,AccountsInfo> myAccounts = (Map<Integer, AccountsInfo>) userRouter.getMyAccountInfo(thisUserId);
					System.out.println("-----Listing All Accounts---------");
					for(Integer key : myAccounts.keySet())
					{
						System.out.println(myAccounts.get(key));
					}
				}
				catch(BMException e)
				{
					logConsole.log(Level.SEVERE, e.getMessage(), e.getCause());
				}
				break;
			}
			case 11:
			{
				logConsole.log(Level.INFO, "Enter Your AccountNumber : ");
				Integer accountNumber = scan.nextInt();
				try 
				{
					AccountsInfo account = userRouter.getMyAccountInfo(thisUserId, accountNumber);
					if(account != null)
					{
						logConsole.log(Level.INFO, "{0}",account);
					}
				}
				catch(BMException e)
				{
					logConsole.log(Level.SEVERE, e.getMessage(), e.getCause());
				}
				break;
			}
			case 12:
			{
				logConsole.log(Level.INFO, "Enter Your AccountNumber : ");
				Integer accountNumber = scan.nextInt();
				try 
				{
					AccountsInfo account = userRouter.getMyAccountInfo(thisUserId, accountNumber);
					boolean rescode = userRouter.closeAccount(thisUserId, account);
					if(rescode)
					{
						logConsole.log(Level.INFO, "Account Deleted Successfully");
					}
					else
					{
						logConsole.log(Level.INFO, "Account Deletion Process Failed");
					}
					
				}
				catch(BMException e)
				{
					logConsole.log(Level.SEVERE, e.getMessage(), e.getCause());
				}
				break;
			}
			case 13:
			{
				logConsole.log(Level.INFO, "Enter Your Phone number : ");
				Integer phone = scan.nextInt();
				try 
				{
					boolean rescode = userRouter.updateMobileNumber(thisUserId, phone);
					if(rescode)
					{
						logConsole.log(Level.INFO, "Mobile Number Updated Successfully");
					}
					else
					{
						logConsole.log(Level.INFO, " Update Mobile Number Process Failed");
					}
					
				}
				catch(BMException e)
				{
					logConsole.log(Level.SEVERE, e.getMessage(), e.getCause());
				}
				break;
			}
			case 14:
			{
				logConsole.log(Level.INFO, "Enter Your EmailId : ");
				String email = scan.next();
				try 
				{
					boolean rescode = userRouter.updateEmail(thisUserId, email);
					if(rescode)
					{
						logConsole.log(Level.INFO, "EmailId Updated Successfully");
					}
					else
					{
						logConsole.log(Level.INFO, " Update EmailId Process Failed");
					}
					
				}
				catch(BMException e)
				{
					logConsole.log(Level.SEVERE, e.getMessage(), e.getCause());
				}
				break;
			}
			case 15:
			{
				logConsole.log(Level.INFO, "Enter Your AadharNumber : ");
				int aadhar = scan.nextInt();
				try 
				{
					boolean rescode = userRouter.updateAadhar(thisUserId, aadhar);
					if(rescode)
					{
						logConsole.log(Level.INFO, "aadhar Number Updated Successfully");
					}
					else
					{
						logConsole.log(Level.INFO, " Update Aadhar Process Failed");
					}
					
				}
				catch(BMException e)
				{
					logConsole.log(Level.SEVERE, e.getMessage(), e.getCause());
				}
				break;
			}
			case 16:
			{
				logConsole.log(Level.INFO, "Enter Your Name : ");
				String name = scan.next();
				try 
				{
					boolean rescode = userRouter.updateName(thisUserId, name);
					if(rescode)
					{
						logConsole.log(Level.INFO, "name Updated Successfully");
					}
					else
					{
						logConsole.log(Level.INFO, " Update name Process Failed");
					}
					
				}
				catch(BMException e)
				{
					logConsole.log(Level.SEVERE, e.getMessage(), e.getCause());
				}
				break;
			}
			case 17:
			{
				logConsole.log(Level.INFO, "Enter Your Role : ");
				String role = scan.next();
				try 
				{
					boolean rescode = userRouter.updateRole(thisUserId, role);
					if(rescode)
					{
						logConsole.log(Level.INFO, "role Updated Successfully");
					}
					else
					{
						logConsole.log(Level.INFO, " Update role Process Failed");
					}
					
				}
				catch(BMException e)
				{
					logConsole.log(Level.SEVERE, e.getMessage(), e.getCause());
				}
				break;
			}
			case 18:
			{
				logConsole.log(Level.INFO, "Enter Your oldPassword : ");
				String oldPassword = scan.next();
				logConsole.log(Level.INFO, "Enter Your newPassword : ");
				String newPassword = scan.next();
				try 
				{
					boolean rescode = userRouter.updatePassword(thisUserId, oldPassword,newPassword);
					if(rescode)
					{
						logConsole.log(Level.INFO, "password Updated Successfully");
					}
					else
					{
						logConsole.log(Level.INFO, " Update password failed Process Failed");
					}
					
				}
				catch(BMException e)
				{
					logConsole.log(Level.SEVERE, e.getMessage(), e.getCause());
				}
				break;
			}
			case 19:
			{
				logConsole.log(Level.INFO, "Enter Your city : ");
				String city = scan.next();
				
				try 
				{
					boolean rescode = userRouter.changeCity(thisUserId, city);
					if(rescode)
					{
						logConsole.log(Level.INFO, "city Updated Successfully");
					}
					else
					{
						logConsole.log(Level.INFO, " Update city failed Process Failed");
					}
					
				}
				catch(BMException e)
				{
					logConsole.log(Level.SEVERE, e.getMessage(), e.getCause());
				}
				break;
			}
			case 20:
			{
				logConsole.log(Level.INFO, "Enter Your type : ");
				String type = scan.next();
				
				try 
				{
					boolean rescode = userRouter.changeType(thisUserId, type);
					if(rescode)
					{
						logConsole.log(Level.INFO, "type Updated Successfully");
					}
					else
					{
						logConsole.log(Level.INFO, " Update type failed Process Failed");
					}
					
				}
				catch(BMException e)
				{
					logConsole.log(Level.SEVERE, e.getMessage(), e.getCause());
				}
				break;
			}
			case 21:
			{
				logConsole.log(Level.INFO, "Enter Your AccountNumber : ");
				int accountNumber = scan.nextInt();
				logConsole.log(Level.INFO, "Enter Your old AtmPin : ");
				int oldAtmPin = scan.nextInt();
				logConsole.log(Level.INFO, "Enter Your new AtmPin : ");
				int newAtmPin = scan.nextInt();
				try 
				{
					boolean rescode = userRouter.changeAtmPin(userId, accountNumber,oldAtmPin,newAtmPin);
					if(rescode)
					{
						logConsole.log(Level.INFO, "Atm Pin Number Updated Successfully");
					}
					else
					{
						logConsole.log(Level.INFO, " Update Pin Number Process Failed");
					}
					
				}
				catch(BMException e)
				{
					logConsole.log(Level.SEVERE, e.getMessage(), e.getCause());
				}
				break;
			}
			case 22:
			{
				logConsole.log(Level.INFO, "Enter Your TransactionId : ");
				int transactionId = scan.nextInt();
				try 
				{
					TransactionInfo transaction = userRouter.getTransaction(thisUserId,transactionId);
					if(transaction != null)
					{
						logConsole.log(Level.INFO, "{0}",transaction);
					}
					else
					{
						logConsole.log(Level.INFO, "Fetching TransactionInfo Process Failed ");
					}
				}
				catch(BMException e)
				{
					logConsole.log(Level.SEVERE, e.getMessage(), e.getCause());
				}
				break;
			}
			case 0:
			{
				session = false;
				break;
			}
			}
		}
	}
}
