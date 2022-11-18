#include <FirebaseESP32.h>
#include <ArduinoMqttClient.h>
#include <WiFi.h>


#define Firebase_Host "https://safedom-b7c1c-default-rtdb.europe-west1.firebasedatabase.app"
#define Firebase_Auth "Ji5BY3xGWXxrW8hKnUcKdKBNv5lj7iviDkNfLQGZ"

char ssid[] = "Kasda 4EDE KW6512";
char pass[] = "12345678";

WiFiClient wifiClient;
MqttClient mqttClient(wifiClient);
FirebaseData firebaseData;

const char broker[] = "broker.hivemq.com";
int        port     = 1883;
const char topic[]  = "mmersan/puertaPrincipal";
const String nodo = "/Sensores";
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
  Serial.print("Subscribiendose al topic: ");
  Serial.println(topic);
  Serial.println();
  mqttClient.subscribe(topic);
  Serial.print("Waiting for messages on topic: ");
  Serial.println(topic);
  Serial.println();

  // FIREBASE 
  Firebase.begin(Firebase_Host, Firebase_Auth);
  Firebase.reconnectWiFi(true);
}

void loop() {
  mqttClient.poll();
}

void onMqttMessage(int messageSize) {
  // we received a message, print out the topic and contents
  Serial.println("Mensaje recibido con topic '");
  Serial.print(mqttClient.messageTopic());
  Serial.print("', tamaño ");
  Serial.print(messageSize);
  Serial.println(" bytes:");

  if(messageSize==2){
    Firebase.setString(firebaseData, nodo + "/MC38", "Puerta abierta");
    Serial.println("Puerta abierta");
  }
  if(messageSize==1){
    Firebase.setString(firebaseData, nodo + "/MC38", "Puerta cerrada");
    Serial.println("Puerta cerrada");   
  }
  Serial.println();
}
