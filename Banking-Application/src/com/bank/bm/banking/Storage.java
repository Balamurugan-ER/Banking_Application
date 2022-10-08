/**
 * 
 */
package com.bank.bm.banking;

import com.bank.bm.info.AccountsInfo;
import com.bank.bm.info.UserInfo;
import com.bank.bm.util.BMException;

/**
 * @author Balamurugan
 *
 */
public interface Storage 
{
//	// adding dump data to storage layer
//	public void dumpUserProfileData();
//	
//	// adding dump to storage layer
//	public void dumpAccountsData();
	
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
	
	// transfer your money from your account to another account
	public void moneyTransfer(Integer userId,Integer senderAccountNo,Integer receiverAccountNo,Integer amount) throws BMException;
	
	// change atm pin
	public void updateAtmPin(Integer userId,Integer accountNumber,Integer oldPin,Integer newPin) throws BMException;
	
}
