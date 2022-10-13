/**
 * 
 */
package com.credence.bank.info;

import java.sql.Timestamp;

/**
 * @author Balamurugan
 *
 */
public class TransactionInfo 
{
	private Integer userId;
	private Integer transactionId;
	private Integer senderAccountNumber;
	private Integer receiverAccountNumber;
	private double amount;
	private String status;
	private long time;
	private String type;
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(Integer transactionId) {
		this.transactionId = transactionId;
	}
	public Integer getSenderAccountNumber() {
		return senderAccountNumber;
	}
	public void setSenderAccountNumber(Integer senderAccountNumber) {
		this.senderAccountNumber = senderAccountNumber;
	}
	public Integer getReceiverAccountNumber() {
		return receiverAccountNumber;
	}
	public void setReceiverAccountNumber(Integer receiverAccountNumber) {
		this.receiverAccountNumber = receiverAccountNumber;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String toString()
	{
		return "{\n"
				+ "TransactionId	: "+ getTransactionId()+","
				+ "SenderAccount 	: "+getSenderAccountNumber()+",\n"
				+ "ReceiverAccount	: "+getReceiverAccountNumber()+",\n"
				+ "Transaction Status : "+getStatus()+",\n"
				+ "Transaction Type	: "+getType()+",\n"
				+ "Amount			:"+getAmount()+",\n"
				+ "Time	:"+getTime()			
				+ "\n}";
	}
}
