package com.imagegrafia.jmsfundamental;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class FirstQueue {

	public static void main(String[] args) {

		InitialContext initialContext = null;
		Connection connection = null;
		try {
			// intial context wil be load up from "app.prop" java.naming.factory.intial
			initialContext = new InitialContext();
			// passing name from "app.prop"
			// connectionFactory.ConnectionFactory=tcp://localhost:61616
			ConnectionFactory connectionFactory = (ConnectionFactory) initialContext.lookup("ConnectionFactory");
			 connection = connectionFactory.createConnection();

			Session session = connection.createSession();

			// passing anme from "app.prop" queue.queue/myQueue
			Queue queue = (Queue) initialContext.lookup("queue/myQueue");

			MessageProducer messageProducer = session.createProducer(queue); // destination

			TextMessage message = session.createTextMessage("I am Ashish here");

			messageProducer.send(message);
			System.out.println("Message sent : " + message.getText());

			// message consumer
			MessageConsumer messageConsumer = session.createConsumer(queue);
			connection.start();
			TextMessage textMessage = (TextMessage) messageConsumer.receive(5000);// timeout
			System.out.println("Message received : " + textMessage.getText());

		} catch (NamingException e) {
			e.printStackTrace();
		} catch (JMSException e) {
			e.printStackTrace();
		} finally {
			if (initialContext != null) {
				try {
					initialContext.close();
				} catch (NamingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(connection !=null) {
				try {
					connection.close();
				} catch (JMSException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}
}
