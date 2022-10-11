/**
 * 
 */
package com.credence.bank.routes;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import com.credence.bank.banking.Storage;
import com.credence.bank.controller.Transaction;
import com.credence.bank.controller.TransactionRouter;
import com.credence.bank.info.AccountsInfo;
import com.credence.bank.info.TransactionInfo;
import com.credence.bank.info.UserInfo;
import com.credence.bank.util.BMException;
import com.credence.bank.util.EnvProperties;
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
	private static Transaction transaction = new TransactionRouter();
	private static Storage getStorage() throws BMException 
	{
		if(banking == null)
		{
			banking = Utilities.INST.getStorage();
		}
		return banking;
	}
	@Override
	public boolean addUser(UserInfo userInfo) throws BMException 
	{
		banking = getStorage();
		Utilities.INST.isNull(userInfo);
		banking.createUser(userInfo);
		return true;
	}

	@Override
	public boolean createAccount(Integer userId, AccountsInfo accountInfo) throws BMException
	{
		banking = getStorage();
		Utilities.INST.isNull(userId);
		Utilities.INST.isNull(accountInfo);
		banking.createAccount(userId, accountInfo);
		return true;
	}

	@Override
	public boolean removeUser(Integer userId) throws BMException
	{
		banking = getStorage();
		Utilities.INST.isNull(userId);
		banking.removeUser(userId);
		return true;
	}

	@Override
	public Double checkBalance(Integer userId,Integer accountNumber) throws BMException 
	{
		banking = getStorage();
		Utilities.INST.isNull(userId);
		Utilities.INST.isNull(accountNumber);
		return banking.getBalance(userId, accountNumber);//swami
	}

	@Override
	public boolean selfTransfer(Integer userId,Integer fromAccountNumber, Integer toAccountNumber, Double amount) throws BMException 
	{		
		banking = getStorage();
		Utilities.INST.isNull(userId);
		Utilities.INST.isNull(fromAccountNumber);
		Utilities.INST.isNull(toAccountNumber);
		Utilities.INST.isNull(amount);
		TransactionInfo newTransaction = new TransactionInfo();
		newTransaction.setAmount(amount);
		newTransaction.setReceiverAccountNumber(fromAccountNumber);
		newTransaction.setSenderAccountNumber(toAccountNumber);
		newTransaction.setUserId(userId);
		newTransaction.setTime(System.currentTimeMillis());
		newTransaction.setStatus("Pending");
		newTransaction.setType("moneyTransfer");
		addTransaction(newTransaction);
		return true;
	}

	@Override
	public boolean othersTransfer(Integer userId,Integer fromAccountNumber, Integer toAccountNumber, Double amount) throws BMException 
	{
		banking = getStorage();
		Utilities.INST.isNull(userId);
		Utilities.INST.isNull(fromAccountNumber);
		Utilities.INST.isNull(toAccountNumber);
		Utilities.INST.isNull(amount);
		TransactionInfo newTransaction = new TransactionInfo();
		newTransaction.setAmount(amount);
		newTransaction.setReceiverAccountNumber(fromAccountNumber);
		newTransaction.setSenderAccountNumber(toAccountNumber);
		newTransaction.setUserId(userId);
		newTransaction.setTime(System.currentTimeMillis());
		newTransaction.setStatus("Pending");
		newTransaction.setType("moneyTransfer");
		addTransaction(newTransaction);
		return true;
	}

	@Override
	public boolean selfDeposit(Integer userId,Integer accountNumber, Double amount) throws BMException 
	{
		banking = getStorage();
		Utilities.INST.isNull(userId);
		Utilities.INST.isNull(accountNumber);
		Utilities.INST.isNull(amount);
		TransactionInfo newTransaction = new TransactionInfo();
		newTransaction.setAmount(amount);
		newTransaction.setReceiverAccountNumber(accountNumber);
		newTransaction.setSenderAccountNumber(accountNumber);
		newTransaction.setUserId(userId);
		newTransaction.setTime(System.currentTimeMillis());
		newTransaction.setStatus("Pending");
		newTransaction.setType("selfDeposit");
		addTransaction(newTransaction);
		return true;
	}

	@Override
	public boolean othersDeposit(Integer accountNumber, Double amount) throws BMException 
	{		
		banking = getStorage();
		Utilities.INST.isNull(accountNumber);
		Utilities.INST.isNull(amount);
		banking.otherDeposit(accountNumber, amount);
		TransactionInfo newTransaction = new TransactionInfo();
		newTransaction.setAmount(amount);
		newTransaction.setReceiverAccountNumber(accountNumber);
		newTransaction.setSenderAccountNumber(accountNumber);
		newTransaction.setUserId(1000);
		newTransaction.setTime(System.currentTimeMillis());
		newTransaction.setStatus("Pending");
		newTransaction.setType("otherDeposit");
		addTransaction(newTransaction);
		return true;
	}


	@Override
	public UserInfo getProfileInfo(Integer userId) throws BMException 
	{
		banking = getStorage();
		Utilities.INST.isNull(userId);
		UserInfo userInfo = banking.getUserInfo(userId);
		return userInfo;
	}

	@Override
	public Map<?, ?> getMyAccountInfo(Integer userId) throws BMException 
	{
		banking = getStorage();
		Utilities.INST.isNull(userId);
		return banking.getMyAccountsInfo(userId);
	}

	@Override
	public AccountsInfo getMyAccountInfo(Integer userId, Integer accountNumber) throws BMException 
	{
		banking = getStorage();
		Utilities.INST.isNull(userId);
		Utilities.INST.isNull(accountNumber);
		AccountsInfo accountInfo = banking.getMyAccount(userId, accountNumber);
		return accountInfo;
	}

	@Override
	public boolean closeAccount(Integer userId,AccountsInfo accountsInfo) throws BMException 
	{
		banking = getStorage();
		Utilities.INST.isNull(userId);
		Utilities.INST.isNull(accountsInfo);
		banking.deleteAccount(userId,accountsInfo);
		return true;
	}

	@Override
	public boolean updateMobileNumber(Integer userId, Integer phoneNumber) throws BMException 
	{
		banking = getStorage();
		Utilities.INST.isNull(userId);
		Utilities.INST.isNull(phoneNumber);
		banking.changeMobileNumber(userId, phoneNumber);
		return true;
	}

	@Override
	public boolean updateEmail(Integer userId, String email) throws BMException 
	{
		banking = getStorage();
		Utilities.INST.isNull(userId);
		Utilities.INST.isNull(email);
		Utilities.INST.isEmail(email);
		banking.changeEmail(userId, email);
		return true;
	}

	@Override
	public boolean updateAadhar(Integer userId, Integer aadharNumber) throws BMException 
	{
		banking = getStorage();
		Utilities.INST.isNull(userId);
		Utilities.INST.isNull(aadharNumber);
		banking.changeAadhar(userId, aadharNumber);
		return true;
	}

	@Override
	public boolean updateName(Integer userId, String name) throws BMException 
	{
		banking = getStorage();
		Utilities.INST.isNull(userId);
		Utilities.INST.isNull(name);
		banking.changeName(userId, name);
		return true;
	}

	@Override
	public boolean updateRole(Integer userId, String role) throws BMException 
	{
		banking = getStorage();
		Utilities.INST.isNull(userId);
		Utilities.INST.isNull(role);
		banking.changeRole(userId, role);
		return true;
	}

	@Override
	public boolean updatePassword(Integer userId, String oldPassword, String newPassword) throws BMException 
	{
		banking = getStorage();
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
		banking = getStorage();
		Utilities.INST.isNull(userId);
		Utilities.INST.isNull(city);
		banking.changeCity(userId, city);
		return true;
	}

	@Override
	public boolean changeType(Integer accountNumber, String type) throws BMException 
	{
		banking = getStorage();
		Utilities.INST.isNull(accountNumber);
		Utilities.INST.isNull(type);
		banking.changeType(accountNumber, type);
		return true;
	}

	@Override
	public boolean changeAtmPin(Integer userId,Integer accountNumber, Integer oldPin, Integer newPin) throws BMException 
	{
		banking = getStorage();
		Utilities.INST.isNull(userId);
		Utilities.INST.isNull(accountNumber);
		Utilities.INST.isNull(oldPin);
		Utilities.INST.isNull(newPin);
		banking.updateAtmPin(userId, accountNumber, oldPin, newPin);
		return true;
	}
	@Override
	public boolean withDraw(Integer userId, Integer accountNumber, Double amount) throws BMException 
	{
		banking = getStorage();
		Utilities.INST.isNull(userId);
		Utilities.INST.isNull(accountNumber);
		Utilities.INST.isNull(amount);
		TransactionInfo newTransaction = new TransactionInfo();
		newTransaction.setAmount(amount);
		newTransaction.setReceiverAccountNumber(accountNumber);
		newTransaction.setSenderAccountNumber(accountNumber);
		newTransaction.setUserId(userId);
		newTransaction.setTime(System.currentTimeMillis());
		newTransaction.setStatus("Pending");
		newTransaction.setType("withDraw");
		addTransaction(newTransaction);
		return true;
	}
	@Override
	public void addTransaction(TransactionInfo transactionInfo) throws BMException 
	{
		Utilities.INST.isNull(transactionInfo);
		transaction.addTransaction(transactionInfo);
	}
	@Override
	public TransactionInfo getTransaction(Integer transactionId) throws BMException 
	{
		Utilities.INST.isNull(transactionId);
		return transaction.getTransaction(transactionId);
	}
	@Override
	public Map<?, ?> getAllTransaction() throws BMException {
		
		return transaction.getAllTransaction();
	}
	@Override
	public void grantApproval(Integer transactionId) throws BMException 
	{
		Utilities.INST.isNull(transactionId);
		transaction.grantApproval(transactionId);
		
	}
	
} 
