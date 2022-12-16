#include <ESP32_Servo.h>
#include <SPI.h>
#include <MFRC522.h>
#include <M5Stack.h>
#include <ArduinoMqttClient.h>
#include <WiFi.h>

#define PIN_REST G22         // RFID RC522
#define PIN_ENB G5           // RFID RC522
#define PIN_SERVO G17        // MOTOR SERVO SG90
#define PIN_MC38 G2          // SENSOR MAGNETICO MC38
#define PIN_BTN_INT 39       // BOTON INTERRUPCION (cerrar puerta)

MFRC522 lectorRFID(PIN_ENB, PIN_REST);
Servo servoPuerta;
WiFiClient wifiClient;
MqttClient mqttClient(wifiClient);

// ----------------------------------------
// -------------- VARIABLES ---------------
// ----------------------------------------

// ----- WIFI -----
char ssid[] = "ALCALA - MAS BLANCO";
char pass[] = "bloque3puerta5";

// ----- BROKER -----
const char broker[] = "test.mosquitto.org";
int        port     = 1883;
const char topic[] = "mmersan/Puerta_Principal";

// ----- GENERALES -----
volatile bool estadoIntCerrarPuerta = false;
bool boolEstadoPuerta = true;
String BufferID = "";
const String RFID = "b9afb4c3";
bool cont1 = true;
bool cont2 = false;

// ----------------------------------------
// ---------------- SETUP -----------------
// ----------------------------------------
void setup(){
  M5.begin(true, false, true);
  M5.Power.begin();
  lectorRFID.PCD_Init();
  SPI.begin();
  Serial.begin(115200);
  servoSetup();
  pinModeSetup();
  //conexionWiFi();
  Serial.print("Conectando a SSID: ");
  Serial.println(ssid);
  while (WiFi.begin(ssid, pass) != WL_CONNECTED) {
    Serial.print(".");
    delay(3000);
  }
  Serial.println("CONECTADO AL WIFI");
  Serial.println();
  conexionMQTT();
  menuLCD();

  // ----- INTERRUPCIONES -----
  attachInterrupt(PIN_BTN_INT, intCerrarPuerta, RISING);
  
  Serial.println("INICIANDO...");
}

// ----------------------------------------
// ----------------- LOOP -----------------
// ----------------------------------------
void loop(){
  mqttClient.poll();          // Refrescar la BBDD
  checkIntCerrarPuerta();     // Comprobar interrupcion puerta
  estadoPuerta();             // Comprobar el estado de la puerta
  lectorPuerta();
}

// ----------------------------------------
// ---------------- SERVO -----------------
// ----------------------------------------
void servoSetup(){
  servoPuerta.attach(PIN_SERVO, 500, 2400);
  servoPuerta.write(90);
}

// ----------------------------------------
// --------------- PINMODE ----------------
// ----------------------------------------
void pinModeSetup(){
  pinMode(PIN_BTN_INT, INPUT_PULLDOWN);
  pinMode(PIN_MC38, INPUT_PULLUP);
}

// ----------------------------------------
// ------------ CONEXION WIFI -------------
// ----------------------------------------
void conexionWiFi(){
  Serial.println();
  Serial.print("Conectando a la red: ");
  Serial.println(ssid);
  while (WiFi.begin(ssid, pass) != WL_CONNECTED) {
    Serial.print(".");
    delay(2000);
  }
  Serial.println();
  Serial.println("CONECTADO");
  Serial.println();
}

// ----------------------------------------
// ------------ CONEXION MQTT -------------
// ----------------------------------------
void conexionMQTT(){
  mqttClient.setId("12736g6f98askjg1");
  Serial.print("Conectando al broker: ");
  Serial.print(broker);

  if (!mqttClient.connect(broker, port)) {
    Serial.println();
    Serial.print("ERROR EN EL BROKER: Error code = ");
    Serial.print(mqttClient.connectError());
    while (1);
  }
  Serial.println();
  Serial.println("CONECTADO");
  Serial.println();
}

// ----------------------------------------
// --------------- MENU LCD ---------------
// ----------------------------------------
void menuLCD(){
  M5.Lcd.setTextSize(2);
  M5.Lcd.setCursor(0, 16);
  M5.Lcd.print("1: Cerrar puerta");
  M5.Lcd.setCursor(0, 48);
  M5.Lcd.print("2: Cierre automatico");
  M5.Lcd.setTextSize(4);
  M5.Lcd.setCursor(55, 208);
  M5.Lcd.print("1");
}

// ----------------------------------------
// ------ INTERRUPCION CERRAR PUERTA ------
// ----------------------------------------
void intCerrarPuerta(){
  estadoIntCerrarPuerta = true;
}
// ----- FUNCION CERRAR LA PUERTA -----
void checkIntCerrarPuerta(){
  if(estadoIntCerrarPuerta){
    servoPuerta.write(180);
    M5.Lcd.fillScreen(BLUE);
    estadoPuerta();
    delay(1500);
    M5.Lcd.fillScreen(BLACK);
    menuLCD();
    estadoIntCerrarPuerta = false;
  }
}

// ----------------------------------------
// --------- ESTADO DE LA PUERTA ----------
// ----------------------------------------
void estadoPuerta(){
  if(digitalRead(PIN_MC38)== HIGH && cont1){
    noInterrupts();
    cont1 = false;
    cont2 = true;
    mqttClient.beginMessage(topic);
    mqttClient.print("Abierta");
    mqttClient.endMessage();
    Serial.println("Abierta");
    boolEstadoPuerta = false;
    delay(150);
    interrupts();
  } 
  if(digitalRead(PIN_MC38)== LOW && cont2){
    noInterrupts();
    cont2 = false;
    cont1 = true;
    mqttClient.beginMessage(topic);
    mqttClient.print("Cerrada");
    mqttClient.endMessage();
    Serial.println("Cerrada");
    boolEstadoPuerta = true;
    delay(150);
    interrupts();
  }
}

// ----------------------------------------
// ------------ LECTOR RFID ---------------
// ----------------------------------------
void lectorPuerta(){
  if(lectorRFID.PICC_IsNewCardPresent()){
    if(lectorRFID.PICC_ReadCardSerial()){
      BufferID = "";
      for(byte i=0; i<lectorRFID.uid.size; i++){
        BufferID += String(lectorRFID.uid.uidByte[i], HEX);
      }
      if(BufferID == RFID){
        M5.Lcd.fillScreen(GREEN);
        servoPuerta.write(0);
        Serial.println("OK");
        Serial.println();
        delay(1500);
        M5.Lcd.fillScreen(BLACK);
        menuLCD();
      }
      if(BufferID != RFID){
        M5.Lcd.fillScreen(RED);
        Serial.println("INVALID");
        Serial.println();
        delay(1500);
        M5.Lcd.fillScreen(BLACK);
        menuLCD();
      }
    }
  }
}
