/**
 * 
 */
package com.credence.bank.info;

/**
 * @author Balamurugan
 *
 */
public class SessionInfo 
{
	private int userId;
	private long time;
	private String role;
	public int getUserId() 
	{
		return userId;
	}
	public void setUserId(int userId) 
	{
		this.userId = userId;
	}
	public long getTime() 
	{
		return time;
	}
	public void setTime(long time) 
	{
		this.time = time;
	}
	public String getRole() 
	{
		return role;
	}
	public void setRole(String role) 
	{
		this.role = role;
	}
	
}
