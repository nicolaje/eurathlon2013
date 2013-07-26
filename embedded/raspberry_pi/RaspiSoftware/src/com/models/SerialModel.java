package com.models;

import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Enumeration;

import com.serial.Communication;
import static com.models.MainModel.buggySerial;
import static com.models.MainModel.mode;

public class SerialModel {
	public CommPortIdentifier portID;
	public SerialPort port;
	public boolean co = false;
	
	public BufferedReader in;
	public OutputStream out;
	
	public double inputCompass;
	public int outData;
	

	
	public void serialSettings(){
		inputCompass = 0.0;
		outData = 0;
		
		final String[] PORT_NAME = {"/dev/ttyAMA0","/dev/ttyACM1", "/dev/ttyACM0"};

		final int TIME_OUT = 2000;
		final int DATA_RATE = 115200;
		try{
			@SuppressWarnings("rawtypes")
			Enumeration ports = CommPortIdentifier.getPortIdentifiers();
			if (ports.hasMoreElements()){
				portID = (CommPortIdentifier) ports.nextElement();

				if(portID.getName().equalsIgnoreCase(PORT_NAME[0]) || portID.getName().equalsIgnoreCase(PORT_NAME[1]) || portID.getName().equalsIgnoreCase(PORT_NAME[2]) ){
					System.out.println("Arduino is on serial port " + portID.getName());
					port = (SerialPort) portID.open("Arduino", TIME_OUT);
					port.setSerialPortParams(DATA_RATE, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
					in = new BufferedReader(new InputStreamReader(port.getInputStream()));
					out = port.getOutputStream();
					co = true;
					buggySerial.starts();
					mode = MainModel.Mode.MAN;
				}
			}
			else{System.out.println("Arduino serial port could not be reached");}
		}catch(Exception e){System.err.println(e.toString());}
	}
	
	public void starts(){
		new Communication();
	}
}
