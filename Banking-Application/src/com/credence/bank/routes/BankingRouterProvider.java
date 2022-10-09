/**
 * 
 */
package com.credence.bank.routes;

import java.lang.reflect.Constructor;
import java.util.Map;
import java.util.Properties;

import com.credence.bank.banking.BankingDB;
import com.credence.bank.banking.EnvProperties;
import com.credence.bank.banking.Storage;
import com.credence.bank.info.AccountsInfo;
import com.credence.bank.info.UserInfo;
import com.credence.bank.util.BMException;

/**
 * @author Balamurugan
 *
 */

//TODO null Check for all values
public class BankingRouterProvider implements BankingRouter 
{
	private static Storage banking;
	public BankingRouterProvider() throws BMException 
	{
		String className = EnvProperties.INST.envProps.getProperty("storage");
		try 
		{	
			Class<?> classObj = Class.forName(className);
			banking = (Storage) classObj.newInstance();
		}
		catch (ClassNotFoundException e) 
		{
			throw new BMException("Implementation Class not found");
		}
		catch (InstantiationException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IllegalAccessException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	@Override
	public Integer addUser(UserInfo userInfo) throws BMException 
	{
		banking.createUser(userInfo);
		return 200;
	}

	@Override
	public Integer createAccount(Integer userId, AccountsInfo accountInfo) throws BMException
	{
		banking.createAccount(userId, accountInfo);
		return 200;
	}

	@Override
	public Integer removeUser(Integer userId) throws BMException
	{
		banking.removeUser(userId);
		return 200;
	}

	@Override
	public Integer checkBalance(Integer userId,Integer accountNumber) throws BMException 
	{
		return banking.getBalance(userId, accountNumber);
	}

	@Override
	public Integer selfTransfer(Integer userId,Integer fromAccountNumber, Integer toAccountNumber, Integer amount) throws BMException {
		
		banking.moneyTransfer(userId,fromAccountNumber, toAccountNumber, amount);
		return 200;
	}

	@Override
	public Integer othersTransfer(Integer userId,Integer fromAccountNumber, Integer toAccountNumber, Integer amount) throws BMException 
	{
		banking.moneyTransfer(userId, fromAccountNumber, toAccountNumber, amount);
		return 200;
	}

	@Override
	public Integer selfDeposit(Integer userId,Integer accountNumber, Integer amount) throws BMException 
	{
		banking.selfDeposit(userId, accountNumber, amount);
		return 200;
	}

	@Override
	public Integer othersDeposit(Integer accountNumber, Integer amount) throws BMException {
		
		banking.otherDeposit(accountNumber, amount);
		return 200;
	}

	@Override
	public Integer fixedDeposit(Integer accountNumber, Integer amount) throws BMException
	{
		banking.fixedDeposit(accountNumber, amount);
		return 200;
	}

	@Override
	public UserInfo getProfileInfo(Integer userId) throws BMException {
		UserInfo userInfo = banking.getUserInfo(userId);
		return userInfo;
	}

	@Override
	public Map<?, ?> getMyAccountInfo(Integer userId) throws BMException 
	{
		return banking.getMyAccountsInfo(userId);
	}

	@Override
	public AccountsInfo getMyAccountInfo(Integer userId, Integer accountNumber) throws BMException {
		AccountsInfo accountInfo = banking.getMyAccount(userId, accountNumber);
		return accountInfo;
	}

	@Override
	public Integer closeAccount(Integer userId,AccountsInfo accountsInfo) throws BMException 
	{
		banking.deleteAccount(userId,accountsInfo);
		return 200;
	}

	@Override
	public Integer updateMobileNumber(Integer userId, Integer phoneNumber) throws BMException {
		banking.changeMobileNumber(userId, phoneNumber);
		return 200;
	}

	@Override
	public Integer updateEmail(Integer userId, String email) throws BMException {
		banking.changeEmail(userId, email);
		return 200;
	}

	@Override
	public Integer updateAadhar(Integer userId, Integer aadharNumber) throws BMException 
	{
		banking.changeAadhar(userId, aadharNumber);
		return 200;
	}

	@Override
	public Integer updateName(Integer userId, String name) throws BMException 
	{
		banking.changeName(userId, name);
		return 200;
	}

	@Override
	public Integer updateRole(Integer userId, String role) throws BMException 
	{
		banking.changeRole(userId, role);
		return 200;
	}

	@Override
	public Integer updatePassword(Integer userId, String oldPassword, String newPassword) throws BMException {
		banking.updatePassword(userId, oldPassword, newPassword);
		return 200;
	}

	@Override
	public Integer changeCity(Integer userId, String city) throws BMException 
	{
		banking.changeCity(userId, city);
		return 200;
	}

	@Override
	public Integer changeType(Integer accountNumber, String type) throws BMException 
	{
		banking.changeType(accountNumber, type);
		return 200;
	}

	@Override
	public Integer changeAtmPin(Integer userId,Integer accountNumber, Integer oldPin, Integer newPin) throws BMException {
		banking.updateAtmPin(userId, accountNumber, oldPin, newPin);
		return 200;
	}
	@Override
	public Integer withDraw(Integer userId, Integer accountNumber, Integer amount) throws BMException {
		banking.withDraw(userId, accountNumber, amount);
		return 200;
	}
	
} 
