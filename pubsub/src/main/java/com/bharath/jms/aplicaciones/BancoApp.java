package com.bharath.jms.aplicaciones;

import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Topic;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import com.bharath.jms.hr.Comprador;
import com.bharath.jms.hr.Vendedor;

public class BancoApp {
	public static void main(String[] args) throws NamingException, JMSException, InterruptedException {
		InitialContext context = new InitialContext();
		Topic topic = (Topic) context.lookup("topic/bancoTopic");
		Topic topic2 = (Topic) context.lookup("topic/billeteraTopic");
		Map<Integer, Double> cuentas = new HashMap<>();
		cuentas.put(1234, 9000.0);
		cuentas.put(4321, 8000.0);
		cuentas.put(2134, 9000.0);

		try (ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
				JMSContext jmscontext = cf.createContext()) {
			JMSConsumer consumer = jmscontext.createConsumer(topic);
			Message message = consumer.receive();
			Comprador comprador = message.getBody(Comprador.class);

			if (!cuentas.containsKey(comprador.getDni())) {
				System.out.println("Cliente no encontrado. Lanzando una excepción.");
				throw new IllegalArgumentException("Cliente no encontrado.");
			}

			double monto = comprador.getCantJavacoinAComprar() * comprador.getCotizacion();

			if (comprador.getCantOperaciones() < 3) {
				double comision = monto * 3 / 100;
				monto = monto + comision;
			} else if (comprador.getCantOperaciones() >= 3 && comprador.getCantOperaciones() < 6) {
				double comision = monto * 6 / 100;
				monto = monto + comision;
			}
			if (monto > cuentas.get(comprador.getDni())) {
				System.out.println("No alcanza el saldo. Lanzando una excepción.");
				throw new IllegalArgumentException("Cliente con saldo insuficiente para completar transaccion");
			}

			cuentas.put(comprador.getDni(), monto);

			JMSConsumer consumer2 = jmscontext.createConsumer(topic2);
			Message message2 = consumer2.receive();
			Vendedor vendedor = message2.getBody(Vendedor.class);
			double montoAAcreditar = comprador.getCantJavacoinAComprar() * comprador.getCotizacion();

			if (vendedor.getCantOperaciones() < 3) {
				double comision = montoAAcreditar * 3 / 100;
				montoAAcreditar = montoAAcreditar - comision;
			} else if (vendedor.getCantOperaciones() >= 3 && vendedor.getCantOperaciones() < 6) {
				double comision = montoAAcreditar * 6 / 100;
				montoAAcreditar = montoAAcreditar - comision;
			}
			cuentas.put(vendedor.getDni(), montoAAcreditar);
		}

	}

}
