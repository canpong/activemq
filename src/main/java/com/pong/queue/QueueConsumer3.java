package com.pong.queue;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.IOException;

public class QueueConsumer3 {
    public static void main(String[] args) throws JMSException, IOException,Exception {
        //1.创建连接工厂
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://127.0.0.1:61616");
        //2.获取连接
        Connection connection = connectionFactory.createConnection();
        //3.启动连接
        connection.start();
        //4.获取session  (参数1：是否启动事务,参数2：消息确认模式)
        /*4.获取session  (参数1：是否启动事务,
        参数2：消息确认模式[
        AUTO_ACKNOWLEDGE = 1    自动确认
        CLIENT_ACKNOWLEDGE = 2    客户端手动确认
        DUPS_OK_ACKNOWLEDGE = 3    自动批量确认
        SESSION_TRANSACTED = 0    事务提交并确认
        ])*/
        final Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
        //5.创建队列对象
        Queue queue = session.createQueue("test-queue");
        //6.创建消息消费者
        MessageConsumer consumer = session.createConsumer(queue);
        //7.监听消息
        consumer.setMessageListener(new MessageListener() {
        @Override
        public void onMessage(Message message) {
                TextMessage textMessage = (TextMessage) message;
                try {
                        System.out.println("接收到消息:"+textMessage.getText());
//                        System.out.println(1/0);
//                        message.acknowledge();//客户端手动确认
//                    session.rollback();//事务提交并确认
//                    session.commit();//事务提交并确认
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }

        });
        //8.等待键盘输入
        System.in.read();
        //9.关闭资源
        consumer.close();
        session.close();
        connection.close();
    }

}
