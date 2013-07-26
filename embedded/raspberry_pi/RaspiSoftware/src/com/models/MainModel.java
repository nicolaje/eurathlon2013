package com.models;

import java.io.IOException;
import java.net.ServerSocket;



/**
 * @author rieux
 *
 */
public class MainModel {
	public static boolean start = false;
	public static String timeCoLoose;
	public static Mode mode;
	public static ServerModel buggyServer = new ServerModel();
	public static ServosModel buggyServos = new ServosModel();
	public static SerialModel buggySerial = new SerialModel();
	public static GPSModel buggyGPS = new GPSModel();
	
	
	public MainModel(){	
		try{buggyServer.ssocket = new ServerSocket (buggyServer.PORT);}catch(IOException e){}
		System.out.println("#################Server creation done.#################\n");
		
		mode = Mode.MAN;
		buggyServer.serverSettings();
		buggyServos.servosSettings();
//		buggyGPS.gpsSettings();
		buggySerial.serialSettings();
	}

	public enum Mode{
		MAN,		//Manual control mode
		AUTO,		//Automatic mode
		STOP,		
		UNREACHABLE; //Connection lost
		
		Mode(){}
		
		public void doMode(){

			if(mode == Mode.MAN){	
//				if(buggyGPS.lx != 0.0)
//				buggyServer.outData = "GPS" + buggyGPS.lx + " " + buggyGPS.ly;
				
				buggySerial.outData = (byte)'C';  //asking compass value		
				
				synchronized(buggySerial){
					if(buggySerial.inputCompass != 0.0){
//						buggyServos.teta = buggySerial.inputCompass;
//						buggyServos.distance = buggyGPS.doIt("normal");
						synchronized(buggyServer){
							buggyServer.outData = String.valueOf(buggySerial.inputCompass);
						}
					}
				}

				
				if(buggyServer.newMovement){
					synchronized(buggyServer){
						buggyServer.newMovement = false;
					}
					buggyServos.engineForce = Integer.parseInt(buggyServer.inData.nextToken());
					String direction = buggyServer.inData.hasMoreElements()? buggyServer.inData.nextToken() : "NONE";
					buggyServos.servos.steeringMAN(direction);
				}	
				
			}
			if(mode == Mode.AUTO){
//				System.out.println("Auto mode");
//				buggySerial.outData = 67;  //asking compass value
//			
//				if(buggyGPS.lx != 0.0)
//					buggyServer.outData = "GPS" + buggyGPS.lx + " " + buggyGPS.ly;
//			
//				if(buggyGPS.lx == buggyGPS.desired_lx && buggyGPS.ly == buggyGPS.desired_ly)  //when desired position has been reached
//					buggyServer.outData = "GPS DESIRED";  //Asking for new desired gps position.
//			
//				if(buggySerial.inputCompass != 0.0){
//					buggyServos.teta = buggySerial.inputCompass;
//					buggyServos.distance = buggyGPS.doIt("normal");
//					buggyServos.servos.steeringAUTO();
//				}
			}
			if(mode == Mode.STOP){
				System.out.println("Stop mode");
				buggyServos.engineForce = 0;
				buggyServos.rearAngle = 90;
				buggyServos.frontAngle = 90;
				start = false;
			}
			if(mode == Mode.UNREACHABLE){
				System.out.println("Unreachable mode");
				buggySerial.co = false;
				buggyServer.co = false;
				try {Thread.sleep(200);} catch (InterruptedException e) {e.printStackTrace();}
				buggyServer.serverSettings();
				buggySerial.serialSettings();
				
					
//			buggyGPS.doIt(timeCoLoose);
			}
		}
	}
	
	public void doMode(){
		synchronized(mode){mode.doMode();}
	}
}
