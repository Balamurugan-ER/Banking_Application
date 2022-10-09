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
	//TODO add user
	public Integer addUser(UserInfo userInfo) throws BMException;
	
	//TODO create account
	public Integer createAccount(Integer userId,AccountsInfo accountInfo) throws BMException;
	
	//TODO remove user
	public Integer removeUser(Integer userId) throws BMException;
	
	//TODO check balance
	public Integer checkBalance(Integer userId,Integer accountNumber) throws BMException;
	
	// withdraw money from your account
	public Integer withDraw(Integer userId,Integer accountNumber,Integer amount) throws BMException;
	
	//TODO money transfer self 
	//Hint same user different account
	public Integer selfTransfer(Integer userId,Integer fromAccountNumber,Integer toAccountNumber,Integer amount) throws BMException;
	
	//TODO money transfer others
	public Integer othersTransfer(Integer userId,Integer fromAccountNumber,Integer toAccountNumber,Integer amount) throws BMException;
	
	//TODO self deposit
	public Integer selfDeposit(Integer userId,Integer accountNumber,Integer amount) throws BMException;
	
	//TODO deposit others
	// transfer only using account number
	public Integer othersDeposit(Integer accountNumber,Integer amount) throws BMException;
	
	//TODO money fixed deposit
	public Integer fixedDeposit(Integer accountNumber,Integer amount) throws BMException;
	
	//TODO display profile userInfo
	public UserInfo getProfileInfo(Integer userId) throws BMException;
	
	//TODO display user Accounts Information
	public Map<?,?> getMyAccountInfo(Integer userId) throws BMException;
	
	//TODO display user Account Information
	public AccountsInfo getMyAccountInfo(Integer userId,Integer accountNumber) throws BMException;
		
	//TODO close account
	public Integer closeAccount(Integer userId,AccountsInfo accountsInfo) throws BMException;
	
	//TODO change mobile number
	public Integer updateMobileNumber(Integer userId,Integer phoneNumber) throws BMException;
	
	//TODO change email id
	public Integer updateEmail(Integer userId,String email) throws BMException;
	
	//TODO update aadhar number
	public Integer updateAadhar(Integer userId,Integer aadharNumber) throws BMException;
	
	//TODO update name
	public Integer updateName(Integer userId,String name) throws BMException;
	
	//TODO change role
	public Integer updateRole(Integer userId,String role) throws BMException;
	
	//TODO change password
	public Integer updatePassword(Integer userId,String oldPassword,String newPassword) throws BMException;
	
	//TODO change city
	public Integer changeCity(Integer userId,String city) throws BMException;
	
	//TODO change type of account
	public Integer changeType(Integer accountNumber,String type) throws BMException;
	
	//TODO update atmpin
	public Integer changeAtmPin(Integer userId,Integer accountNumber, Integer oldPin, Integer newPin) throws BMException;
}
