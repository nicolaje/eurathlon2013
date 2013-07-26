#include <Servo.h>
#include <Wire.h>

int slAdress=33; //compass I2C adress
double compass=0;
Servo front, rear, engine;
int incomingAction;
int inByte=0;
int serialData;

void setup (){
  Serial.begin(115200);
  Wire.begin();
  engine.attach(9);
  rear.attach(10);
  front.attach(11);
  Serial.println("HELLO");
}

void loop(){
  if(Serial.available() > 0){
    incomingAction=Serial.read();
    switch (incomingAction){
      case 70:
        getSerial();
        front.write(serialData);
        break;
      case 82:
        getSerial();
        rear.write(serialData);
        break;
      case 69:
        getSerial();
        engine.write(serialData);
        break;
      case 67:
        Wire.write("A");
        Wire.endTransmission();
        delayMicroseconds(70);
        Wire.requestFrom(slAdress, 2);
        compass=256*Wire.read();
        compass+=Wire.read();
        compass/=10;
        Serial.println('C');
        Serial.println(compass);
        break;
    }
    Serial.print((char)incomingAction);
    Serial.println(serialData);
  }
}

void getSerial(){
  serialData=0;
  while (inByte != '.'){
    delay(20);
    inByte = Serial.read();
    if(inByte > 0 && inByte != '.'){
      serialData = inByte;
    }
  }
  inByte = 0;
}  
