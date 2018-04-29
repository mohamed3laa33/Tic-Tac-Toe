package server;

import java.net.*;
import java.io.*;
import java.util.Vector;
import server.MsgHandler;

public class MsgServer
{
	ServerSocket serverSocket;
	public MsgServer()
	{
		try{
			serverSocket = new ServerSocket(5005);
		}catch(Exception ex){

		}

		while(true)
		{
			try{
				Socket s = serverSocket.accept();
				new MsgHandler(s);
			}catch(Exception ex){
				
			}
		}
	}

	public static void main(String[] args)
	{
		new MsgServer();
	}
}