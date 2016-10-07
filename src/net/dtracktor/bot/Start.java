package net.dtracktor.bot;


import javax.management.MBeanServerConnection;
import javax.management.MBeanServerInvocationHandler;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

import net.dtracktor.bot.components.BotConfigurator;

import org.apache.activemq.broker.jmx.BrokerViewMBean;
import org.apache.activemq.broker.jmx.QueueViewMBean;
import org.apache.camel.spring.SpringCamelContext;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 *  Camel context starter-class
 *
 *  @author rustemnasibullin@yahoo.com
 */
public class Start {

    public static final String _schedule = "schedule";
    public static final String _resize = "resize";
    public static final String _status = "status";
    public static final String _upload = "upload";
  
    public static String inputDir = null;
    public static String cmd = null;
    public static int n = -1;
    public static volatile boolean done  = false;
    
    public static BotConfigurator conf = new BotConfigurator();

    public static void log (Object logObject) {
           System.out.println (logObject);
    }


    public static void status(String host, String port) throws Exception {
            JMXServiceURL url = new JMXServiceURL(
                            "service:jmx:rmi:///jndi/rmi://"+host+":"+port+"/jmxrmi");
            JMXConnector connector = JMXConnectorFactory.connect(url, null);
            connector.connect();
            MBeanServerConnection connection = connector.getMBeanServerConnection();
            ObjectName name = new ObjectName(
                            "org.apache.activemq:brokerName=localhost,type=Broker");
            BrokerViewMBean mbean = (BrokerViewMBean) MBeanServerInvocationHandler
                            .newProxyInstance(connection, name, BrokerViewMBean.class, true);
            
            System.out.println("Queue    Count");
            for (ObjectName queueName : mbean.getQueues()) {
                    QueueViewMBean queueMbean = (QueueViewMBean) MBeanServerInvocationHandler
                                    .newProxyInstance(connection, queueName,
                                                    QueueViewMBean.class, true);
                    System.out.println(queueMbean.getName()+"   "+queueMbean.getQueueSize());
            }
  
  
    }
     

    public void start() {

        SpringCamelContext camel = null; 
        try {
            
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("META-INF/spring/camel-context-bot.xml");    
        context.start();
        camel = (SpringCamelContext) context.getBean(cmd);
        camel.start();
        camel.startAllRoutes();    
            
        } catch (Throwable e) {
            
            e.printStackTrace();
            
        }


 
        log (cmd + " Command Processing ....... ");
        Thread th = new Thread (new Runnable (){
        
               public void run () {
                   
               while (!done) {
                   
               try {
                      Thread.currentThread().sleep (100);
               } catch (InterruptedException ee) {
                      log (ee);
               }
               }
               }
        
            
        });

        th.start();
        try {

        th.join();
        System.out.print("Press any Key to continue....");
        System.in.read();    
        camel.shutdown();
        
        } catch (Exception e) {
            // TODO: Add catch code
            e.printStackTrace();
        }
        System.exit (0);
    
    };

    
    public static void main (String [] args) throws Exception {


        if (args.length == 0) {
            
        
           System.out.println (" Uploader Bot Usage: \n   command [arguments] \n  Available commands:" +
               "\n   schedule  Add filenames to resize queue" +
               "\n   resize    Resize next images from the queue" +
               "\n   status    Output current status in format %queue%:%number_of_images%" +
               "\n   upload    Upload next images to remote storage");
           System.exit(0); 
        
        }
        
        cmd = args[0];
        if (_status.equals(cmd)) {
         
         status (conf.getProperty("AMQ_HOST","localhost"),conf.getProperty("AMQ_RMI_PORT","1099"));
         System.exit(0);
        }


        if (args.length>1) {
            
            // set Parameters
            if (_schedule.equals(cmd)) {
                inputDir = args[1];
                
            } else {
            
                if ("-n".equals(args[1])) {
                    if (args.length > 2) {
                    
                        n = Integer.parseInt (args[2]);  
                    
                    } else {
                        n = 0; 
                    }
                }
            
            }            
            
            
        }
        
        System.out.println ("Start2:"); 
        Start s = new Start ();
        s.start();  

    }


 


}
