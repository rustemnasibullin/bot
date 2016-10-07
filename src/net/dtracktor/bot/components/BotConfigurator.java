package net.dtracktor.bot.components;

import java.io.File;

import java.io.FileInputStream;

import java.io.IOException;

import java.util.Properties;

import net.dtracktor.bot.Start;

public class BotConfigurator extends Properties {

    public BotConfigurator() {
        
        super();
        
        try {
        
          FileInputStream fi = new FileInputStream ("bot.ini");
          this.load(fi);  
        
        } catch (IOException ee) {
          Start.log (ee.getMessage());  
        }
        
    }

}
