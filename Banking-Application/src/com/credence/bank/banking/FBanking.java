/**
 * 
 */
package com.credence.bank.banking;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.credence.bank.info.AccountsInfo;
import com.credence.bank.info.TransactionInfo;
import com.credence.bank.info.UserInfo;
import com.credence.bank.util.BMException;
import com.credence.bank.util.Utilities;

/**
 * @author Balamurugan
 *
 */

public enum FBanking implements Storage
{
	INST;
	private Map<Integer,UserInfo> userDetails = new HashMap<>();
	private Map<Integer,AccountsInfo> accountDetails = new HashMap<>();
	private Map<Integer, Map<Integer, AccountsInfo>> customerInfo = new HashMap<>();
	private Map<Integer,TransactionInfo> transactions = new HashMap<>();
	private String userDetailSer = "FBankingUserDetails.ser";
	private String accountDetailSer = "FBankingAccountDetails.ser";
	private String customerInfoSer = "FBankingCustomerInfo.ser";
	private String transactionInfoSer = "FBankingTransactionInfo.ser";
	@Override
	public void setup() throws BMException
	{
		load();
	}
	@Override
	public void saveChanges() throws BMException 
	{
		store();
	}
	private void writeData(String fileName,Object object) throws BMException
	{
		try(FileOutputStream writeFile = new FileOutputStream(fileName);
				ObjectOutputStream writeObj = new ObjectOutputStream(writeFile))
		{
			writeObj.writeObject(object);
		} 
		catch (FileNotFoundException e) 
		{	
			throw new BMException(e.getMessage(),e);
		}
		catch (IOException e) 
		{	
			throw new BMException(e.getMessage(),e);
		}
	}
	private Object loadData(String fileName) throws BMException
	{
		try(FileInputStream readFile = new FileInputStream(fileName);
				ObjectInputStream readObj = new ObjectInputStream(readFile))
		{
			return readObj.readObject();
		} 
		catch (ClassNotFoundException e) 
		{
			throw new BMException(e.getMessage(),e);
		}
		catch (IOException e) 
		{
			throw new BMException(e.getMessage(),e);
		} 	
	}
	public void store() throws BMException
	{
		writeData(accountDetailSer,accountDetails);
		writeData(userDetailSer,userDetails);
		writeData(customerInfoSer,customerInfo);
		writeData(transactionInfoSer, transactions);
	}
	@SuppressWarnings("unchecked")
	public void load() throws BMException
	{
		accountDetails = (Map<Integer, AccountsInfo>) loadData(accountDetailSer);
		userDetails = (Map<Integer, UserInfo>) loadData(userDetailSer);
		customerInfo = (Map<Integer, Map<Integer, AccountsInfo>>) loadData(customerInfoSer);
		transactions = (Map<Integer, TransactionInfo>) loadData(transactionInfoSer);
	}
	@Override
	public void dumpUserProfileData(List<UserInfo> userInfo) throws BMException 
	{
		Utilities.INST.isNull(userInfo);
		for(UserInfo user: userInfo)
		{
			Utilities.INST.isNull(user);
			userDetails.put(user.getUserId(), user);
		}
	}
	@Override
	public void dumpAccountsData(List<AccountsInfo> accountsInfo) throws BMException 
	{
		Utilities.INST.isNull(accountsInfo);
		for(AccountsInfo accounts : accountsInfo)
		{
			Utilities.INST.isNull(accounts);
			accountDetails.put((int) accounts.getAccountNumber(), accounts);
			customerInfo.put(accounts.getUserId(), accountDetails);
		}
	}
	@Override
	public UserInfo getUserInfo(Integer userId) throws BMException 
	{	
		Utilities.INST.isNull(userId);
		UserInfo userInfo = userDetails.get(userId);
		Utilities.INST.isNull(userInfo);
		return userInfo;
	}

	@Override
	public AccountsInfo getMyAccount(Integer userId, Integer accountNumber) throws BMException {
		Map<Integer,AccountsInfo> myAccounts = customerInfo.get(userId);
		try
		{
			Utilities.INST.isNull(myAccounts);			
		}
		catch(BMException e)
		{
			throw new BMException("No matching Accounts Found",e);
		}
		AccountsInfo accountInfo = myAccounts.get(accountNumber);
		try
		{
			Utilities.INST.isNull(accountInfo);
		}
		catch(BMException e)
		{
			throw new BMException("Invalid account Number",e);
		}

		return accountInfo;
	}

	@Override
	public Double getBalance(Integer userId, Integer accountNumber) throws BMException 
	{
		Utilities.INST.isNull(userId);
		Utilities.INST.isNull(accountNumber);
		AccountsInfo myAccount = getMyAccount(userId,accountNumber); 
		Utilities.INST.isNull(myAccount);
		return myAccount.getBalance();
	}

	@Override
	public void selfDeposit(Integer userId, Integer accountNumber, Double amount) throws BMException 
	{
		Utilities.INST.isNull(userId);
		Utilities.INST.isNull(accountNumber);
		Utilities.INST.isNull(amount);
		AccountsInfo myAccount = getMyAccount(userId, accountNumber);
		Utilities.INST.isNull(myAccount);
		Double currentBalance = myAccount.getBalance();
		currentBalance += amount;
		myAccount.setBalance(currentBalance);
	}

	@Override
	public void otherDeposit(Integer accountNumber, Double amount) throws BMException 
	{
		Utilities.INST.isNull(accountNumber);
		Utilities.INST.isNull(amount);
		AccountsInfo receiverAccount = accountDetails.get(accountNumber);
		Utilities.INST.isNull(receiverAccount);
		Double currentBalance = receiverAccount.getBalance();
		currentBalance += amount;
		receiverAccount.setBalance(currentBalance);
	}

	@Override
	public void withDraw(Integer userId, Integer accountNumber, Double amount) throws BMException 
	{
		Utilities.INST.isNull(userId);
		Utilities.INST.isNull(accountNumber);
		Utilities.INST.isNull(amount);
		AccountsInfo myAccount = getMyAccount(userId,accountNumber);
		Utilities.INST.isNull(myAccount);
		Double currentBalance = myAccount.getBalance();
		if(currentBalance < amount)
		{
			throw new BMException("Insufficient Balance");
		}
		currentBalance -= amount;
		myAccount.setBalance(currentBalance);
	}

	@Override
	public void changeEmail(Integer userId, String email) throws BMException 
	{
		Utilities.INST.isNull(userId);
		Utilities.INST.isNull(email);
		Utilities.INST.isEmail(email);
		UserInfo myInfo = getUserInfo(userId);
		Utilities.INST.isNull(myInfo);
		myInfo.setEmail(email);
	}

	@Override
	public void changeMobileNumber(Integer userId, Integer number) throws BMException 
	{
		Utilities.INST.isNull(userId);
		Utilities.INST.isNull(number);
		UserInfo myInfo = getUserInfo(userId);
		Utilities.INST.isNull(myInfo);
		myInfo.setPhone(number);
	}

	@Override
	public void updatePassword(Integer userId, String oldPassword, String newPassword) throws BMException 
	{
		Utilities.INST.isNull(userId);
		Utilities.INST.isNull(oldPassword);
		Utilities.INST.isNull(newPassword);
		Utilities.INST.isPassword(newPassword);
		UserInfo myInfo = getUserInfo(userId);
		String orgPassword = myInfo.getPassword();
		if(orgPassword.equals(oldPassword))
		{
			myInfo.setPassword(newPassword);
		}
	}

	@Override
	public void moneyTransfer(Integer userId, Integer senderAccountNo, Integer receiverAccountNo, Double amount) throws BMException 
	{
		Utilities.INST.isNull(userId);
		Utilities.INST.isNull(senderAccountNo);
		Utilities.INST.isNull(receiverAccountNo);
		Utilities.INST.isNull(amount);
		AccountsInfo myAccount = getMyAccount(userId, senderAccountNo);
		AccountsInfo receiverAccount = accountDetails.get(receiverAccountNo);
		Utilities.INST.isNull(myAccount);
		Utilities.INST.isNull(receiverAccount);
		Double senderCurrentBalance = myAccount.getBalance();
		if(senderCurrentBalance < amount)
		{
			throw new BMException("Insufficient Balance to Make this Transacation");
		}
		senderCurrentBalance -= amount;
		Double receiverCurrentBalance = receiverAccount.getBalance();
		receiverCurrentBalance += amount;
	}

	@Override
	public void updateAtmPin(Integer userId, Integer accountNumber, Integer oldPin, Integer newPin) throws BMException 
	{
		Utilities.INST.isNull(userId);
		Utilities.INST.isNull(accountNumber);
		Utilities.INST.isNull(oldPin);
		Utilities.INST.isNull(newPin);
		AccountsInfo myAccount = getMyAccount(userId, accountNumber);
		Utilities.INST.isNull(myAccount);
		Integer pin = myAccount.getAtmPin();
		if(pin == oldPin)
		{
			myAccount.setAtmPin(newPin);
		}
	}
	@Override
	public void changeName(Integer userId, String name) throws BMException 
	{
		Utilities.INST.isNull(userId);
		Utilities.INST.isNull(name);
		UserInfo userInfo = getUserInfo(userId);
		Utilities.INST.isNull(userInfo);
		userInfo.setName(name);
	}
	@Override
	public void changeRole(Integer userId, String role) throws BMException 
	{
		Utilities.INST.isNull(userId);
		Utilities.INST.isNull(role);
		UserInfo userInfo = getUserInfo(userId);
		Utilities.INST.isNull(userInfo);
		userInfo.setRole(role);
	}
	@Override
	public void changeCity(Integer userId, String name) throws BMException 
	{
		Utilities.INST.isNull(userId);
		Utilities.INST.isNull(name);
		UserInfo userInfo = getUserInfo(userId);
		Utilities.INST.isNull(userInfo);
		userInfo.setCity(name);
	}
	//TODO Debug 
	@Override
	public void changeType(Integer userId, String type) throws BMException 
	{
		Utilities.INST.isNull(userId);
		Utilities.INST.isNull(type);
		AccountsInfo accountsInfo = accountDetails.get(userId);
		Utilities.INST.isNull(accountsInfo);
		accountsInfo.setType(type);
	}
	@Override
	public void changeAadhar(Integer userId, Integer aadharNumber) throws BMException 
	{
		Utilities.INST.isNull(userId);
		Utilities.INST.isNull(aadharNumber);
		UserInfo userInfo = getUserInfo(userId);
		Utilities.INST.isNull(userInfo);
		userInfo.setAadhar(aadharNumber);
	}
	@Override
	public Map<?, ?> getMyAccountsInfo(Integer userId) throws BMException 
	{
		return customerInfo.get(userId);
	}
	@Override
	public void createUser(UserInfo userInfo) throws BMException 
	{
		Utilities.INST.isNull(userInfo);
		userDetails.put(userInfo.getUserId(), userInfo);
	}
	@Override
	public void removeUser(Integer userId) throws BMException
	{
		Utilities.INST.isNull(userId);
		UserInfo userInfo = getUserInfo(userId);
		Utilities.INST.isNull(userInfo);
		userInfo.setStatus("Inactive");
	}
	@Override
	public void createAccount(Integer userId, AccountsInfo accountsInfo) throws BMException 
	{
		Utilities.INST.isNull(userId);
		Utilities.INST.isNull(accountsInfo);
		accountDetails.put((int) accountsInfo.getAccountNumber(), accountsInfo);
		customerInfo.put(accountsInfo.getUserId(), accountDetails);		
	}
	@Override
	public void deleteAccount(Integer userId, AccountsInfo accountsInfo) throws BMException 
	{
		Utilities.INST.isNull(userId);
		Utilities.INST.isNull(accountsInfo);
		accountsInfo.setStatus("Inactive");
	}
	@Override
	public void addTransaction(TransactionInfo transactionInfo) throws BMException 
	{
		Utilities.INST.isNull(transactionInfo);
		transactions.put(transactionInfo.getTransactionId(), transactionInfo);
	}
	@Override
	public TransactionInfo getTransaction(Integer transactionId) throws BMException 
	{
		Utilities.INST.isNull(transactionId);
		return transactions.get(transactionId);
	}
	@Override
	public Map<?, ?> getAllTransaction() 
	{
		return transactions;
	}
	@Override
	public void grantApproval(Integer transactionId) throws BMException 
	{
		Utilities.INST.isNull(transactionId);
		TransactionInfo thisTransaction = transactions.get(transactionId);
		thisTransaction.setStatus("Approved");
	}
	

}
