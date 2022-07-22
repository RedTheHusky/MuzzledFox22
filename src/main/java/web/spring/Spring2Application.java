package web.spring;
import models.lsGlobalHelper;
import net.dv8tion.jda.api.entities.Guild;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@SpringBootApplication
@RestController
//https://spring.io/guides/tutorials/rest/
//https://www.baeldung.com/exception-handling-for-rest-with-spring
//https://www.baeldung.com/global-error-handler-in-a-spring-rest-api
//https://stackoverflow.com/questions/44839753/returning-json-object-as-response-in-spring-boot
//https://www.baeldung.com/spring-boot-json
//https://www.baeldung.com/spring-mvc-send-json-parameters
//https://knasmueller.net/send-json-objects-via-post-to-spring-boot-controllers
//https://www.geeksforgeeks.org/spring-rest-json-response/
public class Spring2Application {
    Logger logger = Logger.getLogger(getClass());
    public Spring2Application(){

    }
    @GetMapping
    public String hello(){
        String fName="[hello]";
        logger.info(fName+".init");
        return "Hello world";
    }

    /*@Value("${key1}")
    String value_key1;
    @Autowired
    ApplicationContext applicationContext;*/

}
