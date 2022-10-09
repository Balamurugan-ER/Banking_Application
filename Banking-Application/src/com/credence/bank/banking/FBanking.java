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
import java.util.Map;

import com.credence.bank.info.AccountsInfo;
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
	private HashMap<Integer,UserInfo> userDetails = new HashMap<>();
	private HashMap<Integer,AccountsInfo> accountDetails = new HashMap<>();
	private HashMap<Integer, HashMap<Integer, AccountsInfo>> customerInfo = new HashMap<>();
	private String userDetailSer = "FBankingUserDetails.ser";
	private String accountDetailSer = "FBankingUserDetails.ser";
	private String customerInfoSer = "FBankingUserDetails.ser";
	private void writeData(String fileName,Object object) throws BMException
	{
		try(FileOutputStream writeFile = new FileOutputStream(fileName);
				ObjectOutputStream writeObj = new ObjectOutputStream(writeFile))
		{
			writeObj.writeObject(object);
		} 
		catch (FileNotFoundException e) 
		{	
			throw new BMException(e.getMessage());
		}
		catch (IOException e) 
		{	
			throw new BMException(e.getMessage());
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
			throw new BMException(e.getMessage());
		}
		catch (IOException e) 
		{
			throw new BMException(e.getMessage());
		} 	
	}
	public void store() throws BMException
	{
		writeData(accountDetailSer,accountDetails);
		writeData(userDetailSer,userDetails);
		writeData(customerInfoSer,customerInfo);
	}
	@SuppressWarnings("unchecked")
	public void load() throws BMException
	{
		accountDetails = (HashMap<Integer, AccountsInfo>) loadData(accountDetailSer);
		userDetails = (HashMap<Integer, UserInfo>) loadData(userDetailSer);
		customerInfo = (HashMap<Integer, HashMap<Integer, AccountsInfo>>) loadData(customerInfoSer);
	}
	//@Override
	public void dumpUserProfileData() 
	{

		UserInfo user = new UserInfo();
		user.setUserId(1);
		user.setName("Bala");
		user.setEmail("Bala@zohocorp.com");
		user.setAadhar(123456789);
		user.setCity("Madurai");
		user.setRole("Customer");
		user.setPassword("Bala@123");
		user.setPhone(80907060);
		userDetails.put(1, user);
		user.setUserId(2);
		user.setName("John");
		user.setEmail("John@wwe.com");
		user.setAadhar(987654321);
		user.setCity("Chennai");
		user.setRole("Customer");
		user.setPassword("John@123");
		user.setPhone(65432781);
		userDetails.put(2, user);
		user.setUserId(3);
		user.setName("Randy");
		user.setEmail("Randy@zohocorp.com");
		user.setAadhar(5324178);
		user.setCity("Tenkasi");
		user.setRole("Customer");
		user.setPassword("Randy@123");
		user.setPhone(775533221);
		userDetails.put(3, user);
		user.setUserId(4);
		user.setName("Batista");
		user.setEmail("Manager@wwe.com");
		user.setAadhar(44332211);
		user.setCity("Coimbatore");
		user.setRole("Customer");
		user.setPassword("Batista@123");
		user.setPhone(80907060);
		userDetails.put(4, user);
		user.setUserId(5);
		user.setName("Sachin");
		user.setEmail("Sachin@zohocorp.com");
		user.setAadhar(2276410);
		user.setCity("Karaikudi");
		user.setRole("Admin");
		user.setPassword("Sachin@123");
		user.setPhone(1010203040);
		userDetails.put(5, user);
	}



	//@Override
	public void dumpAccountsData() 
	{
		AccountsInfo accountsInfo = new AccountsInfo();
		accountsInfo.setUserId(1);
		accountsInfo.setAccountNumber(123321456);
		accountsInfo.setBalance(1000);
		accountsInfo.setBranch("Madurai");
		accountsInfo.setDeposit(10000);
		accountsInfo.setIfsc("IYZ123098");
		accountsInfo.setStatus("Active");
		accountsInfo.setType("Savings");
		accountDetails.put(123321456, accountsInfo);
		customerInfo.put(1, accountDetails);
		accountsInfo.setUserId(2);
		accountsInfo.setAccountNumber(3245289);
		accountsInfo.setBalance(1500);
		accountsInfo.setBranch("Karaikudi");
		accountsInfo.setDeposit(20000);
		accountsInfo.setIfsc("IYZUO765");
		accountsInfo.setStatus("Active");
		accountsInfo.setType("Savings");
		accountDetails.put(3245289, accountsInfo);
		customerInfo.put(2, accountDetails);
		accountsInfo.setUserId(3);
		accountsInfo.setAccountNumber(876543321);
		accountsInfo.setBalance(5000);
		accountsInfo.setBranch("Chennai");
		accountsInfo.setDeposit(7000);
		accountsInfo.setIfsc("ZC123098");
		accountsInfo.setStatus("Active");
		accountsInfo.setType("Current");
		accountDetails.put(3, accountsInfo);
		customerInfo.put(3, accountDetails);

	}

	@Override
	public UserInfo getUserInfo(Integer userId) throws BMException 
	{	
		UserInfo userInfo = userDetails.get(userId);
		try
		{
			Utilities.INST.isNull(userInfo);
		}
		catch(BMException e)
		{
			throw new BMException("No user Info Found");
		}
		return userInfo;
	}

	@Override
	public AccountsInfo getMyAccount(Integer userId, Integer accountNumber) throws BMException {
		HashMap<Integer,AccountsInfo> myAccounts = customerInfo.get(userId);
		try
		{
			Utilities.INST.isNull(myAccounts);			
		}
		catch(BMException e)
		{
			throw new BMException("No matching Accounts Found");
		}
		AccountsInfo accountInfo = myAccounts.get(accountNumber);
		try
		{
			Utilities.INST.isNull(accountInfo);
		}
		catch(BMException e)
		{
			throw new BMException("Invalid account Number");
		}

		return accountInfo;
	}

	@Override
	public Integer getBalance(Integer userId, Integer accountNumber) throws BMException 
	{
		Utilities.INST.isNull(userId);
		Utilities.INST.isNull(accountNumber);
		AccountsInfo myAccount = getMyAccount(userId,accountNumber); 
		Utilities.INST.isNull(myAccount);
		return myAccount.getBalance();
	}

	@Override
	public void selfDeposit(Integer userId, Integer accountNumber, Integer amount) throws BMException 
	{
		Utilities.INST.isNull(userId);
		Utilities.INST.isNull(accountNumber);
		Utilities.INST.isNull(amount);
		AccountsInfo myAccount = getMyAccount(userId, accountNumber);
		Utilities.INST.isNull(myAccount);
		Integer currentBalance = myAccount.getBalance();
		currentBalance += amount;
		myAccount.setBalance(currentBalance);
	}

	@Override
	public void otherDeposit(Integer accountNumber, Integer amount) throws BMException 
	{
		Utilities.INST.isNull(accountNumber);
		Utilities.INST.isNull(amount);
		AccountsInfo receiverAccount = accountDetails.get(accountNumber);
		Utilities.INST.isNull(receiverAccount);
		Integer currentBalance = receiverAccount.getBalance();
		currentBalance += amount;
		receiverAccount.setBalance(currentBalance);
	}

	@Override
	public void withDraw(Integer userId, Integer accountNumber, Integer amount) throws BMException 
	{
		Utilities.INST.isNull(userId);
		Utilities.INST.isNull(accountNumber);
		Utilities.INST.isNull(amount);
		AccountsInfo myAccount = getMyAccount(userId,accountNumber);
		Utilities.INST.isNull(myAccount);
		Integer currentBalance = myAccount.getBalance();
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
	public void moneyTransfer(Integer userId, Integer senderAccountNo, Integer receiverAccountNo, Integer amount) throws BMException 
	{
		Utilities.INST.isNull(userId);
		Utilities.INST.isNull(senderAccountNo);
		Utilities.INST.isNull(receiverAccountNo);
		Utilities.INST.isNull(amount);
		AccountsInfo myAccount = getMyAccount(userId, senderAccountNo);
		AccountsInfo receiverAccount = accountDetails.get(receiverAccountNo);
		Utilities.INST.isNull(myAccount);
		Utilities.INST.isNull(receiverAccount);
		Integer senderCurrentBalance = myAccount.getBalance();
		if(senderCurrentBalance < amount)
		{
			throw new BMException("Insufficient Balance to Make this Transacation");
		}
		senderCurrentBalance -= amount;
		Integer receiverCurrentBalance = receiverAccount.getBalance();
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
	public void fixedDeposit(Integer accountNumber, Integer amount) throws BMException 
	{
		Utilities.INST.isNull(accountNumber);
		Utilities.INST.isNull(amount);
		AccountsInfo accountsInfo = accountDetails.get(accountNumber);
		Utilities.INST.isNull(accountsInfo);
		accountsInfo.setDeposit(amount);
		
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

}