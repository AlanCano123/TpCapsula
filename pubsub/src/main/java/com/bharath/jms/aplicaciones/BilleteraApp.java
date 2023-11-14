package com.bharath.jms.aplicaciones;

import java.util.HashMap;
import java.util.Map;

import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Topic;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import com.bharath.jms.hr.Comprador;

import com.bharath.jms.hr.Vendedor;
public class BilleteraApp {
	

	public static void main(String[] args) throws NamingException, JMSException {
		InitialContext context =new InitialContext();
		Topic topic = (Topic) context.lookup("topic/bancoTopic");
		Topic topic2 = (Topic) context.lookup("topic/billeteraTopic");
		Map<Integer, Integer> cuentas = new HashMap<>();
		cuentas.put(1234, 10);
		cuentas.put(4321, 20);
		cuentas.put(2134, 30);
		
		try(ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
				JMSContext jmscontext = cf.createContext()) {
			
			JMSConsumer consumer = jmscontext.createConsumer(topic);
			Message message = consumer.receive();
			Comprador comprador = message.getBody(Comprador.class);
			if(!cuentas.containsKey(comprador.getDni())) {
				cuentas.put(comprador.getDni(),0);
			}
			
			
			JMSConsumer consumer2 = jmscontext.createConsumer(topic2);
			Message message2 = consumer2.receive();
			Vendedor vendedor = message2.getBody(Vendedor.class);
		
			if(comprador.getCantJavacoinAComprar()>cuentas.get(vendedor.getDni())){
				System.out.println("Javacoins insuficientes. Lanzando una excepción.");
				throw new IllegalArgumentException("La cantidad de javacoins del vendedor no es suficiente para completar la transaccion");
			}
					
			int saldoRestanteVendedor=cuentas.get(vendedor.getDni())-comprador.getCantJavacoinAComprar();
			cuentas.put(vendedor.getDni(),saldoRestanteVendedor);
			
			int saldoRestanteComprador=comprador.getCantJavacoinAComprar()+cuentas.get(comprador.getDni());
			cuentas.put(comprador.getDni(),saldoRestanteComprador);
			
			System.out.println("Listado de cuentas de la billetera");
			System.out.println(cuentas);	
			System.out.println("Saldo del Vendedor: "+vendedor.getDni()+" ");
			System.out.println(cuentas.get(vendedor.getDni()));
			System.out.println("Saldo del comprador: "+comprador.getDni()+" ");
			System.out.println(cuentas.get(comprador.getDni()));
			
		}

				
	}
}
