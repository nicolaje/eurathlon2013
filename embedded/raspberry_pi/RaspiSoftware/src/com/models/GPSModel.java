package com.models;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import com.computing.GPS;

public class GPSModel {
	private final GPS gps = new GPS();
	
	//buggy gps position
	public double lx;		//longitude (meters) 
	public double ly;		//latitude (meters)
	
	//gps position to reached
	public double desired_lx;
	public double desired_ly;

	public File gpsData;
	public FileOutputStream gpsDataStream;
	
	public void gpsSettings(){
		lx = 0.0;
		ly = 0.0;
		
		try{
			gpsData = new File("/root/gps_data.txt");
			gpsDataStream = new FileOutputStream (gpsData);
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public int doIt(String method){
		if(method.equalsIgnoreCase("normal"))
			gps.saveGPSData();
		else
			gps.loadGPSData(method);
		
		return gps.distanceComputing();
	}

}
