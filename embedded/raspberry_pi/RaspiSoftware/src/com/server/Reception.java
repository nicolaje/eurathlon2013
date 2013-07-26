package com.server;

import static com.models.MainModel.buggyServer;
import static com.models.MainModel.mode;
import static com.models.MainModel.timeCoLoose;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.Calendar;
import java.util.Locale;
import java.util.StringTokenizer;

import com.models.MainModel;

public class Reception implements Runnable {
	public Thread reception = new Thread(this);
	
	//variable used to throw ReadTimeoutException
	private boolean received_null = false;
	private int start_time;
	private int timer = 0;
	
	private void reading() throws ReadTimeoutException{
		if(timer >= buggyServer.TIME_OUT)
			throw new ReadTimeoutException();
		else{
			read();
		}
	}
	
	private void read (){

			try{
				String str = buggyServer.in.readLine();

				if(str != null){
					timer = 0;
					received_null = false;
					
					buggyServer.inData = new StringTokenizer(str);
					switch(buggyServer.inData.nextToken()){
						case "AUTO":{
							synchronized(mode){mode = MainModel.Mode.AUTO;}
							break;
						}
						case "START":{
							synchronized(mode){mode = MainModel.Mode.MAN;}
							break;
						}
						case "STOP":{
							synchronized(mode){mode = MainModel.Mode.UNREACHABLE;}
							break;
						}
						case "MOVE":{
							synchronized(buggyServer){
								buggyServer.newMovement = true;
							}
						}
						case "CAM":{ 
							break;
						}
//						case "GPS": {
//							buggyGPS.desired_lx = Double.parseDouble(buggyServer.inData.nextToken());
//							buggyGPS.desired_ly = Double.parseDouble(buggyServer.inData.nextToken());
//						}
						default :{
							break;
						}	
					}
				}
				else{
					if(!received_null){
						start_time =(int) System.currentTimeMillis();
						received_null = true;
					}
					timer = (int) System.currentTimeMillis() - start_time;
				}
			}
			catch(SocketTimeoutException e){
				System.out.println(">>>>>>>>>To long period without receiving any order<<<<<<<<<");
				synchronized(mode){mode = MainModel.Mode.UNREACHABLE;}
			}
			catch(IOException e){}
	}
	


	private void close(){
		System.out.println("Connection lost.");
	
		//set time when connection is lost
		StringTokenizer tmp = new StringTokenizer (Calendar.getInstance(Locale.FRANCE).getTime().toString());
		tmp.nextToken();
		tmp.nextToken();
		tmp.nextToken();
		timeCoLoose = tmp.nextToken();
		
		if(buggyServer.in != null){
			try{
				buggyServer.in.close();
			}catch(IOException e){e.printStackTrace();}
		}
		buggyServer.newMovement = false;
		System.out.println("####################################################\n");
	}
	
	public void run(){
		while (buggyServer.co){
			try{
				reading();
			}catch(Exception e){e.printStackTrace();}
		}
		close();
	}
	
	//Timeout exception for socket disconnection.
	class ReadTimeoutException extends Exception {
		private static final long serialVersionUID = 1L;

		public ReadTimeoutException(){
			System.out.println(">>>>>>>>>Receive null order<<<<<<<<<");
			synchronized(mode){mode = MainModel.Mode.UNREACHABLE;}
		}
	}
		

}
