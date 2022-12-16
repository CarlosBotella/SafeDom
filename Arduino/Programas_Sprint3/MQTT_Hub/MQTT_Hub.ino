#include <FirebaseESP32.h>
#include <ArduinoMqttClient.h>
#include <WiFi.h>

#define Firebase_Host "https://safedom-b7c1c-default-rtdb.europe-west1.firebasedatabase.app"
#define Firebase_Auth "Ji5BY3xGWXxrW8hKnUcKdKBNv5lj7iviDkNfLQGZ"

char ssid[] = "ALCALA - MAS BLANCO";
char pass[] = "bloque3puerta5";

WiFiClient wifiClient;
MqttClient mqttClient(wifiClient);
FirebaseData firebaseData;

const char broker[] = "test.mosquitto.org";
int        port     = 1883;
const char topic1[] = "mmersan/Puerta_Principal";
const char topic2[] = "mmersan/Sensor_Peso";
const char topic3[] = "mmersan/Temperatura";
const char topic4[] = "mmersan/Humedad";
const char topic5[] = "mmersan/Sensor_Peso_TiempoActivo";
bool iterar = true;

void setup() {
  Serial.begin(115200);

  // WIFI NETWORK
  Serial.print("Conectando a SSID: ");
  Serial.println(ssid);
  while (WiFi.begin(ssid, pass) != WL_CONNECTED) {
    Serial.print(".");
    delay(3000);
  }
  Serial.println("CONECTADO AL WIFI");
  Serial.println();

  // BROKER NETWORK
  Serial.print("Conectando al broker: ");
  Serial.println(broker);

  if (!mqttClient.connect(broker, port)) {
    Serial.print("ERROR EN LA CONEXION: Error code = ");
    Serial.println(mqttClient.connectError());
    while (1);
  }
  Serial.println("CONECTADO AL BROKER");
  Serial.println();

  // MESSAGE CALLBACK
  mqttClient.onMessage(onMqttMessage);

  // SUBSCRIBE TO TOPIC
  Serial.println("Subscribiendose al topic: ");
  Serial.println(topic1);
  Serial.println(topic2);
  Serial.println(topic3);
  Serial.println(topic4);
  Serial.println(topic5);
  Serial.println();
  mqttClient.subscribe(topic1);
  mqttClient.subscribe(topic2);
  mqttClient.subscribe(topic3);
  mqttClient.subscribe(topic4);
  mqttClient.subscribe(topic5);

  // FIREBASE 
  Firebase.begin(Firebase_Host, Firebase_Auth);
  Firebase.reconnectWiFi(true);
}

void loop() {
  mqttClient.poll();
}

// --------------------------------
// --------------------------------
// --------------------------------
void onMqttMessage(int messageSize) {

  // VARIABLES
  String content = "";
  
  // MESSAGE RECIEVED
  Serial.print("Mensaje recibido en ");
  Serial.println(mqttClient.messageTopic());
  
  if(mqttClient.messageTopic()==topic1){
    while(mqttClient.available()){
      content.concat((char)mqttClient.read());
    }
    Firebase.setString(firebaseData, "/Casas/Clot, 68,20,5/Puerta_Principal", content);
    Serial.println(content);
  }
  
  if(mqttClient.messageTopic()==topic2){
    while(mqttClient.available()){
      content.concat((char)mqttClient.read());
    }
    Firebase.setString(firebaseData, "/Casas/Clot, 68,20,5/Sensor_Peso", content);
    Serial.println(content);
  }
  
  if(mqttClient.messageTopic()==topic3){
    while(mqttClient.available()){
      content.concat((char)mqttClient.read());
    }
    Serial.println(content.toFloat());
    Firebase.setFloat(firebaseData, "/Casas/Clot, 68,20,5/Temperatura", content.toFloat());
  }

  if(mqttClient.messageTopic()==topic4){
    while(mqttClient.available()){
      content.concat((char)mqttClient.read());
    }
    Serial.println(content.toFloat());
    Firebase.setInt(firebaseData, "/Casas/Clot, 68,20,5/Humedad", content.toFloat());
  }

  if(mqttClient.messageTopic()==topic5){
    while(mqttClient.available()){
      content.concat((char)mqttClient.read());
    }
    Serial.println(content.toInt());
    Firebase.setInt(firebaseData, "/Casas/Clot, 68,20,5/Sensor_Peso_TiempoActivo", content.toInt());
  }
}
