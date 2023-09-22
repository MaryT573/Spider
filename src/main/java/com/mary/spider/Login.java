package com.mary.spider;

import java.io.IOException;

//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
import java.util.LinkedList;
import java.util.List;


/****************************************************************************
 * <b>Title:</b> Login.java
 * <b>Project:</b> spider-lib
 * <b>Description:</b> CHANGE ME!!
 * <b>Copyright:</b> Copyright (c) 2023
 * <b>Company:</b> Silicon Mountain Technologies
 * 
 * @author MaryT
 * @version 3.x
 * @since Sep 19, 2023
 * <b>updates:</b>
 *  
 ****************************************************************************/

/**
 * @author mary
 *
 */
public class Login {
	List<String> cookies = new LinkedList<>();
	
	/**
	 * Method getPost creates builds the post request and initializes a connection - sends header info onto
	 * getCookies
	 * @return html
	 * @throws IOException
	 */
	public StringBuilder sendPost() throws IOException {
		StringBuilder html = new StringBuilder();
		//creates post request
		String request = "POST " + "/admintool" + " HTTP/1.1\r\n" + "Host: " + "smt-stage.qa.siliconmtn.com" + ":" + 443 + "\r\n" 
						+ "Content-Type: application/x-www-form-urlencoded" + "\r\n" + "Content-Length: " + 97 + "\r\n"
						+ "\r\n" + "requestType=reqBuild&pmid=ADMIN_LOGIN&emailAddress=" +System.getenv("email")
						+ "&password="+ System.getenv("password")+ "&l=" + "\r\n" + "\r\n";
		Connection connect = new Connection();
		html = connect.getConnect("smt-stage.qa.siliconmtn.com", 443, request);
		System.out.println(html);
		//getCookies(html);
		return html;
	} 
	
	//here for testing purposes
	//public static void main(String[] args) throws IOException  {
		//System.out.println("start");
		//Login login = new Login();
		//login.sendPost();
	//}
}
