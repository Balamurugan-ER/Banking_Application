/**
 * 
 */
package com.credence.bank.controller;

import java.util.Map;

import com.credence.bank.info.RequestInfo;

/**
 * @author Balamurugan
 *
 */
public interface RequestOperation 
{
	/**
	 * @category user requesting for an operation to complete.
	 * @param requestInfo RequestInfo Object.
	 * @category user Specific method
	 */
	public void requestForApproval(RequestInfo requestInfo);
	
	
	/**
	 * @category approving users request.
	 * @param requestId
	 * @category admin Specific method.
	 */
	public void grantApproval(Integer requestId);
	
	/**
	 * @category viewing users requests Information.
	 * @category admin Specific method.
	 */
	public Map<?,?> getAllRequests();
}
