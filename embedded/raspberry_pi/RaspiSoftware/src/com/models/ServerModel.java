package com.models;

import static com.models.MainModel.buggyServer;
import static com.models.MainModel.mode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.StringTokenizer;

import com.server.Reception;
import com.server.Transmission;

public class ServerModel {
	public final int PORT= 8000;
	public ServerSocket ssocket;
	public Socket socket;
	public boolean co = false;
	
	public Reception reception;
	public Transmission transmission;
	
	public BufferedReader in;
	public PrintWriter out;
	
	public StringTokenizer inData;
	public String outData;
	public boolean newMovement = false;
	
	public final int TIME_OUT = 5_000;
	
	
	public void serverSettings(){
		try{
			System.out.println("Wating for client connection...");
			buggyServer.socket = ssocket.accept();
			System.out.println("Client connected.");
			
			buggyServer.co = true;
			buggyServer.newMovement = false;
			buggyServer.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			buggyServer.out = new PrintWriter(socket.getOutputStream());
			buggyServer.out.write("Hello !\n");
			buggyServer.out.flush();
			buggyServer.socket.setSoTimeout(TIME_OUT);
			synchronized(mode){mode = MainModel.Mode.MAN;}
			
			buggyServer.starts();
		}catch(IOException e){e.printStackTrace();}
	}
	
	public void starts(){
		buggyServer.reception = new Reception();
		buggyServer.transmission = new Transmission(); 
		buggyServer.reception.reception.start();
		buggyServer.transmission.transmission.start();
	}

}
