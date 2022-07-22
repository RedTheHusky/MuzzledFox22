package web.spring;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import models.lcGlobalHelper;
import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.FileSystemResource;
import web.spring.discord.saEct;
import web.spring.mongodb.Spring2ApplicationMono;

import java.util.Arrays;

//https://www.toptal.com/spring/top-10-most-common-spring-framework-mistakes
public class Spring2Init {
    Logger logger = Logger.getLogger(getClass());
    public Spring2Init(lcGlobalHelper global){
        gGlobal=global;
    }
    lcGlobalHelper gGlobal;
    PropertySourcesPlaceholderConfigurer properties;
    public PropertySourcesPlaceholderConfigurer propertyLoader() {
        String fName="[propertyLoader]";
        try{
            properties =
                    new PropertySourcesPlaceholderConfigurer();
            properties.setLocation(new FileSystemResource("./resources/spring.properties"));
            properties.setIgnoreResourceNotFound(false);
            return properties;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e+" StackTrace:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public ConfigurableApplicationContext build(){
        String fName="[build]";
        try{
            logger.info(fName+"init");
            //if(propertyLoader()==null)throw  new Exception("Could not load property file> abort");
            return createApp();
        }catch (Exception e){
            logger.error(fName + ".exception=" + e+" StackTrace:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    SpringApplication application;ConfigurableApplicationContext configurableapplicationacontext;
    public ConfigurableApplicationContext createApp(){
        String fName="[createApp]";
        try{
            logger.info(fName+"init");
            //soaptest_CountryEndpoint.class
            application=  new SpringApplicationBuilder() .sources(Spring2Application.class, saEct.class, Spring2ApplicationMono.class ).build();
            configurableapplicationacontext=application.run();
            logger.info(fName+"done");
            return configurableapplicationacontext;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e+" StackTrace:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public ConfigurableApplicationContext build_v1(){
        String fName="[build]";
        try{
            logger.info(fName+"init");
            configurableapplicationacontext=SpringApplication.run(Spring2Application.class);
            logger.info(fName+"done");
            return configurableapplicationacontext;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e+" StackTrace:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    MongoClient mongoClient;
    public MongoClient connect2MongoDb(){
        String fName="[connect2MongoDb]";
        try{
            logger.info(fName+"init");
            ConnectionString connectionString = new ConnectionString("mongodb+srv://red:hIjhMR9D1CQUh3XD@cluster0.fo10zty.mongodb.net/?retryWrites=true&w=majority");
            MongoClientSettings settings = MongoClientSettings.builder()
                    .applyConnectionString(connectionString)
                    .serverApi(ServerApi.builder()
                            .version(ServerApiVersion.V1)
                            .build())
                    .build();
            mongoClient = MongoClients.create(settings);
            logger.info(fName+"testing...");
            MongoDatabase database = mongoClient.getDatabase("test");
            logger.info(fName+"done");
            return  mongoClient;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e+" StackTrace:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }


    public boolean start(){
        String fName="[start]";
        try{
            logger.info(fName+"init");
            if(configurableapplicationacontext.isRunning())throw  new Exception("Cant start while running!");
            configurableapplicationacontext.start();
            logger.info(fName+"done");
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e+" StackTrace:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean stop(){
        String fName="[start]";
        try{
            logger.info(fName+"init");
            if(!configurableapplicationacontext.isRunning())throw  new Exception("Cant stop if not running!");
            configurableapplicationacontext.stop();
            logger.info(fName+"done");
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e+" StackTrace:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }



}
