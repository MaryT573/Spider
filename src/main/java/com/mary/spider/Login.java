package com.mary.spider;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;


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

public class Login {
	
	
	public static void main(String[] args) throws IOException  {
		System.out.println("start");
		Login login = new Login();
		login.getPost();
	}
	
	public StringBuilder getPost() throws IOException {
		StringBuilder html = new StringBuilder();
		
		String request = "POST " + "/admintool" + " HTTP/1.1\r\n" +
                "Host: " + "smt-stage.qa.siliconmtn.com" + ":" + 443 + "\r\n" + "Content-Type: application/x-www-form-urlencoded" + "\r\n" + "Content-Length: " + 97 + "\r\n"
                + "\r\n" + "requestType=reqBuild&pmid=ADMIN_LOGIN&emailAddress=" +System.getenv("email")+ "&password="+ System.getenv("pword")+ "&l=" + "\r\n" + "\r\n";
		Connection connect = new Connection();
		html = connect.getConnect("smt-stage.qa.siliconmtn.com", 443, request);
		System.out.println(html);
		return html;
	} 
	
	public void getCookies() throws IOException {
		StringBuilder html = getPost();
	}
}
