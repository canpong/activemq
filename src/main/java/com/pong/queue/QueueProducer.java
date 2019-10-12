package com.pong.queue;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * 发布订阅的模式 默认的请情况下：消息的内容不存在服务器，当生产者发送了一个消息，如果消费者之前没有订阅，就没了。 
 * 点对点的方式：默认的请情况下：将消息存储在服务器上，消费者随时来取，但是一旦一个消费者获取到了消息，这个消息就没有了。
 */
public class QueueProducer {
    public static void main(String[] args) throws JMSException {
        //1.创建连接工厂
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://127.0.0.1:61616");
        //2.获取连接
        Connection connection = connectionFactory.createConnection();
        //3.启动连接
        connection.start();
        /*4.获取session  (参数1：是否启动事务,
        参数2：消息确认模式[
        AUTO_ACKNOWLEDGE = 1    自动确认
        CLIENT_ACKNOWLEDGE = 2    客户端手动确认
        DUPS_OK_ACKNOWLEDGE = 3    自动批量确认
        SESSION_TRANSACTED = 0    事务提交并确认
        ])*/
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //5.创建队列对象
        Queue queue = session.createQueue("test-queue");
        //6.创建消息生产者
        MessageProducer producer = session.createProducer(queue);
        //7.创建消息
        TextMessage textMessage = session.createTextMessage("欢迎来到MQ世界15");
        //8.发送消息
        producer.send(textMessage);
        double a = 1/0;
        //9.关闭资源
        producer.close();
        session.close();
        connection.close();
    }
}
