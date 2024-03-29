package com.pptik.siput.service;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.util.Log;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeoutException;

public class Rmq_Service {
    String user = "kapalselam";
    String pass = "1245and4512";
    String host = "cloudrmqserver.pptik.id";
    String vhost = "/kapalselam";
    String exchanges_name = "amq.fanout";
    ConnectionFactory factory = new ConnectionFactory();
    final My_Rmq myRmq;

    public Rmq_Service(My_Rmq myRmq) {
        this.myRmq = myRmq;
    }
    /**
     * Setup Connection Factory
     * Load Setting Connection RMQ Sebagai Parameter Koneksi
     */
    public void setupConnectionFactory() throws NoSuchAlgorithmException, URISyntaxException, KeyManagementException {
        factory.setAutomaticRecoveryEnabled(false);
        factory.setUri("amqp://"+user+":"+pass+"@"+host);
        factory.setVirtualHost(vhost);
    }

    /**
     * Publish data lewat RMQ
     * @param message
     */
    public void publish(String message, String queue_name_publish ) {
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
            String messageOn = message ;
            channel.basicPublish("", queue_name_publish,null,messageOn.getBytes());
            myRmq.Berhasil(message);
        } catch (IOException e) {
            Log.d("Publish Error", e.getMessage());
            myRmq.Gagal();
        } catch (TimeoutException e) {
            Log.d("Publish Error", e.getMessage());
            myRmq.Gagal();
        } catch (Exception e) {
            Log.d("Publish Error", e.getMessage());
            myRmq.Gagal();
        }

    }

    /**
     * Optional, Send Speed for threading speed
     * @throws InterruptedException
     */
    public void SendSpeed() throws InterruptedException {
        Thread.sleep(500); //0.5 sec
    }

    /**
     * Fungsi untuk subscribe data RMQ
     * @param handler
     * @param subscribeThread
     */
    public void subscribe(final Handler handler, Thread subscribeThread, String queue_name_subscribe, String routingKey)
    {
        subscribeThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    try {
                        Connection connection = factory.newConnection();
                        Channel channel = connection.createChannel();
                        channel.basicQos(0);
                        channel.queueBind(queue_name_subscribe, exchanges_name, routingKey);
                        QueueingConsumer consumer = new QueueingConsumer(channel);
                        channel.basicConsume(queue_name_subscribe, true, consumer);
                        QueueingConsumer.Delivery delivery = consumer.nextDelivery();

                        if (delivery != null){
                            try{
                                String message = null;
                                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                                    message = new String(delivery.getBody(), StandardCharsets.UTF_8);
                                }
                                Log.d("ConsumeDataRMQ", "MessageConsumed" + message);
                                Message msg = handler.obtainMessage();
                                Bundle bundle = new Bundle();
                                bundle.putString("msg", message);
                                msg.setData(bundle);
                                handler.sendMessage(msg);
                                channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
                            }catch (Exception e){
                                channel.basicReject(delivery.getEnvelope().getDeliveryTag(),true);
                            }
                        }
                    } catch (InterruptedException e) {
                        break;
                    } catch (Exception e1) {
                        Log.d("", "Connection broken: " + e1.getClass().getName());
                        try {
                            Thread.sleep(4000); //sleep and then try again
                        } catch (InterruptedException e) {
                            break;
                        }
                    }
                }
            }
        });
        subscribeThread.start();
    }

}
