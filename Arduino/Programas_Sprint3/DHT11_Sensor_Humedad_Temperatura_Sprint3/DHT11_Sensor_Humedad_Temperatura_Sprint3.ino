#include <DHT.h>
#include <ArduinoMqttClient.h>
#include <M5Stack.h>
#include <WiFi.h>
 
#define DHTPIN 5
#define DHTTYPE DHT11

DHT dht(DHTPIN, DHTTYPE);
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
const char topic1[] = "mmersan/Temperatura";
const char topic2[] = "mmersan/Humedad";
bool iterar = true;

// ----------------------------------------
// ---------------- SETUP -----------------
// ----------------------------------------
void setup() {
  Serial.begin(115200);
  dht.begin();
  conexionWiFi();
  conexionMQTT();
}

// ----------------------------------------
// ----------------- LOOP -----------------
// ----------------------------------------
void loop() {
  
  // READ TEMPERATURE AND HUMIDITY
  float h = dht.readHumidity();
  float t = dht.readTemperature();
 
  // Comprobamos si ha habido algún error en la lectura
  if (isnan(t)||isnan(h)) {
    Serial.println("Error obteniendo los datos del sensor DHT11");
    delay(2000);
    return;
  }

  mqttClient.beginMessage(topic1);
  mqttClient.print(t);
  mqttClient.endMessage();
  mqttClient.beginMessage(topic2);
  mqttClient.print(h);
  mqttClient.endMessage();
  Serial.print("Temperatura: ");
  Serial.print(t);
  Serial.println(" *C");
  Serial.print("Humedad: ");
  Serial.print(h);
  Serial.println(" %");
  delay(6000);
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
  mqttClient.setId("Mariox112_DHT11");
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
