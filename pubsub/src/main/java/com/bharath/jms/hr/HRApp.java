package com.bharath.jms.hr;
import javax.naming.InitialContext;

import java.util.Map;
import java.util.HashMap;
import javax.naming.NamingException;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.Queue;
import javax.jms.TemporaryQueue;
import javax.jms.TextMessage;
import javax.jms.Topic;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
public class HRApp {

	public static void main(String[] args) throws NamingException {
		InitialContext context =new InitialContext();
		Topic topic = (Topic) context.lookup("topic/bancoTopic");
		Topic topic2 = (Topic) context.lookup("topic/billeteraTopic");
		try(ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
				JMSContext jmscontext = cf.createContext()) {
			Comprador comprador = new Comprador();
			comprador.setDni(1234);
			comprador.setcantJavacoinAComprar(1);
			comprador.setCotizacion(1000.0);
			jmscontext.createProducer().send(topic, comprador);
			System.out.println("Message sent");
			
			Vendedor vendedor = new Vendedor();
			vendedor.setDni(4321);
			jmscontext.createProducer().send(topic2, vendedor);
			System.out.println("Message sent");
			
		}

				
	}

}
