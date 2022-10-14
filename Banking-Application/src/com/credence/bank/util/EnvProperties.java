/**
 * 
 */
package com.credence.bank.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

/**
 * @author Balamurugan
 *
 */
public enum EnvProperties 
{
	INST;
	public Properties envProps = new Properties();
	private String propsName = "EnvProps.properties";
	public void writingProps()
	{
		envProps.setProperty("url", "jdbc:mysql://localhost:3306/incubationDB");
		envProps.setProperty("username", "root");
		envProps.setProperty("password", "Root@123");
		envProps.setProperty("storage", "com.credence.bank.banking.DBBanking");
//		envProps.setProperty("storage", "com.credence.bank.banking.FBanking");
		try 
		{
			OutputStream writeProps = new FileOutputStream(propsName);
			envProps.store(writeProps, "Writing Props");
		}
		catch (FileNotFoundException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void loadProps()
	{
		try 
		{
			InputStream loadProps = new FileInputStream(propsName);
			envProps.load(loadProps);
		}
		catch (FileNotFoundException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
