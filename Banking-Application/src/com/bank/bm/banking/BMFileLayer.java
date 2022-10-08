/**
 * 
 */
package com.bank.bm.banking;

import java.util.HashMap;
import java.util.Map;

import com.bank.bm.pojo.AccountsInfo;
import com.bank.bm.pojo.UserInfo;
import com.bank.bm.util.BMException;

/**
 * @author Balamurugan
 *
 */
public class BMFileLayer //implements Storage 
{
	HashMap<Integer,UserInfo> userDetails = new HashMap<>();
	HashMap<Integer, HashMap<Integer, AccountsInfo>> customerInfo = new HashMap<Integer,HashMap<Integer,AccountsInfo>>();
	
	// Adding Default UserInfo Details
	//@Override
	public void addDumpData() 
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
	
	public void addDumpAccountsData()
	{
		HashMap<Integer,AccountsInfo> dumpInfo = new HashMap<Integer,AccountsInfo>();
		AccountsInfo accountsInfo = new AccountsInfo();
		accountsInfo.setUserId(1);
		accountsInfo.setAccountNumber(123321456);
		accountsInfo.setBalance(1000);
		accountsInfo.setBranch("Madurai");
		accountsInfo.setDeposit(10000);
		accountsInfo.setIfsc("IYZ123098");
		accountsInfo.setStatus("Active");
		accountsInfo.setType("Savings");
		dumpInfo.put(1, accountsInfo);
		customerInfo.put(123321456, dumpInfo);
		accountsInfo.setUserId(2);
		accountsInfo.setAccountNumber(3245289);
		accountsInfo.setBalance(1500);
		accountsInfo.setBranch("Karaikudi");
		accountsInfo.setDeposit(20000);
		accountsInfo.setIfsc("IYZUO765");
		accountsInfo.setStatus("Active");
		accountsInfo.setType("Savings");
		dumpInfo.put(2, accountsInfo);
		customerInfo.put(3245289, dumpInfo);
		accountsInfo.setUserId(3);
		accountsInfo.setAccountNumber(876543321);
		accountsInfo.setBalance(5000);
		accountsInfo.setBranch("Chennai");
		accountsInfo.setDeposit(7000);
		accountsInfo.setIfsc("ZC123098");
		accountsInfo.setStatus("Active");
		accountsInfo.setType("Current");
		dumpInfo.put(3, accountsInfo);
		customerInfo.put(876543321, dumpInfo);
	}
	//@Override
	public Object getData(Integer id) {
		
		return userDetails.get(id);
	}
	public Map<?,?> getAccountsInfo(Integer id )
	{
		return customerInfo.get(id);
	}
	public AccountsInfo getMyAccount(int userId,long accountNumber,int money)
	{
		return customerInfo.get(userId).get(accountNumber);
	}
	public long getMyBalance(AccountsInfo obj)
	{
		return obj.getBalance();
	}
	public void cashDeposit(int userId,long accountNumber,int money) 
	{
		AccountsInfo myAccount = getMyAccount(userId,accountNumber,money);
		long currentBalance = getMyBalance(myAccount);
		currentBalance += money;
		myAccount.setBalance(currentBalance);
	}
	public void withDraw(int userId,long accountNumber,int money) throws BMException
	{
		AccountsInfo myAccount = getMyAccount(userId,accountNumber,money);
		long currentBalance = getMyBalance(myAccount);
		if(currentBalance < money)
		{
			throw new BMException("Insufficient Balance");
		}
		currentBalance -= money;
		myAccount.setBalance(currentBalance);
	}

}
