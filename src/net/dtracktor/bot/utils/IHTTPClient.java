package net.dtracktor.bot.utils;

/**
 * 
 *   Typical HTTP Client Interface    
 *   @author rustemnasibullin@yahoo.com
 */
public interface IHTTPClient {
    
    void setURL (String url);
    void setHeader (String id, String value);
    void setDataAsByteArray(byte[] body);
    String execute();        
    String getResponseDataAsString();
    
}
