import devicetypes.*;
import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;

import java.util.Locale;
import java.util.Random;
import java.util.UUID;
import java.util.logging.Level;

public class App {
    public static void main(String[] args) {
        String publisherId = UUID.randomUUID().toString();

        String type = args[0];

        try {
            IMqttClient publisher = new MqttClient("tcp://mqtt.eclipseprojects.io:1883", publisherId);
            MqttConnectOptions options = new MqttConnectOptions();
            options.setAutomaticReconnect(true);
            options.setCleanSession(true);
            options.setConnectionTimeout(10);

            Device d;
            if (type.toLowerCase().equals("tempsensor")) {
                d = new AmbientTemperatureSensor(publisher);
                System.out.println("Temperature Sensor");
            } else if (type.toLowerCase().equals("solararray")) {
                d = new SolarArraySensor(publisher);
                System.out.println("Solar Array");
            } else if (type.toLowerCase().equals("streetlight")) {
                d = new StreetLightStatusSensor(publisher);
                System.out.println("Street Light");
            } else {
                d = new TrafficSignalStatusSensor(publisher);
                System.out.println("Traffic Signal");
            }

            while (true) {
                Thread.sleep(1000 * d.getInterval());
                publisher.connect(options);
                d.call();
                // devicetypes.AmbientTemperatureSensor.logger.log(Level.INFO, "Data send success");
                publisher.disconnect();
            }
        } catch (MqttException e) {
            AmbientTemperatureSensor.logger.log(Level.SEVERE, "Connection initialization failed " + e.getMessage());
            System.exit(-1);
        } catch (Exception e) {
            AmbientTemperatureSensor.logger.log(Level.SEVERE, "Data send failure " + e.getMessage());
            System.exit(-1);
        }
    }
}
