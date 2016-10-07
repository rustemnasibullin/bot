package net.dtracktor.bot.utils;

public interface IHTTPClient {
    
    void setURL (String url);
    void setHeader (String id, String value);
    void setDataAsByteArray(byte[] body);
    String execute();        
    String getResponseDataAsString();
    
}
