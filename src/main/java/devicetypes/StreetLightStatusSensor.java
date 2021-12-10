package devicetypes;

import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;


public class StreetLightStatusSensor extends Device {

    public StreetLightStatusSensor(IMqttClient client) {
        super(client);
        this.setInterval(60);
        this.setTopic("Street Light");
    }

    @Override
    public MqttMessage read() {
        int status = getRand().nextInt(2);
        int amount = getRand().nextInt(10000);
        byte[] payload = (String.format("T:%1d", status) + String.format("T:%05d", amount)).getBytes(StandardCharsets.UTF_8);
        return new MqttMessage(payload);
    }

}
