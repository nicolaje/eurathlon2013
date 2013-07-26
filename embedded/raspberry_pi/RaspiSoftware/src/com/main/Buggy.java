package com.main;

import java.io.File;

import com.models.MainModel;

public class Buggy implements Runnable {
	static MainModel buggy = new MainModel();
	public Thread t = new Thread(this);
	
	Buggy(){
		MainModel.start = true;
		t.start();
	}
	
	public void run(){
		while(MainModel.start){
			buggy.doMode();
		}
	}
	
	public static void main (String[] args){
		//delete the lock file named "LCK..ttyACM0" or "LCK..ttyAMA0" if exits.
		Runtime deleteLockFile = Runtime.getRuntime();
		File lockFileACM0 = new File ("/var/lock/LCK..ttyACM0");
		File lockFileAMA0 = new File ("/var/lock/LCK..ttyAMA0");
		try{
			if (lockFileACM0.exists())
				deleteLockFile.exec("rm /var/lock/LCK..ttyACM0");
			else if(lockFileAMA0.exists())
				deleteLockFile.exec("rm /var/lock/LCK..ttyAMA0");
		}catch(Exception e ){System.err.println(e.toString());}
		
		new Buggy();
	}
	
}
