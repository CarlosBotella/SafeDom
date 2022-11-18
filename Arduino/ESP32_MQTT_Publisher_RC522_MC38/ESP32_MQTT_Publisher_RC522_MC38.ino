#include <ESP32_Servo.h>
#include <SPI.h>
#include <MFRC522.h>
#include <M5Stack.h>
#include <ArduinoMqttClient.h>
#include <WiFi.h>

#define INT_PIN 39
#define RST_PIN G22
#define SS_PIN_ENB G5
#define SERVO_PIN G17
#define MC38_PIN G2

MFRC522 MyLectorRF(SS_PIN_ENB, RST_PIN);
Servo DoorServo;
WiFiClient wifiClient;
MqttClient mqttClient(wifiClient);

volatile bool isrState = false; 
bool isrCounter = false;
bool rfCounter = true;
bool lcdCounter = false;
bool doorCounter = true;
int cont2 = 0;
String BufferID = "";
const String Card = "b9afb4c3";

// WIFI
char ssid[] = "Kasda 4EDE KW6512";
char pass[] = "12345678";

// BROKER
const char broker[] = "broker.hivemq.com";
int        port     = 1883;
const char topic[]  = "mmersan/puertaPrincipal";

void setup() {
  M5.begin(true, false, true);
  M5.Power.begin();
  SPI.begin();
  
  //M5.Lcd.setTextSize(2);
  MyLectorRF.PCD_Init();
  DoorServo.attach(SERVO_PIN, 500, 2400);
  DoorServo.write(180);
  pinMode(INT_PIN, INPUT_PULLDOWN);
  pinMode(MC38_PIN, INPUT_PULLUP);

  // INTERRUPTION
  attachInterrupt(INT_PIN, closeDoor, RISING);

  // WIFI NETWORK
  Serial.print("Conectando al SSID: ");
  Serial.println(ssid);
  while (WiFi.begin(ssid, pass) != WL_CONNECTED) {
    // failed, retry
    Serial.print(".");
    delay(2000);
  }
  Serial.println("CONECTADO AL WIFI");
  Serial.println();

  // MQTT CONNECTION
  mqttClient.setId("12736g6f98askjg1");
  Serial.print("Conectado al broker: ");
  Serial.println(broker);

  if (!mqttClient.connect(broker, port)) {
    Serial.print("ERROR EN EL BROKER: Error code = ");
    Serial.println(mqttClient.connectError());
    while (1);
  }
  Serial.println("CONECTADO AL BROKER MQTT");
  Serial.println();

  // CHECK START DOOR STATUS
  doorStatus();

  // SERIAL BEGIN
  Serial.begin(115200);
  Serial.println("Inicializando...");
}

void loop() {
  mqttClient.poll();
  rfidRc522();
  checkIsrState();
}

void rfidRc522(){
  if(MyLectorRF.PICC_IsNewCardPresent()){
    if(MyLectorRF.PICC_ReadCardSerial()){
      BufferID = "";
      for(byte i=0; i<MyLectorRF.uid.size; i++){
        BufferID += String(MyLectorRF.uid.uidByte[i], HEX);
      }
      if(BufferID == Card && rfCounter){
        M5.Lcd.fillScreen(GREEN);
        rfCounter = false;
        isrCounter = true;
        Serial.println(BufferID);
        Serial.println("Card OK");
        Serial.println();
        for(int i = 180; i >=90; i--){
          DoorServo.write(i);
          delay(10);
        }
        doorStatus();
        delay(1500);
        M5.Lcd.fillScreen(BLACK);
      } 
      else if((BufferID != Card || BufferID == Card) && !rfCounter){
        rfCounter = false;
        isrCounter = true;
        Serial.println("Door already opened");
        Serial.println();
      }
      else if(BufferID != Card && rfCounter){
        rfCounter = true;
        isrCounter = false;
        M5.Lcd.fillScreen(RED);
        Serial.println(BufferID);
        Serial.println("Card denied");
        Serial.println();
        delay(1500);
        M5.Lcd.fillScreen(BLACK);
      }
      MyLectorRF.PICC_HaltA();
    }
    lcdMenu();
  }
}

void closeDoor(){
  isrState = true; 
}

void checkIsrState(){
  if(isrState && isrCounter){
    Serial.println("Door closing...");
    Serial.println();
    for(int i=90; i<=180; i++){
      DoorServo.write(i);
      delay(10);
    }
    doorStatus();
    isrState = false;
    isrCounter = false;
    rfCounter = true;
  }else if(isrState && !isrCounter){
    isrState = false;
    Serial.println("Door already closed");
    Serial.println();
  }
}

void lcdMenu(){
  M5.Lcd.setTextSize(2);
  M5.Lcd.setCursor(0, 16);
  M5.Lcd.print("1: Cerrar puerta");
  M5.Lcd.setCursor(0, 48);
  M5.Lcd.print("2: Cierre automatico");
  M5.Lcd.setTextSize(4);
  M5.Lcd.setCursor(30, 208);
  M5.Lcd.print("1");
}

void doorStatus(){
  if(digitalRead(MC38_PIN) == HIGH){
    mqttClient.beginMessage(topic);
    mqttClient.print(11);
    mqttClient.endMessage();
  }
  if(digitalRead(MC38_PIN) == LOW){
    mqttClient.beginMessage(topic);
    mqttClient.print(00);
    mqttClient.endMessage();
  }
}
