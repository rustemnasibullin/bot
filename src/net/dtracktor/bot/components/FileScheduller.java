package net.dtracktor.bot.components;

import java.io.File;

import java.util.HashMap;
import java.util.HashSet;

import net.dtracktor.bot.Start;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.ProducerTemplate;

/**
 * 
 *   File schedule Command    
 *   @author rustemnasibullin@yahoo.com
 */
public class FileScheduller  implements Processor {
 
    BotConfigurator configurator;
    ProducerTemplate producer;
    int counter = 0 ;

    public void setProducer(ProducerTemplate producer) {
        this.producer = producer;
    }

    public ProducerTemplate getProducer() {
        return producer;
    }
    HashMap <String, Long> tstamps = new   HashMap <>();   
        

    public void setConfigurator(BotConfigurator configurator) {
        this.configurator = configurator;
    }

    public BotConfigurator getConfigurator() {
           return configurator;
    }

    public void process (Exchange in) {
        
               File fs = new File(Start.inputDir);
               File[] fss = fs.listFiles();

               for (File f: fss) {
                   

                    if (f.getName().endsWith(".gif")||f.getName().endsWith(".png")||f.getName().endsWith(".jpg")) {
                 
                    long  d = f.lastModified();
                    final String fid = f.getAbsolutePath();
                    Long ds = tstamps.get(fid);
                    
                    Start.log (fid); 
                    if (ds == null) {
                        tstamps.put(fid, d);
                        producer.send(new Processor() {
                                 public void process(Exchange outExchange) {
                                     outExchange.getIn().setBody(fid); 
                                     counter++;
                                 }
                              });
                    }
                    
                    
                    }
                   
               }
               
               Start.done = true;

    }
 
    
    public FileScheduller() {
        super();
    }
    
}
