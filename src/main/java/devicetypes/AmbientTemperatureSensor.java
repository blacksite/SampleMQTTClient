package devicetypes;

import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;


public class AmbientTemperatureSensor extends Device {
    private int tempType;

    public AmbientTemperatureSensor(IMqttClient client) {
        super(client);
        tempType = getRand().nextInt(3);
        this.setInterval(30);
        this.setTopic("Ambient Temperature Sensor");
    }

    @Override
    public MqttMessage read() {
        double temp;
        switch (tempType) {
            case 0:
                temp = 40 * getRand().nextDouble() * 20;
                break;
            case 1:
                temp = 60 * getRand().nextDouble() * 20;
                break;
            default:
                temp = 80 * getRand().nextDouble() * 20;
        }
        byte[] payload = String.format("T:%04.2f", temp).getBytes(StandardCharsets.UTF_8);
        return new MqttMessage(payload);
    }


}
