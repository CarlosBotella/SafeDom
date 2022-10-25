#include <M5Stack.h>
#include <WiFi.h>
#include <WebServer.h>
#include <ArduinoJson.h>
#include <AsyncUDP.h>

const int pinSensor = 2;
const int pinLed = 5; 
int cont1 = 0;
int cont2 = 0;
const char * ssid = "ALCALA - MAS BLANCO";
const char * password = "bloque3puerta5";
int PORT = 1120;

AsyncUDP udp;
StaticJsonDocument<200> jsonBuffer; //tamaño maximo de los datos

//---------------------------------------------------------------
//---------------------------------------------------------------

void setup() {
  M5.begin();
  M5.Power.begin();
  M5.Lcd.setTextSize(3);

  // PIN Configuration
  pinMode(pinSensor, INPUT_PULLUP);
  pinMode(pinLed, OUTPUT);
  digitalWrite(pinLed, LOW);
  
  // Configuration serial
  Serial.begin(115200);

  // WiFi configuration
  WiFiConfiguration();
}

void loop() {
  readSensorValue();
  jsonSendData();
}

//---------------------------------------------------------------
//---------------------------------------------------------------

void WiFiConfiguration(){
  WiFi.mode(WIFI_STA);
  WiFi.begin(ssid, password);

  if (WiFi.waitForConnectResult() != WL_CONNECTED) {
    Serial.println("WiFi Failed");
    while(1) {
      delay(1000);
    }
  }
  if(udp.listen(PORT)) {
    Serial.print("UDP Listening on IP: ");
    Serial.println(WiFi.localIP());
    udp.onPacket([](AsyncUDPPacket packet) {
      Serial.write(packet.data(), packet.length());
      Serial.println();
    });
  }
  
}

//---------------------------------------------------------------
//---------------------------------------------------------------

void readSensorValue(){
  M5.Lcd.fillScreen(BLACK); 
  M5.Lcd.setTextColor(WHITE);
  do{
    cont2 = 0;
    if(cont1==0){
      digitalWrite(pinLed, HIGH);
      M5.Lcd.clear();
      M5.Lcd.setCursor(0, 10);
      M5.Lcd.print("Puerta principal abierta");      
    }else{
      digitalWrite(pinLed, HIGH);
    }
    cont1++;
  }while(digitalRead(pinSensor)==HIGH);
  do{
    cont1 = 0;
    if(cont2==0){
      digitalWrite(pinLed, LOW);
      M5.Lcd.clear();
      M5.Lcd.setCursor(0, 10);
      M5.Lcd.print("Puerta principal cerrada");  
    }else{
      digitalWrite(pinLed, LOW);
    }
    cont2++;
  }while(digitalRead(pinSensor)==LOW);
}

//---------------------------------------------------------------
//---------------------------------------------------------------

void jsonSendData(){

  char texto[200];
  jsonBuffer["DoorState"]="";
  
  if(digitalRead(pinSensor)==HIGH){
    jsonBuffer["DoorState"]="Puerta abierta";
  }
  if(digitalRead(pinSensor)==LOW){
    jsonBuffer["DoorState"]="Puerta cerrada";
  }

  serializeJson(jsonBuffer, texto);
  udp.broadcastTo(texto, PORT);
}
