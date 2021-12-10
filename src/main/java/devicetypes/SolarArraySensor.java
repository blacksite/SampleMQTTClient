package devicetypes;

import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;


public class SolarArraySensor extends Device {

    public SolarArraySensor(IMqttClient client) {
        super(client);
        this.setInterval(60);
        this.setTopic("Solar Array");
    }

    @Override
    public MqttMessage read() {
        double voltage = getRand().nextDouble() * 20000;

        byte[] payload = String.format("T:%04.2f", voltage).getBytes(StandardCharsets.UTF_8);
        return new MqttMessage(payload);
    }

}
