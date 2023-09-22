package com.mary.spider;

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
public class Runner {	
	/**
	 * main method starts and runs retrieval class
	 * @param args
	 */
	public static void main(String[] args) {
		Retrieval pages = new Retrieval();
		pages.getWebPage();
	}
}
