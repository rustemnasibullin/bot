package net.dtracktor.bot.components;

import net.dtracktor.bot.Start;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class FileBrowser  implements Processor  {
    
    public FileBrowser() {
        super();
    }
    
    BotConfigurator configurator;

    public void setConfigurator(BotConfigurator configurator) {
        this.configurator = configurator;
    }

    public BotConfigurator getConfigurator() {
        return configurator;
    }

 
    
    public void process (Exchange ex) throws Exception {
           
    
           System.out.println ("Test: "+ex.getIn().getBody()); 

           
    }
}
