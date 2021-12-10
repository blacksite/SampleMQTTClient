package devicetypes;

import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;


public class TrafficSignalStatusSensor extends Device {
    public TrafficSignalStatusSensor(IMqttClient client) {
        super(client);
        this.setInterval(30);
        this.setTopic("Traffic Signal");
    }

    @Override
    public MqttMessage read() {
        int status = getRand().nextInt(3);
        byte[] payload = String.format("T:%04d", status).getBytes(StandardCharsets.UTF_8);
        return new MqttMessage(payload);
    }

}
