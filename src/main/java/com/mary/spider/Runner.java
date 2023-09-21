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
 * @author mary
 *
 */
public class Runner {	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Retrieval pages = new Retrieval();
		pages.getWebPage("smt-stage.qa.siliconmtn.com", 443);
	}
}
