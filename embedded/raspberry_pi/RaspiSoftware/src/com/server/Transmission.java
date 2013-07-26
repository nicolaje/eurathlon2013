package com.server;

import static com.models.MainModel.buggyServer;

import java.io.IOException;

public class Transmission implements Runnable{
	public Thread transmission = new Thread(this);
	
	
	private void write(){
		synchronized(buggyServer){
			if(buggyServer.outData != null){
				buggyServer.out.write("COMPASS " +  buggyServer.outData + "\n");
				buggyServer.out.flush();
			}
			else{
				buggyServer.out.write("ping\n");
				buggyServer.out.flush();
			}
		}
	}
	
	private void close(){

		if(buggyServer.out != null){
			buggyServer.out.close();
			try{buggyServer.socket.close();}catch(IOException e){e.printStackTrace();}
		}
	}
	
	public void run(){
		while(buggyServer.co){
			write();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {e.printStackTrace();}
		}
		close();
	}
}
