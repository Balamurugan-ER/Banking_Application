/**
 * 
 */
package com.credence.bank.info;

/**
 * @author Balamurugan
 *
 */
public class RequestInfo 
{
	private int id;
	private int userId;
	private String type;
	private String Status;
	private int accountNumber;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getStatus() {
		return Status;
	}
	public void setStatus(String status) {
		Status = status;
	}
	public int getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(int accountNumber) {
		this.accountNumber = accountNumber;
	}
	public String toString()
	{
		return "RequestId 		:"+getId()+"\n"
				+ "UserId			:"+getUserId()+"\n"
				+ "Type			:"+getType()+"\n"
				+ "Status			:"+getStatus()+"\n"
				+ "AccountNumber		:"+getAccountNumber()+"\n";
	}
}
