package net.dtracktor.bot.components;

import net.dtracktor.bot.Start;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

/**
 * 
 * 
 */
public class FileRetry implements Processor {
    public FileRetry() {
        super();
    }
    
    
    BotConfigurator configurator;
    volatile int counter = 0;

    public void setConfigurator(BotConfigurator configurator) {
        this.configurator = configurator;
    }

    public BotConfigurator getConfigurator() {
        return configurator;
    }

    public void process (Exchange ex) throws Exception {
        
        counter++;
        int exitcode = 0;
        if (Start.n>0 && counter>Start.n) {
            Start.done = true;
            throw new Exception ();
        }

        String qs = ex.getIn().getBody(String.class);


        ex.getOut().setHeader("exitcode", exitcode);
        ex.getOut().setBody(qs);
        
    }

}
