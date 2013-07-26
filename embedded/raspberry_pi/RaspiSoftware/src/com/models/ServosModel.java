package com.models;

import com.computing.Servos;

public class ServosModel {
	public Servos servos = new Servos();
	
	public double teta;   //compass value
	public double dteta;  //desired direction
	public double error;  //dteta - teta
	public final int K = 5;  //steering nervousness
	
	public int distance;  //distance between me and the desired position in meters
	
	//servos values
	public boolean reverse = false;  //if we are in reverse gear
	public int frontAngle;
	public int rearAngle;
	public int engineForce;
	public int step;
	
	/*integer which is set when at least one of the servos's value changes
	 *1 => only engine servo's value changes
	 *2 => front and rear +5 and engine value changes
	 *3 => front and rear -5 and engine value changes 
	 *4 => front and rear +/-5 (faster) and engine value changes
	 */
	public int news = 0; 
	
	public void servosSettings(){
		teta = 0.0;
		dteta = 0.0;
		error = 0.0;
		distance = 0;
		
		frontAngle = 90;
		rearAngle = 90;
		engineForce = 90;
		step =10;	
	}
}
