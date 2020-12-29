package com.wzx.activemq.topic;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class JmsProduce_Topic {

    public static final String ACTIVEMQ_URL = "tcp://192.168.184.10:61616";
    public static final String TOPIC_NAME = "topic-wzx";

    public static void main(String[] args) throws JMSException {
        //1.创建连接工厂，按照给定的url地址，采用默认用户名和密码
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);

        //2.通过连接工厂，获取连接connection并启动访问
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();

        //3.创建会话session
        //两个参数，每个叫事务，第二个叫签收
        Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);

        //4.创建目的地（具体是队列还是主题topic）
        Topic topic = session.createTopic(TOPIC_NAME);

        //5.创建消息的生厂者
        MessageProducer producer = session.createProducer(topic);

        //6.通过使用messageProducer生产3条消息发送到MQ的队列里面
        for (int i = 1; i <=9 ; i++) {
            //7.创建消息，好比学生按照要求写好的消息
            TextMessage textMessage = session.createTextMessage("msg--" + i);
            producer.send(textMessage);
        }

        //9.关闭资源
        producer.close();
        session.close();
        connection.close();


        System.out.println("*******消息发布到MQ******");
;    }
}
