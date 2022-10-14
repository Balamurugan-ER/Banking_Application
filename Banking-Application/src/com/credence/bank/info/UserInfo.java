/**
 * 
 */
package com.credence.bank.info;

/**
 * @author Balamurugan
 *
 */
public class UserInfo 
{
	private int userId;
	private String name;
	private String password;
	private String email;
	private long phone;
	private long aadhar;
	private String city;
	private String role;
	private String status;
	private String adminAccess;
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public long getPhone() {
		return phone;
	}
	public void setPhone(long phone) {
		this.phone = phone;
	}
	public long getAadhar() {
		return aadhar;
	}
	public void setAadhar(long aadhar) {
		this.aadhar = aadhar;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) 
	{
		this.status = status;
	}
	public String getAdminAccess() 
	{
		return adminAccess;
	}
	public void setAdminAccess(String adminAccess) {
		this.adminAccess = adminAccess;
	}
	@Override
	public String toString()
	{
		String row = "{\n"
				+ "UserId 		: "+getUserId()+",\n"
				+ "Name 		: "+getName()+",\n"
				+ "Email 		:"+getEmail()+",\n"
				+ "Phone 		:"+getPhone()+",\n"
				+ "Role			:"+getRole()+",\n"
				+ "Aadhar  		:"+getAadhar()+",\n"
				+ "City 		:"+getCity()+",\n"
				+ "Status		:"+getStatus()+",\n"
				+ "AdminAccess	:"+getAdminAccess()+"\n}";
		return row;
	}
	
}
