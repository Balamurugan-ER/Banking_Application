/**
 * 
 */
package com.credence.bank.controller;

import java.util.Map;

import com.credence.bank.banking.Storage;
import com.credence.bank.info.TransactionInfo;
import com.credence.bank.util.BMException;
import com.credence.bank.util.Utilities;

/**
 * @author Balamurugan
 *
 */
public class TransactionRouter implements Transaction
{
	
	private static Storage banking;
	
	private static Storage getStorage() throws BMException 
	{
		if(banking == null)
		{
			banking = Utilities.INST.getStorage();
		}
		return banking;
	}
	@Override
	public void addTransaction(TransactionInfo transactionInfo) throws BMException 
	{
		Utilities.INST.isNull(transactionInfo);
		banking = getStorage();
		banking.addTransaction(transactionInfo);
	}

	@Override
	public TransactionInfo getTransaction(Integer transactionId) throws BMException
	{
		Utilities.INST.isNull(transactionId);
		banking = getStorage();
		return banking.getTransaction(transactionId);
	}

	public Map<?, ?> getAllTransaction() throws BMException
	{
		banking = getStorage();
		return banking.getAllTransaction();
	}

//	public void grantApproval(Integer transactionId) throws BMException
//	{
//		banking = getStorage();
//		banking.grantApproval(transactionId);
//		TransactionInfo thisTransaction = getTransaction(transactionId);
//	
//		if(thisTransaction.getStatus().equals("Approved"))
//		{
//			if(thisTransaction.getType().equals("selfDeposit"))
//			{
//				banking.selfDeposit(thisTransaction.getUserId(), thisTransaction.getReceiverAccountNumber(), thisTransaction.getAmount());
//			}
//			if(thisTransaction.getType().equals("otherDeposit"))
//			{
//				banking.otherDeposit(thisTransaction.getReceiverAccountNumber(), thisTransaction.getAmount());
//			}
//			if(thisTransaction.getType().equals("withDraw"))
//			{
//				banking.withDraw(thisTransaction.getUserId(), thisTransaction.getReceiverAccountNumber(), thisTransaction.getAmount());
//			}
//			if(thisTransaction.getType().equals("moneyTransfer"))
//			{
//				banking.moneyTransfer(thisTransaction.getUserId(), thisTransaction.getSenderAccountNumber(), thisTransaction.getReceiverAccountNumber(), thisTransaction.getAmount());
//			}
//		}
//	}

	public void grantApproval(Integer transactionId) throws BMException
	{
		Utilities.INST.isNull(transactionId);
		banking = getStorage();
		banking.grantApproval(transactionId);
		TransactionInfo thisTransaction = getTransaction(transactionId);
		String status = thisTransaction.getStatus();
		String type = thisTransaction.getType();
		double amount = thisTransaction.getAmount();
		int userId = thisTransaction.getUserId();
		int receiverAccountNumber = thisTransaction.getReceiverAccountNumber();
		int senderAccountNumber = thisTransaction.getSenderAccountNumber();
		if(status.equals("Approved"))
		{
			if(type.equals("selfDeposit"))//swami enum
			{
				banking.selfDeposit(userId, receiverAccountNumber, amount);
			}
			if(type.equals("otherDeposit"))
			{
				banking.otherDeposit(receiverAccountNumber, amount);
			}
			if(type.equals("withDraw"))
			{
				banking.withDraw(userId, receiverAccountNumber, amount);
			}
			if(type.equals("moneyTransfer"))
			{
				banking.moneyTransfer(userId, senderAccountNumber, receiverAccountNumber, amount);
			}
		}
	}

}
