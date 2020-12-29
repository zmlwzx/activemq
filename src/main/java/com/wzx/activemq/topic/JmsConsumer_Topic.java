package com.wzx.activemq.topic;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.IOException;

public class JmsConsumer_Topic {

    public static final String ACTIVEMQ_URL = "tcp://192.168.184.10:61616";
    public static final String TOPIC_NAME = "topic-wzx";

    public static void main(String[] args) throws JMSException, IOException {

        System.out.println("****我是3号消息者*******");
        //1.创建连接工厂，按照给定的url地址，采用默认用户名和密码
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);

        //2.通过连接工厂，获取连接connection并启动访问
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();

        //3.创建会话session
        //两个参数，每个叫事务，第二个叫签收
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        //4.创建目的地（具体是队列还是主题topic）
        Topic topic = session.createTopic(TOPIC_NAME);

        //5.创建消息的消费者
        MessageConsumer consumer = session.createConsumer(topic);

        /*while (true) {
            TextMessage textMessage = (TextMessage) consumer.receive();
            if (null != textMessage) {
                System.out.println("******消费者接收到消息：" + textMessage.getText());
            } else {
                break;
            }
        }
        consumer.close();
        session.close();
        connection.close();*/

        //通过监听的方式来消费消息，

        /*consumer.setMessageListener(new MessageListener() {
            public void onMessage(Message message) {
                if(null != message && message instanceof TextMessage){
                    TextMessage textMessage = (TextMessage) message;

                    try {
                        System.out.println("**********消费者接收到消息："+ textMessage.getText());
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }
            }
        });*/

        consumer.setMessageListener((message) -> {
            if(null != message && message instanceof TextMessage){
                TextMessage textMessage = (TextMessage) message;

                try {
                    System.out.println("**********消费者接收到Topic消息："+ textMessage.getText());
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });

        System.in.read();
        consumer.close();
        session.close();
        connection.close();

        //1、先生产，只启动1号消费者，结论：1号消费者可以消费
        //2、先生产，先启动1号消费者，再启动2号消费者。结论：1号消费者可以消费，2号消费者不可以消费。

        //3、先启动2个消费者，再生产6条消息，结论：消费者一人一半，1号消费者3条，2号消费者3条。
    }
}




























