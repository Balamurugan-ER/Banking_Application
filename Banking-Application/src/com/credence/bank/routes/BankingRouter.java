/**
 * 
 */
package com.credence.bank.routes;

import java.util.Map;

import com.credence.bank.info.AccountsInfo;
import com.credence.bank.info.UserInfo;
import com.credence.bank.util.BMException;

/**
 * @author Balamurugan
 *
 */
public interface BankingRouter 
{
	/**
	 * 
	 * @param userInfo
	 * @return
	 * @throws BMException
	 */
	public boolean addUser(UserInfo userInfo) throws BMException;
	
	public boolean createAccount(Integer userId,AccountsInfo accountInfo) throws BMException;
	
	//TODO remove user
	public boolean removeUser(Integer userId) throws BMException;
	
	//TODO check balance
	public Integer checkBalance(Integer userId,Integer accountNumber) throws BMException;
	
	// withdraw money from your account
	public boolean withDraw(Integer userId,Integer accountNumber,Integer amount) throws BMException;
	
	//TODO money transfer self 
	//Hint same user different account
	public boolean selfTransfer(Integer userId,Integer fromAccountNumber,Integer toAccountNumber,Integer amount) throws BMException;
	
	//TODO money transfer others
	public boolean othersTransfer(Integer userId,Integer fromAccountNumber,Integer toAccountNumber,Integer amount) throws BMException;
	
	//TODO self deposit
	public boolean selfDeposit(Integer userId,Integer accountNumber,Integer amount) throws BMException;
	
	//TODO deposit others
	// transfer only using account number
	public boolean othersDeposit(Integer accountNumber,Integer amount) throws BMException;
	
	//TODO display profile userInfo
	public UserInfo getProfileInfo(Integer userId) throws BMException;
	
	//TODO display user Accounts Information
	public Map<?,?> getMyAccountInfo(Integer userId) throws BMException;
	
	//TODO display user Account Information
	public AccountsInfo getMyAccountInfo(Integer userId,Integer accountNumber) throws BMException;
		
	//TODO close account
	public boolean closeAccount(Integer userId,AccountsInfo accountsInfo) throws BMException;
	
	//TODO change mobile number
	public boolean updateMobileNumber(Integer userId,Integer phoneNumber) throws BMException;
	
	//TODO change email id
	public boolean updateEmail(Integer userId,String email) throws BMException;
	
	//TODO update aadhar number
	public boolean updateAadhar(Integer userId,Integer aadharNumber) throws BMException;
	
	//TODO update name
	public boolean updateName(Integer userId,String name) throws BMException;
	
	//TODO change role
	public boolean updateRole(Integer userId,String role) throws BMException;
	
	//TODO change password
	public boolean updatePassword(Integer userId,String oldPassword,String newPassword) throws BMException;
	
	//TODO change city
	public boolean changeCity(Integer userId,String city) throws BMException;
	
	//TODO change type of account
	public boolean changeType(Integer accountNumber,String type) throws BMException;
	
	//TODO update atmpin
	public boolean changeAtmPin(Integer userId,Integer accountNumber, Integer oldPin, Integer newPin) throws BMException;
}
