/**
 * 
 */
package com.credence.bank.banking;

import java.util.List;
import java.util.Map;

import com.credence.bank.controller.Transaction;
import com.credence.bank.info.AccountsInfo;
import com.credence.bank.info.TransactionInfo;
import com.credence.bank.info.UserInfo;
import com.credence.bank.routes.BankingRouter;
import com.credence.bank.util.BMException;
import com.credence.bank.util.Utilities;

/**
 * @author Balamurugan
 *
 */
public interface Storage
{
	public void setup() throws BMException;
	
	public void saveChanges() throws BMException;
	// adding dump data to storage layer
	public void dumpUserProfileData(List<UserInfo> userInfo) throws BMException;
	
	// adding dump to storage layer
	public void dumpAccountsData(List<AccountsInfo> accountsInfo) throws BMException;
	
	// Retrieve data from layer
	public UserInfo getUserInfo(Integer userId) throws BMException;
	
	// return your account
	public AccountsInfo getMyAccount(Integer userId,Integer accountNumber) throws BMException;
	
	// return your current balance
	public Double getBalance(Integer userId,Integer accountNumber) throws BMException;
	
	// deposit to your personal account
	public void selfDeposit(Integer userId,Integer accountNumber,Double amount) throws BMException;
	
	// deposit to another account
	public void otherDeposit(Integer accountNumber, Double amount) throws BMException;
	
	// withdraw money from your account
	public void withDraw(Integer userId,Integer accountNumber,Double amount) throws BMException;
	
	// change your email
	public void changeEmail(Integer userId,String email) throws BMException;
	
	// change your mobile number
	public void changeMobileNumber(Integer userId,Integer number) throws BMException;
	
	// change your password
	public void updatePassword(Integer userId,String oldPassword,String newPassword) throws BMException;

	// change your update name
	public void changeName(Integer userId,String name) throws BMException;
	
	// change your update role
	public void changeRole(Integer userId,String role) throws BMException;
		
	// change your update city
	public void changeCity(Integer userId,String name) throws BMException;

	// change your update type
	public void changeType(Integer userId,String type) throws BMException;
	
	// change your update aadhar
	public void changeAadhar(Integer userId,Integer aadharNumber) throws BMException;
	
	// transfer your money from your account to another account
	public void moneyTransfer(Integer userId,Integer senderAccountNo,Integer receiverAccountNo,Double amount) throws BMException;
	
	// change atm pin
	public void updateAtmPin(Integer userId,Integer accountNumber,Integer oldPin,Integer newPin) throws BMException;
	
	//return map of accounts for a given user
	public Map<?,?> getMyAccountsInfo(Integer userId) throws BMException;
	/**
	 * 
	 * @param userInfo
	 * @throws BMException
	 */
	public void createUser(UserInfo userInfo) throws BMException;
	/**
	 * 
	 * @param userId
	 * @throws BMException
	 */
	public void removeUser(Integer userId) throws BMException;
	/**
	 * 
	 * @param userId
	 * @param accountsInfo
	 * @throws BMException
	 */
	public void createAccount(Integer userId,AccountsInfo accountsInfo) throws BMException;
	/**
	 * 
	 * @param userId
	 * @param accountsInfo
	 * @throws BMException
	 */
	public void deleteAccount(Integer userId,AccountsInfo accountsInfo) throws BMException;
	
	/**
	 * 
	 * @param transactionInfo
	 * @throws BMException 
	 */
	public void addTransaction(TransactionInfo transactionInfo) throws BMException;
	
	/**
	 * 
	 * @param transactionId 
	 * @return TransactionInfo {@link TransactionInfo}
	 * @throws BMException 
	 */
	public TransactionInfo getTransaction(Integer transactionId) throws BMException;
	
	/**
	 * 
	 * @param userId
	 * @return Map of transactions 
	 * @throws BMException 
	 * @implNote admin only method
	 */
	public Map<?,?> getAllTransaction() throws BMException;
	
	/**
	 * @category approving users request.
	 * @param requestId
	 * @throws BMException 
	 * @category admin Specific method.
	 */
	public void grantApproval(Integer transactionId) throws BMException;
	
	default
	public boolean login(Integer userId,String email, String password) throws BMException 
	{
		Utilities.INST.isNull(email);
		Utilities.INST.isNull(password);
		Utilities.INST.isEmail(email);
		Utilities.INST.isPassword(password);
		UserInfo user = getUserInfo(userId);
		if(user == null)
		{
			throw new BMException("User not found");
		}
		String orgEmail = user.getEmail();
		String orgPassword = user.getPassword();
		if(email.equals(orgEmail) && password.equals(orgPassword))
		{
			return true;
		}
		return false;
	}
	
}
