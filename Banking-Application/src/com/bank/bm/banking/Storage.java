/**
 * 
 */
package com.bank.bm.banking;

import com.bank.bm.pojo.AccountsInfo;
import com.bank.bm.pojo.UserInfo;

/**
 * @author Balamurugan
 *
 */
public interface Storage 
{
	// adding dump data to storage layer
	public void addDumpData();
	
	// Retrieve data from layer
	public UserInfo getUserInfo(Integer userId);
	
	// 
	public AccountsInfo getMyAccount(Integer userId,Long accountNumber);
	
	public Integer getBalance(Integer userId,Long accountNumber);
	
	public void deposit(Integer userId,Long accountNumber,Integer amount);
	
	public void withDraw(Integer userId,Long accountNumber,Integer amount);
	
	public void changeEmail(Integer userId,String email);
	
	public void changeMobileNumber(Integer userId,Integer number);
	
	public void moneyTransfer(Integer userId,Integer senderAccountNo,Integer receiverAccountNo,Integer amount);
	
	
}
