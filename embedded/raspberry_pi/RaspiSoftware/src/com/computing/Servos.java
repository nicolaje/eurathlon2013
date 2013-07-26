package com.computing;

import static com.models.MainModel.buggyServos;

public class Servos {
	
	private boolean once = false;
	
	
	public void steeringMAN(String direction){
		synchronized(buggyServos){
			if(direction.equalsIgnoreCase("R")){
					buggyServos.news = 2;
					once = false;
			}
			else if(direction.equalsIgnoreCase("L")){
					buggyServos.news = 3;
					once = false;
			}
			else if(direction.equalsIgnoreCase("NONE")){
				if(!once){
				buggyServos.news = 4;
					once = true;
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {e.printStackTrace();}
				}
				else
					buggyServos.news = 1;
			}
			else {
				buggyServos.news = 1;
				once = false;
			}
		}
	}
	
	public void steeringCAP(String direction){
		int hold_frontAngle = buggyServos.frontAngle;
		int hold_engineForce = buggyServos.engineForce;
		
//		if(buggyServos.teta != 0.0 /*&& buggyServos.distance != 0*/){
			//rotation angle computing
			if(direction.equalsIgnoreCase("R")){
				buggyServos.news = 2;
				once = false;
			}
			else if(direction.equalsIgnoreCase("L")){
				buggyServos.news = 3;
				once = false;
			}
			else if(direction.equalsIgnoreCase("NONE")){
				if(!once){
					buggyServos.news = 4;
					once = true;
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {e.printStackTrace();}
				}
				else
					buggyServos.news = 1;
			}
			else{
				
			}
			int epsilon = 1;
			buggyServos.error = buggyServos.dteta - buggyServos.teta;
			System.out.println("angle error : " + buggyServos.error);
			if(Math.cos(buggyServos.error) < 0.0)
				epsilon = 0;
			buggyServos.frontAngle = buggyServos.rearAngle = 
					(int)((double) buggyServos.K * ((double)epsilon * Math.sin(buggyServos.error) + (1-epsilon) * Math.signum(Math.sin(buggyServos.error))));
			
			//engine force computing
//			if(buggyServos.distance < 0.5)
//				buggyServos.engineForce = 0;
//			else if(buggyServos.distance < 2)
//				buggyServos.engineForce = 25;
//			else if (buggyServos.distance < 4)
//				buggyServos.engineForce = 50;
//			else if (buggyServos.distance < 5)
//				buggyServos.engineForce = 75;
//			else 
//				buggyServos.engineForce = 100;
			
			//Looks for any change in servos's values
			if(hold_engineForce != buggyServos.engineForce){
				if(hold_frontAngle != buggyServos.frontAngle){
					buggyServos.news = 3;
				}
				buggyServos.news = 1;
			}
			else if(hold_frontAngle != buggyServos.frontAngle){
				buggyServos.news = 2;
			}
			else{
				buggyServos.news = 0;
			}
//		}
		System.out.println("Servos : " + buggyServos.engineForce + " " + buggyServos.frontAngle + " " + buggyServos.rearAngle);
	}
	
}
