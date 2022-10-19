/**
 * 
 */
package com.credence.bank.routes;

import java.util.Map;

import com.credence.bank.authentication.Authentication;
import com.credence.bank.banking.Storage;
import com.credence.bank.controller.Transaction;
import com.credence.bank.controller.TransactionRouter;
import com.credence.bank.info.AccountsInfo;
import com.credence.bank.info.TransactionInfo;
import com.credence.bank.info.UserInfo;
import com.credence.bank.util.BMException;
import com.credence.bank.util.Credence;
import com.credence.bank.util.Utilities;

/**
 * @author Balamurugan
 *
 */
public class BankingRouterProvider implements BankingRouter
{
	
	public BankingRouterProvider(int userId) 
	{
		this.userId = userId;
	}
	private int userId;
	private static Storage banking;
	private Authentication auth = new Authentication();
	private static Transaction transaction = new TransactionRouter();
	
	private static Storage getStorage() throws BMException 
	{
		if(banking == null)
		{
			banking = Utilities.INST.getStorage();
		}
		return banking;
	}
	private boolean isAuthenticated() throws BMException
	{
		if(auth.isAuthenticated(userId)) 
		{
			return true;
		}
		throw new BMException("User is not authenticated");
	}
	private boolean isAdmin() throws BMException
	{
		if(auth.isAdmin(userId))
		{
			return true;
		}
		throw new BMException("Not an Admin user");
	}
	@Override
	public boolean addUser(UserInfo userInfo) throws BMException 
	{
		this.isAuthenticated();
		this.isAdmin();
		banking = getStorage();
		Utilities.INST.isNull(userInfo);
		banking.createUser(userInfo);
		return true;
	}
	
	@Override
	public boolean createAccount(Integer userId, AccountsInfo accountInfo) throws BMException
	{
		this.isAuthenticated();
		banking = getStorage();
		Utilities.INST.isNull(userId);
		Utilities.INST.isNull(accountInfo);
		banking.createAccount(userId, accountInfo);///review
		return true;
	}

	@Override
	public boolean removeUser(Integer userId) throws BMException
	{
		this.isAuthenticated();
		this.isAdmin();
		Utilities.INST.isNull(userId);
		UserInfo user = getProfileInfo(userId);
		String access = user.getAdminAccess();
		if(access.equals(Credence.Access.USER.getAccess()))
		{
			banking = getStorage();
			banking.removeUser(userId);
			return true;
		}
		throw new BMException("Admin user Cannot able to delete");
	}

	@Override
	public Double checkBalance(Integer userId,Integer accountNumber) throws BMException 
	{
		this.isAuthenticated();
		banking = getStorage();
		Utilities.INST.isNull(userId);
		Utilities.INST.isNull(accountNumber);
		return banking.getBalance(userId, accountNumber);
	}

	@Override
	public boolean selfTransfer(Integer userId,Integer fromAccountNumber, Integer toAccountNumber, Double amount) throws BMException 
	{		
		this.isAuthenticated();
		banking = getStorage();
		Utilities.INST.isNull(userId);
		Utilities.INST.isNull(fromAccountNumber);
		Utilities.INST.isNull(toAccountNumber);
		Utilities.INST.isNull(amount);
		TransactionInfo newTransaction = new TransactionInfo();
		newTransaction.setAmount(amount);
		newTransaction.setReceiverAccountNumber(toAccountNumber);
		newTransaction.setSenderAccountNumber(fromAccountNumber);
		newTransaction.setUserId(userId);
		newTransaction.setTime(System.currentTimeMillis());
		newTransaction.setStatus(Credence.Status.PENDING.getStatus());
		newTransaction.setType(Credence.TransactionType.MONEYTRANSFER.getType());
		addTransaction(newTransaction);
		return true;
	}

	@Override
	public boolean othersTransfer(Integer userId,Integer fromAccountNumber, Integer toAccountNumber, Double amount) throws BMException 
	{
		this.isAuthenticated();
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
		newTransaction.setStatus(Credence.Status.PENDING.getStatus());
		newTransaction.setType(Credence.TransactionType.MONEYTRANSFER.getType());
		addTransaction(newTransaction);
		return true;
	}

	@Override
	public boolean selfDeposit(Integer userId,Integer accountNumber, Double amount) throws BMException 
	{
		this.isAuthenticated();
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
		newTransaction.setStatus(Credence.Status.PENDING.getStatus());
		newTransaction.setType(Credence.TransactionType.SELFDEPOSIT.getType());
		addTransaction(newTransaction);
		return true;
	}

	@Override
	public boolean othersDeposit(Integer accountNumber, Double amount) throws BMException 
	{		
		this.isAuthenticated();
		banking = getStorage();
		Utilities.INST.isNull(accountNumber);
		Utilities.INST.isNull(amount);
		banking.otherDeposit(accountNumber, amount);
		TransactionInfo newTransaction = new TransactionInfo();
		newTransaction.setAmount(amount);
		newTransaction.setReceiverAccountNumber(accountNumber);
		newTransaction.setSenderAccountNumber(accountNumber);
		newTransaction.setUserId(null);
		newTransaction.setTime(System.currentTimeMillis());
		newTransaction.setStatus(Credence.Status.PENDING.getStatus());
		newTransaction.setType(Credence.TransactionType.OTHERDEPOSIT.getType());
		addTransaction(newTransaction);
		return true;
	}


	@Override
	public UserInfo getProfileInfo(Integer userId) throws BMException 
	{
		this.isAuthenticated();
		banking = getStorage();
		Utilities.INST.isNull(userId);
		UserInfo userInfo = banking.getUserInfo(userId);
		return userInfo;
	}

	@Override
	public Map<?, ?> getMyAccountInfo(Integer userId) throws BMException 
	{
		this.isAuthenticated();
		banking = getStorage();
		Utilities.INST.isNull(userId);
		return banking.getMyAccountsInfo(userId);
	}

	@Override
	public AccountsInfo getMyAccountInfo(Integer userId, Integer accountNumber) throws BMException 
	{
		this.isAuthenticated();
		banking = getStorage();
		Utilities.INST.isNull(userId);
		Utilities.INST.isNull(accountNumber);
		AccountsInfo accountInfo = banking.getMyAccount(userId, accountNumber);
		return accountInfo;
	}

	@Override
	public boolean closeAccount(Integer userId,AccountsInfo accountsInfo) throws BMException 
	{
		this.isAuthenticated();
		banking = getStorage();
		Utilities.INST.isNull(userId);
		Utilities.INST.isNull(accountsInfo);
		banking.deleteAccount(userId,accountsInfo);
		return true;
	}

	@Override
	public boolean updateMobileNumber(Integer userId, Integer phoneNumber) throws BMException 
	{
		this.isAuthenticated();
		banking = getStorage();
		Utilities.INST.isNull(userId);
		Utilities.INST.isNull(phoneNumber);
		banking.changeMobileNumber(userId, phoneNumber);
		return true;
	}

	@Override
	public boolean updateEmail(Integer userId, String email) throws BMException 
	{
		this.isAuthenticated();
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
		this.isAuthenticated();
		banking = getStorage();
		Utilities.INST.isNull(userId);
		Utilities.INST.isNull(aadharNumber);
		banking.changeAadhar(userId, aadharNumber);
		return true;
	}

	@Override
	public boolean updateName(Integer userId, String name) throws BMException 
	{
		this.isAuthenticated();
		banking = getStorage();
		Utilities.INST.isNull(userId);
		Utilities.INST.isNull(name);
		banking.changeName(userId, name);
		return true;
	}

	@Override
	public boolean updateRole(Integer userId, String role) throws BMException 
	{
		this.isAuthenticated();
		banking = getStorage();
		Utilities.INST.isNull(userId);
		Utilities.INST.isNull(role);
		banking.changeRole(userId, role);
		return true;
	}

	@Override
	public boolean updatePassword(Integer userId, String oldPassword, String newPassword) throws BMException 
	{
		this.isAuthenticated();
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
		this.isAuthenticated();
		banking = getStorage();
		Utilities.INST.isNull(userId);
		Utilities.INST.isNull(city);
		banking.changeCity(userId, city);
		return true;
	}

	@Override
	public boolean changeType(Integer accountNumber, String type) throws BMException 
	{
		this.isAuthenticated();
		banking = getStorage();
		Utilities.INST.isNull(accountNumber);
		Utilities.INST.isNull(type);
		banking.changeType(accountNumber, type);
		return true;
	}

	@Override
	public boolean changeAtmPin(Integer userId,Integer accountNumber, Integer oldPin, Integer newPin) throws BMException 
	{
		this.isAuthenticated();
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
		this.isAuthenticated();
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
		newTransaction.setStatus(Credence.Status.PENDING.getStatus());
		newTransaction.setType(Credence.TransactionType.WITHDRAW.getType());
		addTransaction(newTransaction);
		return true;
	}
	@Override
	public void addTransaction(TransactionInfo transactionInfo) throws BMException 
	{
		this.isAuthenticated();
		Utilities.INST.isNull(transactionInfo);
		banking = getStorage();
		double amount = transactionInfo.getAmount();
		Integer accountNum = transactionInfo.getSenderAccountNumber();
		double curentBalance = banking.getBalance(accountNum);
		if(curentBalance < amount)
		{
			throw new BMException("Transaction Add failed - Insufficient Balance");
		}
		transaction.addTransaction(transactionInfo);
	}
	@Override
	public TransactionInfo getTransaction(Integer userId,Integer transactionId) throws BMException 
	{
		this.isAuthenticated();
		Utilities.INST.isNull(transactionId);
		UserInfo user = getProfileInfo(userId);
		String access = user.getAdminAccess();
		TransactionInfo myTransaction = transaction.getTransaction(transactionId);
		Integer transUserId = myTransaction.getUserId();
		if(access.equals(Credence.Access.ADMIN.getAccess()) || userId == transUserId)
		{
			return myTransaction;
		}
		else
		{
			throw new BMException("UnAuthorized Access");
		}
	}
	@Override
	public Map<?, ?> getAllTransaction() throws BMException 
	{
		this.isAuthenticated();
		this.isAdmin();
		return transaction.getAllTransaction();
	}
	@Override
	public void grantApproval(Integer transactionId) throws BMException 
	{
		this.isAuthenticated();
		this.isAdmin();
		Utilities.INST.isNull(transactionId);
		transaction.grantApproval(transactionId);
	}
	@Override
	public void setup() throws BMException 
	{
		this.isAuthenticated();
		banking = getStorage();
		banking.setup();
	}
	@Override
	public void saveChanges() throws BMException 
	{
		this.isAuthenticated();
		banking = getStorage();
		banking.saveChanges();
	}
	@Override
	public Map<?, ?> getAllPendingTransaction() throws BMException 
	{
		this.isAuthenticated();
		this.isAdmin();
		return transaction.getAllPendingTransaction();
	}
	@Override
	public void rejectTransaction(Integer transactionId) throws BMException 
	{
		this.isAuthenticated();
		this.isAdmin();
		Utilities.INST.isNull(transactionId);
		transaction.rejectTransaction(transactionId);
	}
	@Override
	public void reActivateUser(Integer userId,Integer requestId) throws BMException 
	{
		this.isAuthenticated();
		this.isAdmin();
		Utilities.INST.isNull(userId);
		banking = getStorage();
		banking.setRequestFlag(Credence.Status.ACTIVE,requestId);
		banking.reActivateUser(userId);
	}
	@Override
	public void reActivateAccount(Integer accountNumber,Integer requestId) throws BMException 
	{
		this.isAuthenticated();
		this.isAdmin();
		Utilities.INST.isNull(accountNumber);
		banking = getStorage();
		banking.setRequestFlag(Credence.Status.ACTIVE,requestId);
		banking.reActivateAccount(accountNumber);
	}
	@Override
	public Map<?,?> getAllUserTransaction(Integer userId) throws BMException 
	{
		this.isAuthenticated();
		banking = getStorage();
		Utilities.INST.isNull(userId);
		return banking.getAllUserTransaction(userId);
	}
	@Override
	public void reActivateAccountRequest(Integer userId, Integer accountNumber) throws BMException 
	{
		banking = getStorage();
		this.isAuthenticated();
		Utilities.INST.isNull(accountNumber);
		Utilities.INST.isNull(userId);
		banking.reActivateAccountRequest(userId, accountNumber);
		
	}
	@Override
	public void reActivateUserRequest(Integer userId) throws BMException 
	{
		banking = getStorage();
		Utilities.INST.isNull(userId);
		banking.reActivateUserRequest(userId);
		
	}
	@Override
	public Map<?, ?> getAllRequests() throws BMException 
	{
		this.isAuthenticated();
		this.isAdmin();
		banking = getStorage();
		return banking.getAllRequest();
	}
	
} 
