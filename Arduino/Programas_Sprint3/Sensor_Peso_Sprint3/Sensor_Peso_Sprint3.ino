#include <HX711.h>
#include <ArduinoMqttClient.h>
#include <M5Stack.h>
#include <WiFi.h>

#define DOUT 18
#define CLK 19

HX711 balanza;
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
const char topic1[] = "mmersan/Sensor_Peso";
const char topic2[] = "mmersan/Sensor_Peso_TiempoActivo";
bool iterar = true;

// ----- GENERALES -----
bool WS_Activity = false;
bool cont1 = false;
bool cont2 = false;
int count = 0;

// ----------------------------------------
// ---------------- SETUP -----------------
// ----------------------------------------
void setup() {
  Serial.begin(115200);
  balanza.begin(DOUT, CLK);
  balanza.set_scale(65045); // Establecemos la escala
  balanza.tare(5);  //El peso actual es considerado Tara.
  conexionWiFi();
  conexionMQTT();
  
  Serial.println("TODO LISTO");  
}

// ----------------------------------------
// ----------------- LOOP -----------------
// ----------------------------------------
void loop() {
  float valor = balanza.get_units(5);
  Serial.println((valor),3);
  if(valor>=2){
    WS_Activity = true;
    cont1=true;
    if(cont2){
      mqttClient.beginMessage(topic1);
      mqttClient.print("Activo");
      mqttClient.endMessage(); 
      cont2=false;
    }
  }
  if(valor<2){
    WS_Activity = false;
    cont2=true;
    if(cont1){
      mqttClient.beginMessage(topic1);
      mqttClient.print("Inactivo");
      mqttClient.endMessage();
      mqttClient.beginMessage(topic2);
      mqttClient.print(count);
      mqttClient.endMessage();
      Serial.println(count);
      count=0;
      cont1=false;
    }
  }
  
  if(WS_Activity){
    count++;
  }
  delay(1000);
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
  mqttClient.setId("Mariox112_HX711");
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
