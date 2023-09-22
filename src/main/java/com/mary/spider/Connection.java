package com.mary.spider;

import java.io.*;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

/****************************************************************************
 * <b>Title:</b> Connection.java
 * <b>Project:</b> spider-lib
 * <b>Description:</b> CHANGE ME!!
 * <b>Copyright:</b> Copyright (c) 2023
 * <b>Company:</b> Silicon Mountain Technologies
 * 
 * @author MaryT
 * @version 3.x
 * @since Sep 17, 2023
 * <b>updates:</b>
 *  
 ****************************************************************************/

/**
 * Utility class that connects to a website and returns website html
 * @author Mary Turpin
 */
public class Connection {
	
	/**
	 * getConnect method connects using SSLSocket and returns the html
	 * @param host
	 * @param portNumber
	 * @param request
	 * @return
	 * @throws IOException
	 * @throws IOException
	 */
	public StringBuilder getConnect(String host, int portNumber, String request) throws IOException, IOException {
		//creates stringbuilder html
		StringBuilder html = new StringBuilder();
		//establish connection using SSL
		SSLSocketFactory sslsocketfactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
		SSLSocket socket = (SSLSocket) sslsocketfactory
				  .createSocket(host, portNumber);
		
		//creates printwriter, bufferedwriter, and outputstreamwriter
		PrintWriter out = new PrintWriter(
                new BufferedWriter(
                new OutputStreamWriter(
                socket.getOutputStream())));
		
		//send request
		out.println(request);
		out.println();
		out.flush();
		
		//System.out.println(request);
		//creates bufferedreader, inputstreamreader
		BufferedReader in = new BufferedReader(
                new InputStreamReader(
                socket.getInputStream()));
		String inData = null;
		
		//loops through data line by line and appends it to the html stringbuilder
		while((inData = in.readLine()) != null ) {
            html.append(inData).append(System.lineSeparator());
        }
		//closes and returns
		in.close();
        out.close();
        socket.close();

		return html;
	}
}
