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
 * <b>Description:</b> CHANGE ME!!
 * <b>Copyright:</b> Copyright (c) 2023
 * <b>Company:</b> Silicon Mountain Technologies
 * 
 * @author MaryT
 * @version 3.x
 * @since Sep 18, 2023
 * <b>updates:</b>
 *  
 ****************************************************************************/

public class Retrieval {
	
	HashSet<String> newLinks = new HashSet<>();
	HashSet<String> clickedLinks = new HashSet<>();
	String route = null;
	
	public String requestBuilder(String host, int portNumber) {
		
		if (!newLinks.contains("/")) {
			newLinks.add("/");
		}

		for (String link : newLinks) {
			if (clickedLinks.contains(link)) {
				continue;
			}
			else {
				route = link;
				clickedLinks.add(link);
				break; 
			}	
		}
		
		String request = "GET " + route + " HTTP/1.1\r\n" +
                "Host: " + host + ":" + portNumber + "\r\n" +
                "\r\n";
		return request;
	}
	
	public StringBuilder getWebPage(String host, int portNumber) {
		String request = requestBuilder(host, portNumber);
		
		Connection connect = new Connection();
		StringBuilder html = null;
		try {
			html = connect.getConnect(host, portNumber, request);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("connected");
		printToFile(html);
		return html;
	}
	
	public void printToFile(StringBuilder html) {
		String name = null;
		if (route == "/") {
			name = "/home";
		}
		else {
			name = route;
		}
		
		try {
			File webPage = new File(name.substring(1) + ".html");
			BufferedWriter write = null;
	        System.out.println("File created: " + webPage.getName());
	        write = new BufferedWriter(new FileWriter(webPage));
	        write.append(html);
	        write.close(); 
	        parseLinks(webPage);
		} catch (IOException e) {
			System.out.println("An error occurred.");
		    e.printStackTrace();
		}
	}
	
	public void parseLinks(File webPage) throws IOException {
		Document page = Jsoup.parse(webPage);
		Elements links = page.select("a[href]");
		for (Element link : links) {
			if (link.attr("href").startsWith("/")) {
				newLinks.add(link.attr("href"));
			}
        }
		if (newLinks.equals(clickedLinks)) {
			System.out.println("Completed");
		}
		else {
			StringBuilder newpage = getWebPage("smt-stage.qa.siliconmtn.com", 443);
		}
    }
}
