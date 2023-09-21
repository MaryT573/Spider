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

public class Connection {
	public StringBuilder getConnect(String host, int portNumber, String request) throws IOException, IOException {
		StringBuilder html = new StringBuilder();
		
		SSLSocketFactory sslsocketfactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
		SSLSocket socket = (SSLSocket) sslsocketfactory
				  .createSocket(host, portNumber);
	
		PrintWriter out = new PrintWriter(
                new BufferedWriter(
                new OutputStreamWriter(
                socket.getOutputStream())));

		out.println(request);
		out.println();
		out.flush();
		System.out.println(request);
		
		BufferedReader in = new BufferedReader(
                new InputStreamReader(
                socket.getInputStream()));
		String inData = null;
		
		while((inData = in.readLine()) != null ) {
            html.append(inData).append(System.lineSeparator());
        }

		in.close();
        out.close();
        socket.close();

		return html;
	}
}
