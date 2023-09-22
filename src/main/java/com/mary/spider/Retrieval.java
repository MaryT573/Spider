package com.mary.spider;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


/****************************************************************************
 * <b>Title:</b> Retrieval.java
 * <b>Project:</b> spider-lib
 * <b>Description:</b> Retrieval Class for Spider
 * <b>Copyright:</b> Copyright (c) 2023
 * <b>Company:</b> Silicon Mountain Technologies
 * 
 * @author MaryT
 * @version 3.x
 * @since Sep 18, 2023
 * <b>updates:</b>
 *  
 ****************************************************************************/
/***
 * The retrieval class 
 * @author Mary Turpin
 *
 */
public class Retrieval {
	// establishes newLinks, clickedLinks, and route
	HashSet<String> newLinks = new HashSet<>();
	HashSet<String> clickedLinks = new HashSet<>();
	String route = null;
	String host = "smt-stage.qa.siliconmtn.com";
	int portNumber = 443;
	
	/***
	 * The method requestBuilder takes in the host, portNumber, and route and builds
	 * a request string to get a new page. It checks against clickedLinks to makes sure
	 * nothing is repeated and adds routes that have been used to clickedLinks
	 * @param host
	 * @param portNumber
	 * @return request 
	 */
	public String requestBuilder() {
		//adds basic homepage route at start of run
		if (!newLinks.contains("/")) {
			newLinks.add("/");
		}
		//checks if route has been used adds it to clickedLinks 
		for (String link : newLinks) {
			if (!clickedLinks.contains(link)) {
				route = link;
				clickedLinks.add(link);
				break; 
			}	
		}
		//builds request
		String request = "GET " + route + " HTTP/1.1\r\n" +
                "Host: " + host + ":" + portNumber + "\r\n" +
                "\r\n";
		return request;
	}
	
	
	/**
	 * The method getWebPage calls to the connection class and requestBuilder method, 
	 * send the request to the connection and returns the html of the requested page
	 * @param host
	 * @param portNumber
	 * @return html 
	 */
	public StringBuilder getWebPage() {
		//calls requestBuilder and gets the request string
		String request = requestBuilder();
		//instantiates connection class and html var
		Connection connect = new Connection();
		StringBuilder html = null;
		try {
			// calls connection method and sends int host,portnumber and request
			html = connect.getConnect(host, portNumber, request);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("connected");
		//sends out html to printToFile
		printToFile(html);
		return html;
	}
	
	/**
	 * Method printToFile: Takes in html, prints the html to a file at root, names it after 
	 * the route
	 * @param html
	 */
	public void printToFile(StringBuilder html) {
		// determines filename
		String filename = null;
		if (route == "/") {
			filename = "/home";
		}
		else {
			filename = route;
		}
		try {
			//creates file with name of the route and makes t an .html file
			File webPage = new File(filename.substring(1) + ".html");
			BufferedWriter write = null;
	        System.out.println("File created: " + webPage.getName());
	        write = new BufferedWriter(new FileWriter(webPage));
	        //writes html to file
	        write.append(html);
	        write.close(); 
	        //sends file to parseLinks method
	        parseLinks(webPage);
		} catch (IOException e) {
			System.out.println("An error occurred.");
		    e.printStackTrace();
		}
	}
	
	/**
	 * This method parseLinks takes in the webpage html file, parses it to get
	 * all links, either starts a new cycle or ends the program if clicked links and
	 * newlinks are equal, then starts login
	 * @param webPage
	 * @throws IOException
	 */
	public void parseLinks(File webPage) throws IOException {
		Document page = Jsoup.parse(webPage);
		//searches for links and saves them to an array
		Elements links = page.select("a[href]");
		//loops over and finds internal links that start with "/"
		//adds all links to newLinks
		for (Element link : links) {
			if (link.attr("href").startsWith("/")) {
				newLinks.add(link.attr("href"));
			}
        }
		//checks if newLinks and clickedLinks equal, stops crawler and starts login
		if (newLinks.equals(clickedLinks)) {
			System.out.println("Completed");
			System.out.println("Starting Login...");
			Login login = new Login();
			login.sendPost();
		}
		//restarts cycle until all links in newLinks and clickedLinks are the same
		else {
			getWebPage();
		}
    }
}
