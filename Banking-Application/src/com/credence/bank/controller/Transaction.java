/**
 * 
 */
package com.credence.bank.controller;

import java.util.Map;

import com.credence.bank.info.TransactionInfo;
import com.credence.bank.util.BMException;

/**
 * @author Balamurugan
 *
 */
public interface Transaction 
{
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
	
	
	public Map<?, ?> getAllPendingTransaction() throws BMException;
	
	/**
	 * @category approving users request.
	 * @param requestId
	 * @throws BMException 
	 * @category admin Specific method.
	 */
	public void grantApproval(Integer transactionId) throws BMException;
	
	public void rejectTransaction(Integer transactionId) throws BMException;
	
}
