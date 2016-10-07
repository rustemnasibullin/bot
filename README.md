To Run image bot service some component should be installed, configured and started. 

1. For interaction processes use MOM. This one is Apache ActiveMQ. You can download one from url: http://archive.apache.org/dist/activemq/5.11.1/apache-activemq-5.11.1-bin.zip
After unzip zip file to appropriate folder you can start ActiveMQ with use following command:
      bin/activemq start

But to get ability to control or monitor ActiveMQ manually from bot-application, ActiveMQ configuration file conf/activemq.xml should contains following tag 
    <broker xmlns="http://activemq.apache.org/schema/core" brokerName="localhost" dataDirectory="${activemq.data}" useJmx="true">
     ....
    </broker>
and appropriate connection configuration tag like following:

    <managementContext>
        <managementContext createConnector="true"  connectorPort="1099"/>
    </managementContext>
 
2. To build  bot application Maven framework should be installed. Last one may be downloaded via url: http://apache-mirror.rbc.ru/pub/apache/maven/maven-3/3.3.9/binaries/apache-maven-3.3.9-bin.tar.gz 
3. Proprietary springframework-patch must be installed in local maven repository by command from work directory: mvn_install.cmd (mvn_install.sh for Unix/Linux)
 

Before start usage of bot service make appropriate script as an executable like this : chmod 777 bot.
You can monitor ActiveMQ service by WEB, just connect via web-browser by link http://<ACTIVE_MQ_HOST>:8161.

Configuration of bot service located in bot.ini file of the work forder.

Files store with use Google Drive Cloud resource storage. Appropriate OAuth2 token should be located in configuration file under attribute AUTH_TOKEN.

Please enjoy of usage of image-bot service designed and developed by me, Rustem Nasibullin.  
   

