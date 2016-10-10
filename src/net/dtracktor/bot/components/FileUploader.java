package net.dtracktor.bot.components;

import net.dtracktor.bot.GoogleDRIVEApi;
import net.dtracktor.bot.Start;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

/**
 * 
 *   File Upload command    
 *   @author rustemnasibullin@yahoo.com
 */
public class FileUploader implements Processor {

    BotConfigurator configurator;
    volatile int counter = 0;

    public void setConfigurator(BotConfigurator configurator) {
        this.configurator = configurator;
    }

    public BotConfigurator getConfigurator() {
        return configurator;
    }


    private boolean sendToGoogle (String qs) {
    
            boolean b = false;
            String xOAuthToken = configurator.getProperty("AUTHTOKEN");
            GoogleDRIVEApi api = new  GoogleDRIVEApi ();
            try {
              api.upload_file(qs, xOAuthToken);
              b=true;
            } catch (Throwable ee) {
              ee.printStackTrace();    
            }
            return b;
    
    }


    public void process (Exchange ex) throws Exception {
        
        counter++;
        int exitcode = 0;
        String qs = ex.getIn().getBody(String.class);
        if (Start.n>0 && counter>Start.n) {
            exitcode = 2;
            Start.done = true;
            ex.getOut().setBody(qs);

        } else {


            if (!sendToGoogle (qs)) {
                exitcode = 1;
            };

        ex.getOut().setBody(qs);
        
        }

        ex.getOut().setHeader("exitcode", exitcode);
        
    }

    public FileUploader() {
           super();
    }
    
}
