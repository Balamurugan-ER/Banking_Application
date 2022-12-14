/**
 * 
 */
package com.credence.bank.routes;

import java.util.Map;

import com.credence.bank.info.AccountsInfo;
import com.credence.bank.info.TransactionInfo;
import com.credence.bank.info.UserInfo;
import com.credence.bank.util.BMException;

/**
 * @author Balamurugan
 *
 */
public interface BankingRouter extends UserRouter //should authenticate and authorise for each methods
{	
	public Map<?,?> getAllTransaction()throws BMException;
	
	public Map<?, ?> getAllPendingTransaction() throws BMException;
	
	public void grantApproval(Integer transactionId)throws BMException;
	
	public void rejectTransaction(Integer transactionId) throws BMException;
	
	public void reActivateUser(Integer userId) throws BMException;
	
	public void reActivateAccount(Integer accountNumber) throws BMException;
}
