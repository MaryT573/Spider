package com.mary.spider;

import java.io.IOException;

/****************************************************************************
 * <b>Title:</b> Runner.java
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

/**
 * @author Mary Turpin
 *
 */
public class Spider {	
	/**
	 * main method starts and runs retrieval class
	 * @param args
	 * @throws  
	 */
	public static void main(String[] args) {
		Spider crawler = new Spider();
		crawler.run();
	}
	
	public void run() {
		Retrieval pages = new Retrieval();
		pages.getWebPage();
		Login login = new Login();
		try {
			login.sendPostStart();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
