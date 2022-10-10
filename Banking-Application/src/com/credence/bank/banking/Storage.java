/**
 * 
 */
package com.credence.bank.banking;

import java.util.List;
import java.util.Map;

import com.credence.bank.info.AccountsInfo;
import com.credence.bank.info.UserInfo;
import com.credence.bank.util.BMException;

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
	public Integer getBalance(Integer userId,Integer accountNumber) throws BMException;
	
	// deposit to your personal account
	public void selfDeposit(Integer userId,Integer accountNumber,Integer amount) throws BMException;
	
	// deposit to another account
	public void otherDeposit(Integer accountNumber, Integer amount) throws BMException;
	
	// withdraw money from your account
	public void withDraw(Integer userId,Integer accountNumber,Integer amount) throws BMException;
	
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
	public void moneyTransfer(Integer userId,Integer senderAccountNo,Integer receiverAccountNo,Integer amount) throws BMException;
	
	// change atm pin
	public void updateAtmPin(Integer userId,Integer accountNumber,Integer oldPin,Integer newPin) throws BMException;
	
	//return map of accounts for a given user
	public Map<?,?> getMyAccountsInfo(Integer userId) throws BMException;
		
	public void createUser(UserInfo userInfo) throws BMException;
	
	public void removeUser(Integer userId) throws BMException;
	
	public void createAccount(Integer userId,AccountsInfo accountsInfo) throws BMException;
	
	public void deleteAccount(Integer userId,AccountsInfo accountsInfo) throws BMException;
	
}
