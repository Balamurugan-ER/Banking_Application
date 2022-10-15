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
import com.credence.bank.routes.BankingRouter;
import com.credence.bank.routes.BankingRouterProvider;
import com.credence.bank.util.BMException;

/**
 * @author Balamurugan
 *
 */
public class AdminPage 
{
	private static Scanner scan;
	private int thisUserId;
	private static Logger logConsole = Logger.getLogger(AdminPage.class.getName());
	public void adminPage(boolean authenticated,Scanner scanner,int userID) throws BMException 
	{
		this.thisUserId = userID; 
		boolean session = false;
		scan = scanner;
		BankingRouter bankingRouter = new BankingRouterProvider(this.thisUserId);
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
				+ "24.getAll Transaction List\n"
				+ "25.get all Pending Transactions\n"
				+ "26.Reject Transaction"
				+ "27.grant Approval for Transactions\n"
				+ "100.Run initial Setup\n"
				+ "150.Save Changes\n"
				+ "0.Exit");

		if(authenticated)
		{
			session = true;
		}
		while(session)
		{
			logConsole.log(Level.INFO, "Enter Your Wish : ");
			int operation = -1;
			try
			{
				operation = Integer.parseInt(scan.next());		
			}
			catch(NumberFormatException exception)
			{
				logConsole.log(Level.SEVERE, "Wrong input");
			}
			
			switch(operation)
			{
			case 1:
			{
				try 
				{
					logConsole.log(Level.INFO, "Enter UserId  : ");
					int customerId = scan.nextInt();
					UserInfo user = bankingRouter.getProfileInfo(customerId);
					logConsole.log(Level.INFO,"{0}" ,user);
				}
				catch (BMException e) 
				{
					logConsole.log(Level.SEVERE, e.getMessage(), e.getCause());
					if(e.getMessage().equals("Session Time Expired Login Again"))
					{
						throw new BMException("Session Time Expired Login Again");
					}
				}
				catch(InputMismatchException e)
				{
					throw new BMException("Invalid Input",e);
				}
				break;
			}
			case 2:
			{
				try 
				{
					boolean rescode = false;
					UserInfo user = new UserInfo();
					logConsole.log(Level.INFO, "Enter Name");
					String name = scan.next();
					user.setName(name);
					logConsole.log(Level.INFO, "Enter Email");
					String email = scan.next();
					logConsole.log(Level.INFO, "Enter password");
					String password = scan.next();
					logConsole.log(Level.INFO, "Enter phone");
					long phone = scan.nextLong();
					logConsole.log(Level.INFO, "Enter aadhar");
					long aadhar = scan.nextLong();
					user.setAadhar(aadhar);
					logConsole.log(Level.INFO, "Enter city");
					String city = scan.next();
					logConsole.log(Level.INFO, "Enter role");
					String role = scan.next();
					logConsole.log(Level.INFO, "Enter status");
					String status = scan.next();
					logConsole.log(Level.INFO, "Enter adminAccess");
					String adminAccess = scan.next();
					user.setAdminAccess(adminAccess);
					user.setCity(city);
					user.setEmail(email);
					user.setPassword(password);
					user.setRole(role);
					user.setStatus(status);
					user.setPhone(phone);
					rescode = bankingRouter.addUser(user);
					if(rescode)
					{
						logConsole.log(Level.INFO, "Accounts added SuccessFully");
					}
					else
					{
						logConsole.log(Level.INFO, "Failed to remove Account");
					}
				} 
				catch (BMException e) 
				{
					logConsole.log(Level.SEVERE, e.getMessage(), e.getCause());
					if(e.getMessage().equals("Session Time Expired Login Again"))
					{
						throw new BMException("Session Time Expired Login Again");
					}
				}
				catch(InputMismatchException e)
				{
					throw new BMException("Invalid Input",e);
				}
				break;
			}
			case 3:
			{
				try {
					AccountsInfo accounts = new AccountsInfo();
					logConsole.log(Level.INFO, "Enter UserId");
					int accountUserId = scan.nextInt();
					accounts.setUserId(accountUserId);
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
					rescode = bankingRouter.createAccount(thisUserId, accounts);
					if(rescode)
					{
						logConsole.log(Level.INFO, "Accounts added SuccessFully");
					}
					else
					{
						logConsole.log(Level.INFO, "Failed to remove Account");
					}
				} 
				catch (BMException e) 
				{
					logConsole.log(Level.SEVERE, e.getMessage(), e.getCause());
					if(e.getMessage().equals("Session Time Expired Login Again"))
					{
						throw new BMException("Session Time Expired Login Again");
					}
				}
				catch(InputMismatchException e)
				{
					throw new BMException("Invalid Input",e);
				}
				break;
			}
			case 4:
			{
				boolean status = false;
				try
				{
					logConsole.log(Level.INFO, "Enter UserId to Remove ");
					int accountUserId = scan.nextInt();
					status = bankingRouter.removeUser(accountUserId);
					if(status)
					{
						logConsole.log(Level.INFO, "User Removed SuccessFully");
					}
					else
					{
						logConsole.log(Level.INFO, "Cannot able to remove User");
					}
				}
				catch (BMException e)
				{
					logConsole.log(Level.SEVERE, e.getMessage(), e.getCause());
					if(e.getMessage().equals("Session Time Expired Login Again"))
					{
						throw new BMException("Session Time Expired Login Again");
					}
				}
				catch(InputMismatchException e)
				{
					throw new BMException("Invalid Input",e);
				}
				break;
			}
			case 5:
			{
				try
				{
					logConsole.log(Level.INFO, "Enter UserId to check balance");
					int userIdbalance = scan.nextInt();
					logConsole.log(Level.INFO, "Enter Account number");
					int accountNumber = scan.nextInt();
					double balance = 0;
					balance = bankingRouter.checkBalance(userIdbalance,accountNumber);
					System.out.println("Your Account Balance is "+balance);
				}
				catch(BMException e)
				{
					logConsole.log(Level.SEVERE, e.getMessage(), e.getCause());
					if(e.getMessage().equals("Session Time Expired Login Again"))
					{
						throw new BMException("Session Time Expired Login Again");
					}
				}
				catch(InputMismatchException e)
				{
					throw new BMException("Invalid Input",e);
				}
				break;
			}
			case 6:
			{
				try
				{
					logConsole.log(Level.INFO, "WithDraw Money\nEnter Your Amount");
					double amount = scan.nextDouble();
					logConsole.log(Level.INFO, "Enter Account number");
					int accountNumber = scan.nextInt();
					bankingRouter.withDraw(thisUserId,accountNumber ,amount);
				}
				catch(BMException e)
				{
					logConsole.log(Level.SEVERE, e.getMessage(), e.getCause());
					if(e.getMessage().equals("Session Time Expired Login Again"))
					{
						throw new BMException("Session Time Expired Login Again");
					}
				}
				catch(InputMismatchException e)
				{
					throw new BMException("Invalid Input",e);
				}
				break;
			}
			case 7:
			{
				try
				{
					logConsole.log(Level.INFO, "Self deposit");
					logConsole.log(Level.INFO, "Enter From Account Number");
					int fromAccount = scan.nextInt();
					logConsole.log(Level.INFO, "Enter To Account Number");
					int toAccount = scan.nextInt();
					logConsole.log(Level.INFO, "Enter Amount");
					Double amount = scan.nextDouble();
					boolean rescode = bankingRouter.selfTransfer(thisUserId, fromAccount, toAccount, amount);
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
					if(e.getMessage().equals("Session Time Expired Login Again"))
					{
						throw new BMException("Session Time Expired Login Again");
					}
				}
				catch(InputMismatchException e)
				{
					throw new BMException("Invalid Input",e);
				}
				break;
			}
			case 8:
			{
				try
				{
					logConsole.log(Level.INFO, "Enter From Account Number");
					int fromAccount = scan.nextInt();
					logConsole.log(Level.INFO, "Enter To Account Number");
					int toAccount = scan.nextInt();
					logConsole.log(Level.INFO, "Enter Amount");
					Double amount = scan.nextDouble();
					boolean rescode = bankingRouter.othersTransfer(thisUserId, fromAccount, toAccount, amount);
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
					if(e.getMessage().equals("Session Time Expired Login Again"))
					{
						throw new BMException("Session Time Expired Login Again");
					}
				}
				catch(InputMismatchException e)
				{
					throw new BMException("Invalid Input",e);
				}
				break;
			}
			case 9:
			{
				try
				{
					logConsole.log(Level.INFO, "Enter Account Number");
					int account = scan.nextInt();
					logConsole.log(Level.INFO, "Enter Amount");
					Double amount = scan.nextDouble();
					boolean rescode = bankingRouter.selfDeposit(thisUserId, account, amount);
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
					if(e.getMessage().equals("Session Time Expired Login Again"))
					{
						throw new BMException("Session Time Expired Login Again");
					}
				}
				catch(InputMismatchException e)
				{
					throw new BMException("Invalid Input",e);
				}
				break;
			}
			case 10:
			{
				
				try
				{
					logConsole.log(Level.INFO, "Enter Account Number");
					int account = scan.nextInt();
					logConsole.log(Level.INFO, "Enter Amount");
					Double amount = scan.nextDouble();
					boolean rescode = bankingRouter.othersDeposit(account, amount);
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
					if(e.getMessage().equals("Session Time Expired Login Again"))
					{
						throw new BMException("Session Time Expired Login Again");
					}
				}
				catch(InputMismatchException e)
				{
					throw new BMException("Invalid Input",e);
				}
				break;
			}
			case 11:
			{
				try 
				{
					Map<Integer,AccountsInfo> myAccounts = (Map<Integer, AccountsInfo>) bankingRouter.getMyAccountInfo(thisUserId);
					System.out.println("-----Listing All Accounts---------");
					for(Integer key : myAccounts.keySet())
					{
						System.out.println(myAccounts.get(key));
					}
				}
				catch (BMException e) 
				{
					logConsole.log(Level.SEVERE, e.getMessage(), e.getCause());
					if(e.getMessage().equals("Session Time Expired Login Again"))
					{
						throw new BMException("Session Time Expired Login Again");
					}
				}
				catch(InputMismatchException e)
				{
					throw new BMException("Invalid Input",e);
				}
				break;
			}
			case 12:
			{
				
				try 
				{
					logConsole.log(Level.INFO, "Enter Your AccountNumber : ");
					Integer accountNumber = scan.nextInt();
					AccountsInfo account = bankingRouter.getMyAccountInfo(thisUserId, accountNumber);
					if(account != null)
					{
						logConsole.log(Level.INFO, "{0}",account);
					}
				}
				catch (BMException e) 
				{
					logConsole.log(Level.SEVERE, e.getMessage(), e.getCause());
					if(e.getMessage().equals("Session Time Expired Login Again"))
					{
						throw new BMException("Session Time Expired Login Again");
					}
				}
				catch(InputMismatchException e)
				{
					throw new BMException("Invalid Input",e);
				}
				break;
			}
			case 13:
			{
				try 
				{
					logConsole.log(Level.INFO, "Enter Your AccountNumber : ");
					Integer accountNumber = scan.nextInt();
					AccountsInfo account = bankingRouter.getMyAccountInfo(thisUserId, accountNumber);
					boolean rescode = bankingRouter.closeAccount(thisUserId, account);
					if(rescode)
					{
						logConsole.log(Level.INFO, "Account Deleted Successfully");
					}
					else
					{
						logConsole.log(Level.INFO, "Account Deletion Process Failed");
					}

				}
				catch (BMException e) 
				{
					logConsole.log(Level.SEVERE, e.getMessage(), e.getCause());
					if(e.getMessage().equals("Session Time Expired Login Again"))
					{
						throw new BMException("Session Time Expired Login Again");
					}
				}
				catch(InputMismatchException e)
				{
					throw new BMException("Invalid Input",e);
				}
				break;
			}
			case 14:
			{
				
				try 
				{
					logConsole.log(Level.INFO, "Enter Your Phone number : ");
					Integer phone = scan.nextInt();
					boolean rescode = bankingRouter.updateMobileNumber(thisUserId, phone);
					if(rescode)
					{
						logConsole.log(Level.INFO, "Mobile Number Updated Successfully");
					}
					else
					{
						logConsole.log(Level.INFO, " Update Mobile Number Process Failed");
					}

				}
				catch (BMException e) 
				{
					logConsole.log(Level.SEVERE, e.getMessage(), e.getCause());
					if(e.getMessage().equals("Session Time Expired Login Again"))
					{
						throw new BMException("Session Time Expired Login Again");
					}
				}
				catch(InputMismatchException e)
				{
					throw new BMException("Invalid Input",e);
				}
				break;
			}
			case 15:
			{
				
				try 
				{
					logConsole.log(Level.INFO, "Enter Your EmailId : ");
					String email = scan.next();
					boolean rescode = bankingRouter.updateEmail(thisUserId, email);
					if(rescode)
					{
						logConsole.log(Level.INFO, "EmailId Updated Successfully");
					}
					else
					{
						logConsole.log(Level.INFO, " Update EmailId Process Failed");
					}

				}
				catch (BMException e) 
				{
					logConsole.log(Level.SEVERE, e.getMessage(), e.getCause());
					if(e.getMessage().equals("Session Time Expired Login Again"))
					{
						throw new BMException("Session Time Expired Login Again");
					}
				}
				catch(InputMismatchException e)
				{
					throw new BMException("Invalid Input",e);
				}
				break;
			}
			case 16:
			{
				
				try 
				{
					logConsole.log(Level.INFO, "Enter Your AadharNumber : ");
					int aadhar = scan.nextInt();
					boolean rescode = bankingRouter.updateAadhar(thisUserId, aadhar);
					if(rescode)
					{
						logConsole.log(Level.INFO, "aadhar Number Updated Successfully");
					}
					else
					{
						logConsole.log(Level.INFO, " Update Aadhar Process Failed");
					}

				}
				catch (BMException e) 
				{
					logConsole.log(Level.SEVERE, e.getMessage(), e.getCause());
					if(e.getMessage().equals("Session Time Expired Login Again"))
					{
						throw new BMException("Session Time Expired Login Again");
					}
				}
				catch(InputMismatchException e)
				{
					throw new BMException("Invalid Input",e);
				}
				break;
			}
			case 17:
			{
				
				try 
				{
					logConsole.log(Level.INFO, "Enter Your Name : ");
					String name = scan.next();
					boolean rescode = bankingRouter.updateName(thisUserId, name);
					if(rescode)
					{
						logConsole.log(Level.INFO, "name Updated Successfully");
					}
					else
					{
						logConsole.log(Level.INFO, " Update name Process Failed");
					}

				}
				catch (BMException e) 
				{
					logConsole.log(Level.SEVERE, e.getMessage(), e.getCause());
					if(e.getMessage().equals("Session Time Expired Login Again"))
					{
						throw new BMException("Session Time Expired Login Again");
					}
				}
				catch(InputMismatchException e)
				{
					throw new BMException("Invalid Input",e);
				}
				break;
			}
			case 18:
			{
				
				try 
				{
					logConsole.log(Level.INFO, "Enter Your Role : ");
					String role = scan.next();
					boolean rescode = bankingRouter.updateRole(thisUserId, role);
					if(rescode)
					{
						logConsole.log(Level.INFO, "role Updated Successfully");
					}
					else
					{
						logConsole.log(Level.INFO, " Update role Process Failed");
					}

				}
				catch (BMException e) 
				{
					logConsole.log(Level.SEVERE, e.getMessage(), e.getCause());
					if(e.getMessage().equals("Session Time Expired Login Again"))
					{
						throw new BMException("Session Time Expired Login Again");
					}
				}
				catch(InputMismatchException e)
				{
					throw new BMException("Invalid Input",e);
				}
				break;
			}
			case 19:
			{
				
				try 
				{
					logConsole.log(Level.INFO, "Enter Your oldPassword : ");
					String oldPassword = scan.next();
					logConsole.log(Level.INFO, "Enter Your newPassword : ");
					String newPassword = scan.next();
					boolean rescode = bankingRouter.updatePassword(thisUserId, oldPassword,newPassword);
					if(rescode)
					{
						logConsole.log(Level.INFO, "password Updated Successfully");
					}
					else
					{
						logConsole.log(Level.INFO, " Update password failed Process Failed");
					}

				}
				catch (BMException e) 
				{
					logConsole.log(Level.SEVERE, e.getMessage(), e.getCause());
					if(e.getMessage().equals("Session Time Expired Login Again"))
					{
						throw new BMException("Session Time Expired Login Again");
					}
				}
				catch(InputMismatchException e)
				{
					throw new BMException("Invalid Input",e);
				}
				break;
			}
			case 20:
			{
				try 
				{
					logConsole.log(Level.INFO, "Enter Your city : ");
					String city = scan.next();
					boolean rescode = bankingRouter.changeCity(thisUserId, city);
					if(rescode)
					{
						logConsole.log(Level.INFO, "city Updated Successfully");
					}
					else
					{
						logConsole.log(Level.INFO, " Update city failed Process Failed");
					}

				}
				catch (BMException e) 
				{
					logConsole.log(Level.SEVERE, e.getMessage(), e.getCause());
					if(e.getMessage().equals("Session Time Expired Login Again"))
					{
						throw new BMException("Session Time Expired Login Again");
					}
				}
				catch(InputMismatchException e)
				{
					throw new BMException("Invalid Input",e);
				}
				break;
			}
			case 21:
			{
				try 
				{
					logConsole.log(Level.INFO, "Enter Your type : ");
					String type = scan.next();
					boolean rescode = bankingRouter.changeType(thisUserId, type);
					if(rescode)
					{
						logConsole.log(Level.INFO, "type Updated Successfully");
					}
					else
					{
						logConsole.log(Level.INFO, " Update type failed Process Failed");
					}

				}
				catch (BMException e) 
				{
					logConsole.log(Level.SEVERE, e.getMessage(), e.getCause());
					if(e.getMessage().equals("Session Time Expired Login Again"))
					{
						throw new BMException("Session Time Expired Login Again");
					}
				}
				catch(InputMismatchException e)
				{
					throw new BMException("Invalid Input",e);
				}
				break;
			}
			case 22:
			{
				try 
				{
					logConsole.log(Level.INFO, "Enter Your AccountNumber : ");
					int accountNumber = scan.nextInt();
					logConsole.log(Level.INFO, "Enter Your old AtmPin : ");
					int oldAtmPin = scan.nextInt();
					logConsole.log(Level.INFO, "Enter Your new AtmPin : ");
					int newAtmPin = scan.nextInt();
					boolean rescode = bankingRouter.changeAtmPin(thisUserId, accountNumber,oldAtmPin,newAtmPin);
					if(rescode)
					{
						logConsole.log(Level.INFO, "Atm Pin Number Updated Successfully");
					}
					else
					{
						logConsole.log(Level.INFO, " Update Pin Number Process Failed");
					}

				}
				catch (BMException e) 
				{
					logConsole.log(Level.SEVERE, e.getMessage(), e.getCause());
					if(e.getMessage().equals("Session Time Expired Login Again"))
					{
						throw new BMException("Session Time Expired Login Again");
					}
				}
				catch(InputMismatchException e)
				{
					throw new BMException("Invalid Input",e);
				}
				break;
			}
			case 23:
			{
				
				try 
				{
					logConsole.log(Level.INFO, "Enter Your TransactionId : ");
					int transactionId = scan.nextInt();
					TransactionInfo transaction = bankingRouter.getTransaction(thisUserId ,transactionId);
					if(transaction != null)
					{
						System.out.println(transaction);
					}
					else
					{
						logConsole.log(Level.INFO, "Fetching TransactionInfo Process Failed ");
					}
				}
				catch (BMException e) 
				{
					logConsole.log(Level.SEVERE, e.getMessage(), e.getCause());
					if(e.getMessage().equals("Session Time Expired Login Again"))
					{
						throw new BMException("Session Time Expired Login Again");
					}
				}
				catch(InputMismatchException e)
				{
					throw new BMException("Invalid Input",e);
				}
				break;
			}
			case 24:
			{
				try 
				{
					Map<Integer,TransactionInfo> allTransaction = (Map<Integer, TransactionInfo>) bankingRouter.getAllTransaction();
					for(Integer transaction : allTransaction.keySet())
					{
						System.out.println(allTransaction.get(transaction));
					}
				} catch (BMException e) 
				{
					logConsole.log(Level.SEVERE, e.getMessage(), e.getCause());
					if(e.getMessage().equals("Session Time Expired Login Again"))
					{
						throw new BMException("Session Time Expired Login Again");
					}
				}
				catch(InputMismatchException e)
				{
					throw new BMException("Invalid Input",e);
				}
				break;
			}
			case 25:
			{
				try 
				{
					Map<Integer,TransactionInfo> allTransaction = (Map<Integer, TransactionInfo>) bankingRouter.getAllPendingTransaction();
					for(Integer transaction : allTransaction.keySet())
					{
						System.out.println(allTransaction.get(transaction));
					}
				}
				catch (BMException e) 
				{
					logConsole.log(Level.SEVERE, e.getMessage(), e.getCause());
					if(e.getMessage().equals("Session Time Expired Login Again"))
					{
						throw new BMException("Session Time Expired Login Again");
					}
				}
				catch(InputMismatchException e)
				{
					throw new BMException("Invalid Input",e);
				}
				break;
			}
			case 26:
			{
				
				try 
				{
					logConsole.log(Level.INFO, "TransactionId to Reject : ");
					Integer transactionId = scan.nextInt();
					bankingRouter.grantApproval(transactionId);
					System.out.println("Transaction Rejected");

				}
				catch (BMException e) 
				{
					logConsole.log(Level.SEVERE, e.getMessage(), e.getCause());
					if(e.getMessage().equals("Session Time Expired Login Again"))
					{
						throw new BMException("Session Time Expired Login Again");
					}
				}
				catch(InputMismatchException e)
				{
					throw new BMException("Invalid Input",e);
				}
				break;
			}
			case 27:
			{
				
				try 
				{
					logConsole.log(Level.INFO, "TransactionId to Approve Transaction : ");
					Integer transactionId = scan.nextInt();
					bankingRouter.grantApproval(transactionId);
					System.out.println("Transaction Approved");

				}
				catch (BMException e) 
				{
					logConsole.log(Level.SEVERE, e.getMessage(), e.getCause());
					if(e.getMessage().equals("Session Time Expired Login Again"))
					{
						throw new BMException("Session Time Expired Login Again");
					}
				}
				catch(InputMismatchException e)
				{
					throw new BMException("Invalid Input",e);
				}
				break;
			}
			case 100:
			{
				try
				{
					bankingRouter.setup();
				} 
				catch (BMException e) 
				{
					logConsole.log(Level.SEVERE, e.getMessage(), e.getCause());
					if(e.getMessage().equals("Session Time Expired Login Again"))
					{
						throw new BMException("Session Time Expired Login Again");
					}
				}
				logConsole.log(Level.INFO, "Running Crendence Banking Setup.\n.\n.\n.\n.\n. \nSetup Completed\n");
				break;
			}
			case 150:
			{
				try
				{
					bankingRouter.saveChanges();
				} 
				catch (BMException e) 
				{
					logConsole.log(Level.SEVERE, e.getMessage(), e.getCause());
					if(e.getMessage().equals("Session Time Expired Login Again"))
					{
						throw new BMException("Session Time Expired Login Again");
					}
				}
				logConsole.log(Level.INFO, "Crendence Banking Saving your Changes.\n.\n.\n.\n.\n. \nSave Completed\n");
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
