package jms;

import java.util.Hashtable;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class OpenMQWithJNDITest {
    static String def_windows_url = "file:///C:/Temp";
    static String def_unix_url = "file:///tmp";

    String MYCF_LOOKUP_NAME = "MyConnectionFactory";
    String MYQUEUE_LOOKUP_NAME = "MyQueue";

    ConnectionFactory cf;
    Connection connection;
    Session session;
    MessageProducer msgProducer;
    MessageConsumer msgConsumer;
    Queue queue;
    TextMessage msg, rcvMsg;

    public static void main(String args[]) {
        String url = def_windows_url;

        if (args.length > 0) {
            url = args[0];
        }
        System.out.println("\nUsing " + url + " for Context.PROVIDER_URL");

        OpenMQWithJNDITest simple_client = new OpenMQWithJNDITest(url);
    }

    public OpenMQWithJNDITest(String url) {
        Hashtable env;
        Context ctx = null;

        env = new Hashtable();

        // Store the environment variables that tell JNDI which initial context
        // to use and where to find the provider.

        // For use with the File System JNDI Service Provider
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.fscontext.RefFSContextFactory");
        env.put(Context.PROVIDER_URL, url);

        try {
            // Create the initial context.
            ctx = new InitialContext(env);
        } catch (NamingException ne) {
            System.err.println("Failed to create InitialContext.");
            System.err.println("The Context.PROVIDER_URL used/specified was: " + url);
            System.err.println("Please make sure that the path to the above URL exists");
            System.err.println("and matches with the objstore.attrs.java.naming.provider.url");
            System.err.println("property value specified in the imqobjmgr command files:");
            System.err.println("\tadd_cf.props");
            System.err.println("\tadd_q.props");
            System.err.println("\tdelete_cf.props");
            System.err.println("\tdelete_q.props");
            System.err.println("\tlist.props\n");

            usage();

            System.err.println("\nThe exception details:");
            ne.printStackTrace();
            System.exit(-1);
        }

        System.out.println("");

        try {
            // Lookup my connection factory from the admin object store.
            // The name used here here must match the lookup name
            // used when the admin object was stored.
            System.out.println("Looking up Connection Factory object with lookup name: " + MYCF_LOOKUP_NAME);
            cf = (javax.jms.ConnectionFactory) ctx.lookup(MYCF_LOOKUP_NAME);
            System.out.println("Connection Factory object found.");
        } catch (NamingException ne) {
            System.err.println("Failed to lookup Connection Factory object.");
            System.err.println("Please make sure you have created the Connection Factory object using the command:");
            System.err.println("\timqobjmgr -i add_cf.props");

            System.err.println("\nThe exception details:");
            ne.printStackTrace();
            System.exit(-1);
        }

        System.out.println("");

        try {
            // Lookup my queue from the admin object store.
            // The name I search for here must match the lookup name used when
            // the admin object was stored.
            System.out.println("Looking up Queue object with lookup name: " + MYQUEUE_LOOKUP_NAME);
            queue = (javax.jms.Queue) ctx.lookup(MYQUEUE_LOOKUP_NAME);
            System.out.println("Queue object found.");
        } catch (NamingException ne) {
            System.err.println("Failed to lookup Queue object.");
            System.err.println("Please make sure you have created the Queue object using the command:");
            System.err.println("\timqobjmgr -i add_q.props");

            System.err.println("\nThe exception details:");
            ne.printStackTrace();
            System.exit(-1);
        }

        System.out.println("");

        try {
            System.out.println("Creating connection to broker.");
            connection = cf.createConnection();
            System.out.println("Connection to broker created.");
        } catch (JMSException e) {
            System.err.println("Failed to create connection.");
            System.err.println("Please make sure that the broker was started.");

            System.err.println("\nThe exception details:");
            e.printStackTrace();
            System.exit(-1);
        }

        System.out.println("");

        try {
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            // Create the MessageProducer and MessageConsumer
            msgProducer = session.createProducer(queue);
            msgConsumer = session.createConsumer(queue);

            // Tell the provider to start sending messages.
            connection.start();

            msg = session.createTextMessage("Hello World");

            // Publish the message
            System.out.println("Publishing a message to Queue: " + queue.getQueueName());
            msgProducer.send(msg, DeliveryMode.NON_PERSISTENT, 4, 0);

            // Wait for it to be sent back.
            rcvMsg = (TextMessage) msgConsumer.receive();

            System.out.println("Received the following message: " + rcvMsg.getText());

            connection.close();

        } catch (JMSException e) {
            System.err.println("JMS Exception: " + e);
            e.printStackTrace();
            System.exit(-1);
        }
    }

    private static void usage() {
        System.err.println("Usage: " + "\tjava HelloWorldMessageJNDI [Context.PROVIDER_URL]\n"
                        + "\nOn Unix:\n\tjava HelloWorldMessageJNDI " + def_unix_url
                        + "\nOn Windows:\n\tjava HelloWorldMessageJNDI " + def_windows_url);
    }
}
