/**
 * 
 */
package com.credence.bank.routes;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import com.credence.bank.banking.EnvProperties;
import com.credence.bank.banking.Storage;
import com.credence.bank.info.AccountsInfo;
import com.credence.bank.info.UserInfo;
import com.credence.bank.util.BMException;
import com.credence.bank.util.Utilities;

/**
 * @author Balamurugan
 *
 */

//TODO null Check for all values
public enum BankingRouterProvider implements BankingRouter
{
	INST;
	private static Storage banking;
	public void setup() throws BMException
	{
		String className = EnvProperties.INST.envProps.getProperty("storage");
		try 
		{	
			Class<?> classObj = Class.forName(className);
			Constructor<?>[] constObj = classObj.getDeclaredConstructors();
			for(Constructor con : constObj)
			{
				banking = (Storage) con.newInstance(constObj);
			}
		}
		catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
				IllegalArgumentException | InvocationTargetException e ) 
		{
			throw new BMException("Implementation Class not found",e);
		}
	}
	@Override
	public boolean addUser(UserInfo userInfo) throws BMException 
	{
		Utilities.INST.isNull(userInfo);
		banking.createUser(userInfo);
		return true;
	}

	@Override
	public boolean createAccount(Integer userId, AccountsInfo accountInfo) throws BMException
	{
		Utilities.INST.isNull(userId);
		Utilities.INST.isNull(accountInfo);
		banking.createAccount(userId, accountInfo);
		return true;
	}

	@Override
	public boolean removeUser(Integer userId) throws BMException
	{
		Utilities.INST.isNull(userId);
		banking.removeUser(userId);
		return true;
	}

	@Override
	public Integer checkBalance(Integer userId,Integer accountNumber) throws BMException 
	{
		Utilities.INST.isNull(userId);
		Utilities.INST.isNull(accountNumber);
		return banking.getBalance(userId, accountNumber);//swami
	}

	@Override
	public boolean selfTransfer(Integer userId,Integer fromAccountNumber, Integer toAccountNumber, Integer amount) throws BMException 
	{		
		Utilities.INST.isNull(userId);
		Utilities.INST.isNull(fromAccountNumber);
		Utilities.INST.isNull(toAccountNumber);
		Utilities.INST.isNull(amount);
		banking.moneyTransfer(userId,fromAccountNumber, toAccountNumber, amount);
		return true;
	}

	@Override
	public boolean othersTransfer(Integer userId,Integer fromAccountNumber, Integer toAccountNumber, Integer amount) throws BMException 
	{
		Utilities.INST.isNull(userId);
		Utilities.INST.isNull(fromAccountNumber);
		Utilities.INST.isNull(toAccountNumber);
		Utilities.INST.isNull(amount);
		banking.moneyTransfer(userId, fromAccountNumber, toAccountNumber, amount);
		return true;
	}

	@Override
	public boolean selfDeposit(Integer userId,Integer accountNumber, Integer amount) throws BMException 
	{
		Utilities.INST.isNull(userId);
		Utilities.INST.isNull(accountNumber);
		Utilities.INST.isNull(amount);
		banking.selfDeposit(userId, accountNumber, amount);
		return true;
	}

	@Override
	public boolean othersDeposit(Integer accountNumber, Integer amount) throws BMException 
	{		
		Utilities.INST.isNull(accountNumber);
		Utilities.INST.isNull(amount);
		banking.otherDeposit(accountNumber, amount);
		return true;
	}


	@Override
	public UserInfo getProfileInfo(Integer userId) throws BMException 
	{
		Utilities.INST.isNull(userId);
		UserInfo userInfo = banking.getUserInfo(userId);
		return userInfo;
	}

	@Override
	public Map<?, ?> getMyAccountInfo(Integer userId) throws BMException 
	{
		Utilities.INST.isNull(userId);
		return banking.getMyAccountsInfo(userId);
	}

	@Override
	public AccountsInfo getMyAccountInfo(Integer userId, Integer accountNumber) throws BMException 
	{
		Utilities.INST.isNull(userId);
		Utilities.INST.isNull(accountNumber);
		AccountsInfo accountInfo = banking.getMyAccount(userId, accountNumber);
		return accountInfo;
	}

	@Override
	public boolean closeAccount(Integer userId,AccountsInfo accountsInfo) throws BMException 
	{
		Utilities.INST.isNull(userId);
		Utilities.INST.isNull(accountsInfo);
		banking.deleteAccount(userId,accountsInfo);
		return true;
	}

	@Override
	public boolean updateMobileNumber(Integer userId, Integer phoneNumber) throws BMException {
		Utilities.INST.isNull(userId);
		Utilities.INST.isNull(phoneNumber);
		banking.changeMobileNumber(userId, phoneNumber);
		return true;
	}

	@Override
	public boolean updateEmail(Integer userId, String email) throws BMException 
	{
		Utilities.INST.isNull(userId);
		Utilities.INST.isNull(email);
		Utilities.INST.isEmail(email);
		banking.changeEmail(userId, email);
		return true;
	}

	@Override
	public boolean updateAadhar(Integer userId, Integer aadharNumber) throws BMException 
	{
		Utilities.INST.isNull(userId);
		Utilities.INST.isNull(aadharNumber);
		banking.changeAadhar(userId, aadharNumber);
		return true;
	}

	@Override
	public boolean updateName(Integer userId, String name) throws BMException 
	{
		Utilities.INST.isNull(userId);
		Utilities.INST.isNull(name);
		banking.changeName(userId, name);
		return true;
	}

	@Override
	public boolean updateRole(Integer userId, String role) throws BMException 
	{
		Utilities.INST.isNull(userId);
		Utilities.INST.isNull(role);
		banking.changeRole(userId, role);
		return true;
	}

	@Override
	public boolean updatePassword(Integer userId, String oldPassword, String newPassword) throws BMException 
	{
		Utilities.INST.isNull(userId);
		Utilities.INST.isNull(oldPassword);
		Utilities.INST.isNull(newPassword);
		Utilities.INST.isPassword(newPassword);
		banking.updatePassword(userId, oldPassword, newPassword);
		return true;
	}

	@Override
	public boolean changeCity(Integer userId, String city) throws BMException 
	{
		Utilities.INST.isNull(userId);
		Utilities.INST.isNull(city);
		banking.changeCity(userId, city);
		return true;
	}

	@Override
	public boolean changeType(Integer accountNumber, String type) throws BMException 
	{
		Utilities.INST.isNull(accountNumber);
		Utilities.INST.isNull(type);
		banking.changeType(accountNumber, type);
		return true;
	}

	@Override
	public boolean changeAtmPin(Integer userId,Integer accountNumber, Integer oldPin, Integer newPin) throws BMException 
	{
		Utilities.INST.isNull(userId);
		Utilities.INST.isNull(accountNumber);
		Utilities.INST.isNull(oldPin);
		Utilities.INST.isNull(newPin);
		banking.updateAtmPin(userId, accountNumber, oldPin, newPin);
		return true;
	}
	@Override
	public boolean withDraw(Integer userId, Integer accountNumber, Integer amount) throws BMException {
		Utilities.INST.isNull(userId);
		Utilities.INST.isNull(accountNumber);
		Utilities.INST.isNull(amount);
		banking.withDraw(userId, accountNumber, amount);
		return true;
	}
	
} 
