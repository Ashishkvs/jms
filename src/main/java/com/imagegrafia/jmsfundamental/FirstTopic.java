package com.imagegrafia.jmsfundamental;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class FirstTopic {

	public static void main(String[] args) throws NamingException, JMSException {

		InitialContext initialContext = null;
		Connection connection = null;

		initialContext = new InitialContext();
		Topic topic = (Topic) initialContext.lookup("topic/myTopic");

		ConnectionFactory connectionFactory = (ConnectionFactory) initialContext.lookup("ConnectionFactory");
		connection = connectionFactory.createConnection();

		Session session = connection.createSession();
		MessageProducer producer = session.createProducer(topic);

		MessageConsumer consumer1 = session.createConsumer(topic);
		MessageConsumer consumer2 = session.createConsumer(topic);
		
		TextMessage message = session.createTextMessage("I am Ashish here sending Topic");
		producer.send(message);
//		System.out.println("Message sent : " + message.getText());
		
		//receive message
		connection.start();
		TextMessage textMessage1 = (TextMessage) consumer1.receive(5000);// timeout
		System.out.println("Consumer1 received : " + textMessage1.getText());
		
		TextMessage textMessage2 = (TextMessage) consumer2.receive(5000);// timeout
		System.out.println("Consumer1 received : " + textMessage2.getText());
		
		connection.close();
		initialContext.close();

		
	}
}
