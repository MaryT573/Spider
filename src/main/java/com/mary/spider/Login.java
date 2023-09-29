package com.mary.spider;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


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
	String session = null;

	//here for testing
	public static void main(String[] args) throws IOException  {
		System.out.println("start");
		Login login = new Login();
		login.sendPostStart();
	}

	/**
	 * Method getPost creates builds the post request and initializes a connection - sends header info onto
	 * getCookies
	 * @return html
	 * @throws IOException
	 */
	public StringBuilder sendPostStart() throws IOException {
		String body = buildBody();
		String header = "Host: " + "smt-stage.qa.siliconmtn.com" + ":" + 443 + "\r\n"
				+ "Content-Type: application/x-www-form-urlencoded" + "\r\n" + "Content-Length: " + body.length()
				+ "\r\n\r\n";
		String request = "POST " + "/admintool" + " HTTP/1.0\r\n";
		String prompt = request + header + body;
		Connection connect = new Connection();
		StringBuilder response = connect.getConnect("smt-stage.qa.siliconmtn.com", 443, prompt);
		System.out.println("Post  " + response);
		getCookiesGeneral(response);
		getSession(response);
		return response;
	}

	public StringBuilder sendPostContinue() throws IOException {
		String body = buildBody();
		String header = buildHeader();
		String request = "POST " + "/sb/admintool?cPage=stats&actionId=FLUSH_CACHE" + " HTTP/1.0\r\n";
		String prompt = request + header + body;
		Connection connect = new Connection();
		StringBuilder response = connect.getConnect("smt-stage.qa.siliconmtn.com", 443, prompt);
		System.out.println("Post  " + response);
		getCookiesGeneral(response);
		getSession(response);
		return response;
	}

	private void getSession(StringBuilder html) throws IOException {
		boolean stop = false;
		String headersString = html.toString();
		String[] headers = headersString.split("\n");
		for (String header: headers) {
			if (header.startsWith("HTTP/1.1 200 200")){
				stop = true;
			}
			if (header.trim().startsWith("Set-Cookie:")) {
				String[] parts = header.split(": ", 2);
				String session_get = parts[1];
				if (session_get.startsWith("JSESSIONID=")) {
					session = session_get.split(";")[0];
				}
			}
		}
		if (!stop) {
			try {
				sendPostContinue();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else {
			File webPage = new File("cache-stats" + ".html");
			BufferedWriter write = null;
			System.out.println("File created: " + webPage.getName());
			write = new BufferedWriter(new FileWriter(webPage));
			//writes html to file
			write.append(html);
			write.close();
		}
	}
	//take out split stuff put in new method
	private void getCookiesGeneral(StringBuilder html) {
		cookies = new LinkedList<>();
		String headersString = html.toString();
		String[] headers = headersString.split("\n");
		for (String header: headers) {
			if (header.trim().startsWith("Set-Cookie:")) {
				String[] parts = header.split("[:;]");
				String holder = parts[1].trim();
				if (!holder.startsWith("JSESSIONID=")) {
					cookies.add(holder);
				}
			}
		}
	}

	private String buildBody() {
		String body = "requestType=reqBuild&pmid=ADMIN_LOGIN&emailAddress=" +System.getenv("email")
				+ "&password="+ System.getenv("password")+ "&l=" + "\r\n" + "\r\n";
		return body;
	}

	private String buildHeader() {
		StringBuilder buildCookie = new StringBuilder();
		buildCookie.append("Cookie: ").append(session).append("; ");
		for (String cookie: cookies) {
			buildCookie.append(cookie);
		}
		String body = buildBody();
		String header = "Host: " + "smt-stage.qa.siliconmtn.com" + ":" + 443 + "\r\n"
				+ "Content-Type: application/x-www-form-urlencoded" + "\r\n" + "Content-Length: " + body.length() +
				"\r\n" + buildCookie + "\r\n\r\n";
		return header;
	}
}
//check if error with build cookie string type
//	Spider TODO
//      1. get the location and cookies seperated out  (Tues)
//		2. get these assigned to map (Tues)
//		3. overwrite update these values each run (Tues)
//		4. seperate header and body into seperate builder methods (Weds)
//		5. refactor post to take this that way (Weds)
//		6. stop at 200 (Weds/Thurs)
//		7. keep cookies and hardcode cache stats call (Thurs)
//		8. print out cache stats page (Thurs)
//		9. profit (Fri)
//	 String[] cookieAttributes = cookieHeaderValue.split(";")[0]; takes stuff on the left
//		Spider Refactor
//      0.5. refactor print class to use same method
//		1. switch print to log
//		2. ammend methods to be less chunky
//		3. switch to byte array
//once you get dashboard
// /sb/admintool?cPage=stats&actionId=FLUSH_CACHE
