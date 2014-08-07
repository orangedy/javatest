package jms;

import java.util.Properties;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class MessageProducerTest {
    private static String def_windows_url = "file:///C:/Temp";

    private String MYCF_LOOKUP_NAME = "MyConnectionFactory";
    private String MYQUEUE_LOOKUP_NAME = "MyQueue";

    private ConnectionFactory cf;
    private Connection connection;
    private Session session;
    private MessageProducer msgProducer;
    private Queue queue;

    public MessageProducerTest() {
        Properties env = new Properties();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.fscontext.RefFSContextFactory");
        env.put(Context.PROVIDER_URL, def_windows_url);
        Context ctx = null;
        try {
            // Create the initial context.
            ctx = new InitialContext(env);
            cf = (javax.jms.ConnectionFactory) ctx.lookup(MYCF_LOOKUP_NAME);
            queue = (javax.jms.Queue) ctx.lookup(MYQUEUE_LOOKUP_NAME);
            connection = cf.createConnection();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            msgProducer = session.createProducer(queue);
        } catch (NamingException ne) {
            System.err.println("context error");
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public void produceMessage() {
        try {
            connection.start();
            TextMessage message = session.createTextMessage("hello");
//            message = new TextMessageImpl();
            msgProducer.send(message, DeliveryMode.NON_PERSISTENT, 4, 0);
            connection.close();
            System.out.println("send message");
        } catch (JMSException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        MessageProducerTest producer = new MessageProducerTest();
        producer.produceMessage();
    }
}
