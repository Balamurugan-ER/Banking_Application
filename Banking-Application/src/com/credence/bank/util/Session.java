/**
 * 
 */
package com.credence.bank.util;

import java.util.HashMap;
import java.util.Map;

import com.credence.bank.info.SessionInfo;

/**
 * @author Balamurugan
 *
 */
public enum Session 
{
	INST;
	private Map<Integer,SessionInfo> sessionTable = new HashMap<>();
	public void addEntry(Integer userId,SessionInfo session)
	{
		sessionTable.put(userId, session);
	}
	
	public SessionInfo getEntryAuth(Integer userId)
	{
		return sessionTable.get(userId);
	}
}
