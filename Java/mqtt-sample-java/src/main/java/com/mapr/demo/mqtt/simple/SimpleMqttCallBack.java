package com.mapr.demo.mqtt.simple;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;

public class SimpleMqttCallBack implements MqttCallback {
	

  public void connectionLost(Throwable throwable) {
    System.out.println("Connection to MQTT broker lost!");
    System.out.println(throwable);
  }

  public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
    String msg = new String(mqttMessage.getPayload());
    System.out.println("Message received:\t"+ msg );
	final GpioController gpio = GpioFactory.getInstance();
        final GpioPinDigitalOutput pin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_01, "MyLED");
	if(msg.equals("ON")){
	pin.high();
	
	}
	if(msg.equals("OFF")){
	pin.low();
	}
	gpio.shutdown();
	gpio.unprovisionPin(pin);
}


  public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
    System.out.println("OK");
  }
}
