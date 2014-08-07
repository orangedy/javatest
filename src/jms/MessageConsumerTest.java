package jms;

import java.util.Properties;
import java.util.concurrent.Executors;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.jms.Session;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class MessageConsumerTest {
    private static String def_windows_url = "file:///C:/Temp";

    private String MYCF_LOOKUP_NAME = "MyConnectionFactory";
    private String MYQUEUE_LOOKUP_NAME = "MyQueue";

    private ConnectionFactory cf;
    private Connection connection;
    private Session session;
    private MessageConsumer msgConsumer;
    private Queue queue;

    public MessageConsumerTest() {
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
            msgConsumer = session.createConsumer(queue);
        } catch (NamingException ne) {

        } catch (JMSException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void consumeMessage() {
        try {
            connection.start();
            Message message = msgConsumer.receive();
            System.out.println(message);
            connection.close();
        } catch (JMSException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void listenMessageInThread() {
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            public void run() {
                try {
                    msgConsumer.setMessageListener(new MessageListener() {
                        public void onMessage(Message message) {
                            System.out.println(message);
                        }
                    });
                    connection.start();
                } catch (JMSException e) {
                    e.printStackTrace();
                }

            }
        });
        System.out.println("created thread");
    }

    public void listenMessage() {
        try {
            msgConsumer.setMessageListener(new MessageListener() {
                public void onMessage(Message message) {
                    System.out.println(message);
                }
            });
            connection.start();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        MessageConsumerTest consumer = new MessageConsumerTest();
//        consumer.consumeMessage();
        consumer.listenMessage();
    }
}
