package com.serial;

import static com.models.MainModel.buggySerial;
import static com.models.MainModel.buggyServos;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

public class Communication implements SerialPortEventListener{
	
	public Communication(){
		try{
		System.out.println("Starting serial communication with Arduino...");
		buggySerial.port.addEventListener(this);
		buggySerial.port.notifyOnDataAvailable(true);
		}catch(Exception e){System.err.println(e.toString());}
		
		Thread servosCommunication = new Thread() {
			public void run(){
				while(buggySerial.co){
					write();
				}
				close();
			}
		};
		Thread compassCommunication = new Thread() {
			public void run(){
				while(buggySerial.co){
					compassRequest();
				}
			}
		};
		servosCommunication.start();
		compassCommunication.start();
	}
	
	private void write(){
		try{
				switch (buggyServos.news){
					case 1:{
						buggySerial.out.write((byte) 'E'); //send engine servo's value
						buggySerial.out.flush();
						buggySerial.out.write(buggyServos.engineForce);
						buggySerial.out.flush();
						buggySerial.out.write((byte)'.'); 
						buggySerial.out.flush();
						
						System.out.println("Send SERVOS VALUE >> Engine:" + buggyServos.engineForce);
						synchronized (buggyServos){buggyServos.news = 0;}
						delay(25);
						break;
					}
					case 2:{
						buggySerial.out.write((byte) 'E'); //send engine servo's value
						buggySerial.out.flush();
						buggySerial.out.write(buggyServos.engineForce);
						buggySerial.out.flush();
						buggySerial.out.write((byte)'.'); 
						buggySerial.out.flush();
						
						buggySerial.out.write((byte)'F'); //send front servo's value
						buggySerial.out.flush();
						if(buggyServos.frontAngle + buggyServos.step <= 180)
							buggyServos.frontAngle+=buggyServos.step;
						buggySerial.out.write(buggyServos.frontAngle);
						buggySerial.out.flush();
						buggySerial.out.write((byte)'.'); 
						buggySerial.out.flush();
						
						buggySerial.out.write((byte) 'R'); //send rear servo's value
						buggySerial.out.flush();
						if(buggyServos.rearAngle - buggyServos.step >= 0)
							buggyServos.rearAngle-=buggyServos.step;
						buggySerial.out.write(buggyServos.rearAngle);
						buggySerial.out.flush();
						buggySerial.out.write((byte)'.'); 
						buggySerial.out.flush();

						System.out.println("Send SERVOS VALUE >> Engine: " + buggyServos.engineForce + " Front: " + buggyServos.frontAngle + " Rear: " + buggyServos.rearAngle );
						if(buggyServos.frontAngle == 180)
							synchronized (buggyServos){buggyServos.news = 0;}
						delay(25);
						break;
					}
					case 3:{
						buggySerial.out.write((byte) 'E'); //send engine servo's value
						buggySerial.out.flush();
						buggySerial.out.write(buggyServos.engineForce);
						buggySerial.out.flush();
						buggySerial.out.write((byte)'.'); 
						buggySerial.out.flush();
						
						buggySerial.out.write((byte)'F'); //send front servo's value
						buggySerial.out.flush();
						if(buggyServos.frontAngle - buggyServos.step >= 0)
							buggyServos.frontAngle-=buggyServos.step;
						buggySerial.out.write(buggyServos.frontAngle);
						buggySerial.out.flush();
						buggySerial.out.write((byte)'.'); 
						buggySerial.out.flush();
						
						buggySerial.out.write((byte) 'R'); //send rear servo's value
						buggySerial.out.flush();
						if(buggyServos.rearAngle + buggyServos.step <= 180)
							buggyServos.rearAngle+=buggyServos.step;
						buggySerial.out.write(buggyServos.rearAngle);
						buggySerial.out.flush();
						buggySerial.out.write((byte)'.'); 
						buggySerial.out.flush();
						
						System.out.println("Send SERVOS VALUE >> Engine: " + buggyServos.engineForce + " Front: " + buggyServos.frontAngle + " Rear: " + buggyServos.rearAngle );
						if(buggyServos.frontAngle == 0)
							synchronized (buggyServos){buggyServos.news = 0;}
						delay(25);
						break;
					}
					case 4:{
						buggySerial.out.write((byte) 'E'); //send engine servo's value
						buggySerial.out.flush();
						buggySerial.out.write(buggyServos.engineForce);
						buggySerial.out.flush();
						buggySerial.out.write((byte)'.'); 
						buggySerial.out.flush();
						
						buggySerial.out.write((byte)'F'); //send front servo's value
						buggySerial.out.flush();
						buggyServos.frontAngle = 90;
						buggySerial.out.write(buggyServos.frontAngle);
						buggySerial.out.flush();
						buggySerial.out.write((byte)'.'); 
						buggySerial.out.flush();
						
						buggySerial.out.write((byte) 'R'); //send rear servo's value
						buggySerial.out.flush();
						buggyServos.rearAngle = 90;
						buggySerial.out.write(buggyServos.rearAngle);
						buggySerial.out.flush();
						buggySerial.out.write((byte)'.'); 
						buggySerial.out.flush();
						
						System.out.println("Send SERVOS VALUE >> Engine: " + buggyServos.engineForce + " Front: " + buggyServos.frontAngle + " Rear: " + buggyServos.rearAngle );
						synchronized (buggyServos){buggyServos.news = 0;}
						delay(25);
						break;
					}
			}	
		}catch (Exception e){System.err.println(e.toString());}
	}
	
	private void compassRequest(){
		if(buggySerial.outData != 0){
			System.out.println(">>>>>>>>Request compass value");
			try{
				buggySerial.out.write(buggySerial.outData);
				buggySerial.out.flush();
			}catch(Exception e){System.err.println(e.toString());}
			delay(2000);
		}
	}
	
	public synchronized void serialEvent(SerialPortEvent evt){
		if(evt.getEventType() == SerialPortEvent.DATA_AVAILABLE){
			try{
				String str = buggySerial.in.readLine();
				if (str.equalsIgnoreCase("C")){
					buggySerial.inputCompass = Double.parseDouble(buggySerial.in.readLine());
					System.out.println("Compass : " + buggySerial.inputCompass);
				}
			}catch(Exception e){System.err.println(e.toString());}
		}
	}
	
	public synchronized void close(){
		if(buggySerial.port != null){
			buggySerial.port.removeEventListener();
			buggySerial.port.close();
		}
	}
	
	//Block the thread until the delay past
	private void delay(int delay){
		try{
			Thread.sleep(delay);
		}catch(InterruptedException e){
			e.printStackTrace();
		}
	}
}
