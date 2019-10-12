package com.pong.topic;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * 发布订阅的模式 默认的请情况下：消息的内容不存在服务器，当生产者发送了一个消息，如果消费者之前没有订阅，就没了。 
 * 点对点的方式：默认的请情况下：将消息存储在服务器上，消费者随时来取，但是一旦一个消费者获取到了消息，这个消息就没有了。
 */
public class TopicProducer {
    public static void main(String[] args) throws JMSException {
        //1.创建连接工厂
        ConnectionFactory connectionFactory=new ActiveMQConnectionFactory("tcp://127.0.0.1:61616");
        //2.获取连接
        Connection connection = connectionFactory.createConnection();
        //3.启动连接
        connection.start();
        /*4.获取session  (参数1：是否启动事务,
        参数2：消息确认模式)*/
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //5.创建主题对象
        Topic topic = session.createTopic("test-topic");
        //6.创建消息生产者
        MessageProducer producer = session.createProducer(topic);
        //7.创建消息
        TextMessage textMessage = session.createTextMessage("欢迎来到MQ世界2!");
        //8.发送消息
        producer.send(textMessage);
        //9.关闭资源
        producer.close();
        session.close();
        connection.close();
     }

}
