package devicetypes;

import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;


public abstract class Device implements Callable {

    public static final Logger logger = Logger.getLogger(Device.class.getName());

    private IMqttClient client;
    private Random rand;
    private int interval;
    private String topic;

    public Device(IMqttClient client) {
        this.setClient(client);
        setRand(new Random(System.currentTimeMillis()));
    }

    @Override
    public Object call() throws Exception {
        if (!getClient().isConnected()) {
            logger.log(Level.INFO, "Client not connected");
            return null;
        }

        MqttMessage msg = read();
        msg.setQos(2);
        msg.setRetained(true);
        getClient().publish(getTopic(), msg);
        return null;
    }

    public abstract MqttMessage read();

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public IMqttClient getClient() {
        return client;
    }

    public void setClient(IMqttClient client) {
        this.client = client;
    }

    public Random getRand() {
        return rand;
    }

    public void setRand(Random rand) {
        this.rand = rand;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
}
