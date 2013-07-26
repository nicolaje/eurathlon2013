package com.computing;

import static com.models.MainModel.buggyGPS;

import java.io.FileReader;
import java.io.IOException;
import java.util.Calendar;
import java.util.Locale;
import java.util.StringTokenizer;

public class GPS {
	char[] carray_time;
	byte[] buffer_time;
	byte[] buffer_gps = new byte[3];
	
	StringTokenizer timeNow;
	
	public void saveGPSData(){
		if(buggyGPS.lx != 0.0){
			int i = 0;
			timeNow = new StringTokenizer (Calendar.getInstance(Locale.FRANCE).getTime().toString());
			timeNow.nextToken();
			timeNow.nextToken();
			timeNow.nextToken();
			carray_time = timeNow.nextToken().toCharArray();
			buffer_time = new byte[carray_time.length + 1];
			for (char c : carray_time){
				buffer_time[i] = (byte) c;
				i++;
			}
			buffer_time[i] = (byte) '=';
			buffer_gps[0] = (byte) buggyGPS.lx;
			buffer_gps[1] = (byte) ' ';
			buffer_gps[2] = (byte) buggyGPS.ly;
			try{
				buggyGPS.gpsDataStream.write(buffer_time);
				buggyGPS.gpsDataStream.write(buffer_gps);
			}catch(IOException e){e.printStackTrace();}
		}
	}
	
	public void loadGPSData(String date){
		//time when connection was lost
		int h = date.charAt(0);
		int hh = date.charAt(1);
		int m = date.charAt(2);
		int mm = date.charAt(3);
		int s = date.charAt(4);
		int ss = date.charAt(5);
		
		try{
			FileReader read = new FileReader(buggyGPS.gpsData);
			int c = read.read();
			while (c != -1){
				if(c == h){
					c = read.read();
					if (c == hh)
						c=read.read();
						if(c == m){
							c=read.read();
							if(c == mm){
								c=read.read();
								if(c == s){
									c=read.read();
									if(c==ss){
										read.read();
										buggyGPS.desired_lx = read.read();
										read.read();
										buggyGPS.desired_ly = read.read();
										break;
									}
								}
							}
						}
				}
				c = read.read();
			}
			read.close();
		}catch(Exception e){System.err.println(e.toString());}
	}
	
	public int distanceComputing(){
		return (int) Math.sqrt(Math.pow((int)(buggyGPS.lx - buggyGPS.desired_lx), 2) + Math.pow((int)(buggyGPS.ly - buggyGPS.desired_ly), 2));
	}
}
